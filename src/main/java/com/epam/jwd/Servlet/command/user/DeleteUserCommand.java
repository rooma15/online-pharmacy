package com.epam.jwd.Servlet.command.user;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.*;
import com.epam.jwd.Servlet.service.impl.*;
import com.epam.jwd.Servlet.Util.Util;

import java.util.*;
import java.util.stream.Collectors;


public enum DeleteUserCommand implements Command {

    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }

    private final ResponseContext DELETE_USER_CONTEXT = new ResponseContext() {
        @Override
        public boolean isAjax() {
            return false;
        }

        @Override
        public String getPage() {
            return "Controller?action=admin_edit_user_page";
        }

        @Override
        public String getErrorCode() {
            return null;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };


    @Override
    public ResponseContext execute(RequestContext req) {
        UserService service = new UserService();
        String login = req.getParameter("login");
        if(login == null){
            return Util.ERROR_REQUEST_CONTEXT;
        }
        Optional<User> user = service.findByLogin(login);
        if(user.isPresent()){
            PrescriptionService prescriptionService = new PrescriptionService();
            List<Prescription> userPrescriptions = prescriptionService.findByUserId(user.get().getId());
            for(Prescription userPrescription : userPrescriptions) {
                prescriptionService.deleteById(userPrescription.getId());
            }
            CartService cartService = new CartService(req);
            cartService.clearCart(user.get().getId());
            OrderService orderService = new OrderService();
            orderService.deleteFullOrders(user.get().getId());
            boolean status = service.deleteById(user.get().getId());
            return DELETE_USER_CONTEXT;
        }else{
            return Util.NOT_FOUND_REQUEST_CONTEXT;
        }
    }
}
