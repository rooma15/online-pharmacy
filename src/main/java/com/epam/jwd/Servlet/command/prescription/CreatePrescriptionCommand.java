package com.epam.jwd.Servlet.command.prescription;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.User;
import com.epam.jwd.Servlet.model.UserDto;
import com.epam.jwd.Servlet.service.impl.PrescriptionService;
import com.epam.jwd.Servlet.service.impl.UserService;
import com.epam.jwd.Servlet.Util.Util;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.ResourceBundle;

public enum CreatePrescriptionCommand implements Command {
    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }

    private final ResponseContext SUCCESS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "index.jsp";
        }

        @Override
        public boolean isRedirect() {
            return true;
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
        String loc = (String)req.getSession().getAttribute("locale");
        ResourceBundle locale;
        if(loc != null){
            locale = Util.getLocaleBundle((String)req.getSession().getAttribute("locale"));
        }else {
            locale = Util.getLocaleBundle("ru_RU");
        }
        int medicineId;
        try{
            medicineId = Integer.parseInt(req.getParameter("medicineId"));
        }catch (NumberFormatException e){
            return Util.ERROR_REQUEST_CONTEXT;
        }

        ResponseContext ERROR_CONTEXT = new ResponseContext() {
            @Override
            public String getPage() {
                return "/Controller?action=show_medicine_page&id=" + medicineId;
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

        HttpSession session = req.getSession();


        String userLogin = ((UserDto)session.getAttribute("user")).getLogin();
        String login = req.getParameter("login");
        if(!userLogin.equals(login)){
            return Util.ERROR_REQUEST_CONTEXT;
        }


        UserService userService = new UserService();
        Optional<User> user = userService.findByLogin(login);
        if(user.isPresent()){
            int userId = user.get().getId();
            PrescriptionService prescriptionService = new PrescriptionService();
            if(prescriptionService.findByUserIdMedicineId(userId, medicineId).isPresent()){
                req.setAttribute("error", locale.getString("prescription.recipeExists"));
                return ERROR_CONTEXT;
            }
            boolean status = prescriptionService.create(medicineId, userId);
            if(!status){
                req.setAttribute("error", locale.getString("prescription.error"));
                return ERROR_CONTEXT;
            }
        }else{
            return Util.NOT_FOUND_REQUEST_CONTEXT;
        }
        return SUCCESS_CONTEXT;
    }
}
