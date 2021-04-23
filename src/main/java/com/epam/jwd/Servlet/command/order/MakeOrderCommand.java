package com.epam.jwd.Servlet.command.order;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.*;
import com.epam.jwd.Servlet.paymentMethodStrategy.PaymentMethod;
import com.epam.jwd.Servlet.service.PaymentService;
import com.epam.jwd.Servlet.service.impl.CartService;
import com.epam.jwd.Servlet.service.impl.OrderService;
import com.epam.jwd.Servlet.service.impl.UserService;
import com.epam.jwd.Servlet.Util.Util;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

public enum MakeOrderCommand implements Command {
    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }


    private final ResponseContext SUCCESS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "successPayment.jsp";
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

    private final ResponseContext ERROR_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "errorPayment.jsp";
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
        HttpSession session = req.getSession();
        CartService cartService = new CartService(req);

        UserService userService = new UserService();

        OrderService orderService = new OrderService();

        PaymentService paymentService = new PaymentService(req);

        UserDto userDto = (UserDto) session.getAttribute("user");
        User user = userService.findByLogin(userDto.getLogin()).get();

        List<CartItemDto> cartItems = cartService.findByUserId(user.getId());

        if(cartItems.isEmpty()){
            return Util.ERROR_REQUEST_CONTEXT;
        }

        String paymentMethodParam = req.getParameter("method");
        PaymentMethod paymentMethod = PaymentMethod.of(paymentMethodParam);
        double orderPrice = OrderService.calculateOrderPrice(cartItems);
        if(paymentService.pay(paymentMethod, orderPrice)){
            Order order = new Order(
                    user.getId(),
                    new Timestamp(System.currentTimeMillis()),
                    orderPrice
            );
            if(orderService.createOrder(user, order, cartItems)) {
                cartService.clearCart();
                return SUCCESS_CONTEXT;
            } else {
                return ERROR_CONTEXT;
            }
        }else {
            return ERROR_CONTEXT;
        }
    }
}
