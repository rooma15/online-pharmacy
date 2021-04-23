package com.epam.jwd.Servlet.command.page;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.Util.Util;

public enum ShowPaymentPageCommand implements Command {

    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }

    private final ResponseContext CARD_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "WEB-INF/jsp/cardPayment.jsp";
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
        String methodParam = req.getParameter("method");
        if(methodParam == null || methodParam.equals("")){
            return Util.ERROR_REQUEST_CONTEXT;
        }
        if(methodParam.equalsIgnoreCase("card")){
            return CARD_CONTEXT;
        }else {
            return Util.ERROR_REQUEST_CONTEXT;
        }
    }
}
