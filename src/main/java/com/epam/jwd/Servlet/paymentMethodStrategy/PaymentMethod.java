package com.epam.jwd.Servlet.paymentMethodStrategy;

import com.epam.jwd.Servlet.command.RequestContext;

public interface PaymentMethod {
    boolean pay(RequestContext req, double price);
}
