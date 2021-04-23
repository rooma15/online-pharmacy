package com.epam.jwd.Servlet.command.medicine;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.service.impl.MedicineService;
import com.epam.jwd.Servlet.Util.Util;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.ResourceBundle;

public enum AddMedicineCommand implements Command {

    INSTANCE;

    private final String INSERT_IMAGE_PATH = "insert into pharmacy.Images values(null, ?, ?)";

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }


    private final ResponseContext SUCCESS_ADD_MEDICINE_CONTEXT = new ResponseContext() {
        @Override
        public boolean isAjax() {
            return false;
        }

        @Override
        public String getErrorCode() {
            return null;
        }

        @Override
        public String getPage() {
            return "admin/editMedicines.jsp";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private final ResponseContext ERROR_ADD_MEDICINE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "/Controller?action=admin_show_create_medicine_page";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public boolean isAjax() {
            return false;
        }

        @Override
        public String getErrorCode() {
            return null;
        }
    };

    BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        String loc = (String)req.getSession().getAttribute("locale");
        ResourceBundle locale;
        if(loc != null){
            locale = Util.getLocaleBundle((String)req.getSession().getAttribute("locale"));
        }else {
            locale = Util.getLocaleBundle("ru_RU");
        }
        MedicineService service = new MedicineService();
        MedicineDto medicineDto;
        String fileName = "";
        try {
            Part filePart = req.getPart("file");
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            Image image = ImageIO.read(fileContent);
            BufferedImage bi = createResizedCopy(image, 120, 120, true);
            ImageIO.write(bi, "jpg",
                    new File("/home/rumin/IdeaProjects/online-pharmacy/src/main/webapp/img/" + fileName));
        }catch (java.io.IOException | javax.servlet.ServletException e){
            Util.lOGGER.error(e.getMessage());
        }
        try {
            String name = req.getParameter("name");
            int dose = Integer.parseInt(req.getParameter("dose"));
            boolean isPrescriptionDrug = req.getParameter("prescriptionDrug") != null;
            String description = req.getParameter("description");
            String indicationsForUse = req.getParameter("indicationsForUse");
            String contraindications = req.getParameter("contraindications");
            String sideEffects = req.getParameter("sideEffects");
            String consistency = req.getParameter("consistency");
            String composition = req.getParameter("composition");
            double price = Double.parseDouble(req.getParameter("price"));
            String category = req.getParameter("category");
            Enumeration<String> paramNames = req.getParameterNames();
            while(paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                if(req.getParameter(paramName) == null || "".equals(req.getParameter(paramName))){
                    req.setAttribute("error", locale.getString("regError.notAllFields"));
                    return ERROR_ADD_MEDICINE_CONTEXT;
                }
            }
            AbstractDAO.updateByCriteria(INSERT_IMAGE_PATH, "ss", name, fileName);
            medicineDto = new MedicineDto(name,
                    dose,
                    isPrescriptionDrug,
                    description,
                    indicationsForUse,
                    contraindications,
                    sideEffects,
                    consistency,
                    composition,
                    price,
                    category,
                    fileName);
        }catch (NumberFormatException e){
            req.setAttribute("error", locale.getString("addError.notAllFieldsCorrect"));
            return ERROR_ADD_MEDICINE_CONTEXT;
        }
        boolean status = service.create(medicineDto);
        return SUCCESS_ADD_MEDICINE_CONTEXT;
    }

}
