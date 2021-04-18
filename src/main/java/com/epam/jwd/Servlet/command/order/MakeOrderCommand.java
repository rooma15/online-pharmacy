package com.epam.jwd.Servlet.command.order;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.*;
import com.epam.jwd.Servlet.service.impl.CartService;
import com.epam.jwd.Servlet.service.impl.OrderItemService;
import com.epam.jwd.Servlet.service.impl.OrderService;
import com.epam.jwd.Servlet.service.impl.UserService;
import com.epam.jwd.Util;

import javax.servlet.http.HttpSession;
import javax.swing.event.MouseInputListener;
import java.sql.Timestamp;
import java.util.List;

public enum MakeOrderCommand implements Command {
    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }

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
    public ResponseContext execute(RequestContext req) {
        HttpSession session = req.getSession();
        CartService cartService = new CartService(req);
        UserService userService = new UserService();
        UserDto userDto = (UserDto)session.getAttribute("user");
        User user = userService.findByLogin(userDto.getLogin()).get();
        List<CartItemDto> cartItems = cartService.findByUserId(user.getId());
        double orderPrice = 0;
        if(cartItems.isEmpty()){
            return Util.ERROR_REQUEST_CONTEXT;
        }
        for(CartItemDto cartItem : cartItems) {
            orderPrice += cartItem.getPrice();
        }
        OrderItemService orderItemService = new OrderItemService();
        OrderService orderService = new OrderService();
        orderService.createOrder(new Order(
                user.getId(),
                new Timestamp(System.currentTimeMillis()),
                orderPrice
        ));
        int orderId = orderService.getLastOrderId(user.getId());
        orderId = orderId == 0 ? 1 : orderId;
        for(CartItemDto cartItem : cartItems) {
            orderItemService.create(new OrderItem(
                    orderId,
                    cartItem.getAmount(),
                    cartItem.getPrice(),
                    cartItem.getMedicineName(),
                    cartItem.getMedicineDose(),
                    cartItem.getMedicineConsistency(),
                    cartItem.getMedicineId()
            ));
        }
        cartService.clearCart();
        return CONTEXT;
    }
}
