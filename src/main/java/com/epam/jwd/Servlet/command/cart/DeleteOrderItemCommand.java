package com.epam.jwd.Servlet.command.cart;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.service.impl.CartService;
import com.epam.jwd.Servlet.Util.Util;

public enum DeleteOrderItemCommand implements Command {
    INSTANCE;

    private final ResponseContext CONTEXT = new ResponseContext() {
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
    public boolean isUnregisteredClientForbidden() {
        return false;
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException e){
            return Util.ERROR_REQUEST_CONTEXT;
        }
        CartService cartService = new CartService(req);
        boolean status = cartService.deleteById(id);
        if(!status){
            Util.lOGGER.error("order item with id " + id + "  not deleted");
        }
        else {
            Util.lOGGER.info("order item with id " + id + "  was deleted");
        }
        return CONTEXT;
    }


}
