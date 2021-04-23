package com.epam.jwd.Servlet.command.page;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.model.User;
import com.epam.jwd.Servlet.model.UserDto;
import com.epam.jwd.Servlet.service.impl.MedicineService;
import com.epam.jwd.Servlet.service.impl.PrescriptionService;
import com.epam.jwd.Servlet.service.impl.UserService;
import com.epam.jwd.Servlet.Util.Util;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public enum ShowMedicinePageCommand implements Command {
    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return false;
    }

    private final ResponseContext SUCCESS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "medicine_page.jsp";
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


    @Override
    public ResponseContext execute(RequestContext req) {
        MedicineService medicineService = new MedicineService();
        UserService userService = new UserService();
        PrescriptionService prescriptionService = new PrescriptionService();
        HttpSession session = req.getSession();
        int id;
        try{
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException e){
            return Util.ERROR_REQUEST_CONTEXT;
        }
        Optional<MedicineDto> medicineDto = medicineService.findById(id);
        if(medicineDto.isPresent()){
            if(session.getAttribute("user") != null){
                if(medicineDto.get().getPrescriptionDrug()){
                    String login = ((UserDto)session.getAttribute("user")).getLogin();
                    Optional<User> user = userService.findByLogin(login);
                    if(user.isPresent()){
                        int userId = user.get().getId();
                        if(!prescriptionService.checkPrescription(medicineDto.get().getId(), userId)){
                            req.setAttribute("prescription", "needed");
                        }else {
                            req.setAttribute("prescription", "not needed");
                        }
                    }
                }else {
                    req.setAttribute("prescription", "not needed");
                }
            }else {
                if(medicineDto.get().getPrescriptionDrug()){
                    req.setAttribute("prescription", "needed");
                }else{
                    req.setAttribute("prescription", "not needed");
                }
            }
            req.setAttribute("medicine", medicineDto.get());
        }else{
            return Util.NOT_FOUND_REQUEST_CONTEXT;
        }
        return SUCCESS_CONTEXT;
    }
}
