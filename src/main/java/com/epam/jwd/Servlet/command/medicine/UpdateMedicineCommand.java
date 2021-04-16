package com.epam.jwd.Servlet.command.medicine;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.service.impl.MedicineService;
import com.epam.jwd.Util;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.ResourceBundle;

public enum UpdateMedicineCommand implements Command {
    INSTANCE;


    private final String UPDATE_IMAGE_PATH = "update pharmacy.Images set path=? where medicine_name=?";

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }

    private final ResponseContext CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "/Controller?action=admin_show_all_medicines";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }

        @Override
        public String getErrorCode() {
            return null;
        }

        @Override
        public boolean isAjax() {
            return false;
        }
    };


    private final ResponseContext ERROR_REQUEST_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return null;
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
            return "400";
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
        ResourceBundle locale = Util.getLocaleBundle((String)req.getSession().getAttribute("locale"));
        MedicineService service = new MedicineService();
        String fileName = "";
        try {
            Part filePart = req.getPart("file");
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            Image image = ImageIO.read(fileContent);
            BufferedImage bi = createResizedCopy(image, 120, 120, true);
            ImageIO.write(bi, "jpg", new File("/home/rumin/IdeaProjects/online-pharmacy/src/main/webapp/img/" + fileName));
        }catch (java.io.IOException | javax.servlet.ServletException e){
            Util.lOGGER.error(e.getMessage());
        }
        String name = req.getParameter("name");
        boolean isPrescriptionDrug = req.getParameter("prescriptionDrug") != null;
        String description = req.getParameter("description");
        String indicationsForUse = req.getParameter("indicationsForUse");
        String contraindications = req.getParameter("contraindications");
        String sideEffects = req.getParameter("sideEffects");
        String consistency = req.getParameter("consistency");
        String composition = req.getParameter("composition");
        String category = req.getParameter("category");
        Enumeration<String> paramNames = req.getParameterNames();
        int id;
        double price;
        int dose;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException e){
            return ERROR_REQUEST_CONTEXT;
        }

        AbstractDAO.updateByCriteria(UPDATE_IMAGE_PATH, "ss", fileName, name);

        ResponseContext ERROR_UPDATE_CONTEXT = new ResponseContext() {
            @Override
            public String getPage() {
                return "/Controller?action=show_update_medicine_page&id=" + id;
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

        while(paramNames.hasMoreElements()){
            String paramName = paramNames.nextElement();
            if(req.getParameter(paramName) == null || "".equals(req.getParameter(paramName))){
                req.setAttribute("error", locale.getString("regError.notAllFields"));
                return ERROR_UPDATE_CONTEXT;
            }
        }
        try{
            price = Double.parseDouble(req.getParameter("price"));
            dose = Integer.parseInt(req.getParameter("dose"));
        }catch (NumberFormatException e){
            req.setAttribute("error", locale.getString("regError.notAllFields"));
            return ERROR_UPDATE_CONTEXT;
        }

        MedicineDto medicineDto = new MedicineDto(name,
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
        service.updateById(id, medicineDto);
        return CONTEXT;
    }
}
