package com.epam.jwd.Servlet.paymentMethodStrategy;

import com.epam.jwd.Servlet.paymentMethodStrategy.impl.CardPaymentMethod;

public enum PaymentMethodManager {
    CARD(CardPaymentMethod.INSTANCE);

    PaymentMethod method;

    PaymentMethodManager(PaymentMethod method){
            this.method = method;
    }

    static PaymentMethod of(String method){
        for(PaymentMethodManager value : values()){
            if(value.name().equalsIgnoreCase(method)){
                return value.method;
            }
        }
        return CardPaymentMethod.INSTANCE;
    }
}
