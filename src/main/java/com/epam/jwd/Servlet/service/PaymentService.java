package com.epam.jwd.Servlet.service;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.model.CartItemDto;
import com.epam.jwd.Servlet.model.Order;
import com.epam.jwd.Servlet.model.User;
import com.epam.jwd.Servlet.paymentMethodStrategy.PaymentMethod;
import com.epam.jwd.Servlet.paymentMethodStrategy.impl.CardPaymentMethod;
import com.epam.jwd.Servlet.service.impl.CartService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class PaymentService{

    private final RequestContext req;
    public PaymentService(RequestContext req) {
        this.req = req;
    }


    public static PaymentMethod getPaymentMethod(String method){
        if(method != null) {
            if(method.equalsIgnoreCase("card")) {
                return CardPaymentMethod.INSTANCE;
            }
        }
        return CardPaymentMethod.INSTANCE;
    }

    /**
     * start a payment procedure due to method of payment
     * @param method method of payment
     * @param user user who makes a payment
     * @return {@link Optional} of {@link Order} if payment was successful, {@link Optional#empty()} otherwise
     */
    public Optional<Order> pay(PaymentMethod method, User user){
        CartService cartService = new CartService(req);
        List<CartItemDto> cartItems = cartService.findByUserId(user.getId());
        double orderPrice = 0;
        for(CartItemDto cartItem : cartItems) {
            orderPrice += cartItem.getPrice();
        }
        if(method.pay(req, orderPrice)){
            return Optional.of(new Order(
                    user.getId(),
                    new Timestamp(System.currentTimeMillis()),
                    orderPrice
            ));
        }else {
            return Optional.empty();
        }
    }
}
