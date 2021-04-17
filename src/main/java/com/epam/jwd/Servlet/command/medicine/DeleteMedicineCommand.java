package com.epam.jwd.Servlet.command.medicine;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.service.impl.MedicineService;
import com.epam.jwd.Util;

public enum DeleteMedicineCommand implements Command {
    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }

    private final ResponseContext SUCCESS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "/Controller?action=admin_show_all_medicines";
        }

        @Override
        public String getErrorCode() {
            return null;
        }

        @Override
        public boolean isRedirect() {
            return true;
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

    @Override
    public ResponseContext execute(RequestContext req) {
        MedicineService service = new MedicineService();
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException e){
            return ERROR_REQUEST_CONTEXT;
        }
        boolean status = service.deleteById(id);
        if(!status){
            return Util.NOT_FOUND_REQUEST_CONTEXT;
        }
        return SUCCESS_CONTEXT;
    }
}
