package com.epam.jwd.Servlet.command.page;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.CartItemDto;
import com.epam.jwd.Servlet.model.UserDto;
import com.epam.jwd.Servlet.service.impl.CartService;
import com.epam.jwd.Servlet.service.impl.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;


public enum OpenCartCommand implements Command {
    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return false;
    }

    private final  ResponseContext SUCCESS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "cart.jsp";
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
        HttpSession session = req.getSession();
        CartService cartService = new CartService(req);
        if(session.getAttribute("user") == null){
            List<CartItemDto> orderItems = cartService.getSessionCartItems();
            req.setAttribute("order_items", orderItems);
        }else {
            UserService userService = new UserService();
            UserDto user = (UserDto)session.getAttribute("user");
            int userId = userService.findByLogin(user.getLogin()).get().getId();
            List<CartItemDto> orderItems = cartService.findByUserId(userId);
            req.setAttribute("order_items", orderItems);
        }
        return SUCCESS_CONTEXT;
    }
}
