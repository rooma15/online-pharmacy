package com.epam.jwd.Servlet.command.prescription;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.service.impl.PrescriptionService;
import com.epam.jwd.Servlet.Util.Util;

public enum EditPrescriptionsCommand implements Command {
    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }

    private final ResponseContext SUCCESS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "/Controller?action=show_doctor_page";
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
        String method = req.getParameter("method");
        if(method == null){
            return Util.ERROR_REQUEST_CONTEXT;
        }
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException e){
            return Util.ERROR_REQUEST_CONTEXT;
        }
        PrescriptionService prescriptionService = new PrescriptionService();
        if(method.equals("delete")){
            boolean status = prescriptionService.deleteById(id);
            if(!status){
                return Util.NOT_FOUND_REQUEST_CONTEXT;
            }
        }else if(method.equals("accept")){
            boolean status = prescriptionService.setAccepted(id);
            if(!status){
                return Util.NOT_FOUND_REQUEST_CONTEXT;
            }
        }else {
            return Util.ERROR_REQUEST_CONTEXT;
        }
        return SUCCESS_CONTEXT;
    }
}
