package com.epam.jwd.Servlet.paymentMethodStrategy.impl;

import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.paymentMethodStrategy.PaymentMethod;
import com.epam.jwd.Util;
import java.util.Enumeration;

public enum CardPaymentMethod implements PaymentMethod {
    INSTANCE;

    private final String CARD_NUMBER_REGEX = "^[0-9]{16}$";
    private final String NAME_REGEX = "^[a-z ]+$";

    private boolean validCredentials(RequestContext req, String cardNumber, String name, int cvv){
        Enumeration<String> params = req.getParameterNames();
        while(params.hasMoreElements()) {
            String paramName = params.nextElement();
            if(req.getParameter(paramName) == null) {
                return false;
            }
        }

        cardNumber = cardNumber.replaceAll(" ", "");
        name = name.trim();

        if(!cardNumber.matches(CARD_NUMBER_REGEX)){
            return false;
        }

        if(!name.matches(NAME_REGEX)){
            return false;
        }

        if(cvv > 1000 || cvv < 100){
            return false;
        }
        return true;
    }

    @Override
    public boolean pay(RequestContext req, double price) {
        String cardNumber = req.getParameter("cardNumber");
        String name = req.getParameter("name");
        int cvv = 0;
        try {
            cvv = Integer.parseInt(req.getParameter("cvv"));
        }catch (NumberFormatException e){
            Util.lOGGER.error(e.getMessage());
            return false;
        }

        return validCredentials(req, cardNumber, name, cvv);
    }
}
