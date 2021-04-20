package com.epam.jwd.Servlet.service;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.paymentMethodStrategy.PaymentMethod;
public class PaymentService{

    private final RequestContext req;
    public PaymentService(RequestContext req) {
        this.req = req;
    }


    /**
     * start a payment procedure according to method of payment
     * @param method method of payment
     * @return true if payment was successful, false otherwise
     */
    public boolean pay(PaymentMethod method, double orderPrice){
        return method.pay(req, orderPrice);
    }
}
