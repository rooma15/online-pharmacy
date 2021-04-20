package com.epam.jwd.Servlet.paymentMethodStrategy.impl;

import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.paymentMethodStrategy.PaymentMethod;
import com.epam.jwd.Util;
import com.epam.jwd.exception.EmptyRequestException;
import com.epam.jwd.httpSender.HttpSender;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public enum CardPaymentMethod implements PaymentMethod {
    INSTANCE;

    private final String CARD_NUMBER_REGEX = "^[0-9]{16}$";
    private final String NAME_REGEX = "^[A-Z]+\\s[A-Z]+$";

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

        if(validCredentials(req, cardNumber, name, cvv)){
            Map<String, String> params = new HashMap<>();
            params.put("cardNumber", cardNumber);
            params.put("name", name);
            params.put("cvv", String.valueOf(cvv));
            HttpSender httpSender = new HttpSender();
            try {
                httpSender.setHttpRequest(
                        HttpSender.newBuilder()
                        .headers("Content-Type", "text/plain; charset=UTF-8")
                        .uri("http://localhost:8080/BankController")
                        .POST(params)
                        .build());

            }catch (JsonProcessingException e){
                Util.lOGGER.error(e.getMessage());
                return false;
            }
            try {
                HttpResponse<String> httpResponse = httpSender.send();
                System.out.println(httpResponse.statusCode());
                return true;
            }catch (IOException | InterruptedException | EmptyRequestException e){
                Util.lOGGER.error(e.getMessage());
                return false;
            }
        }else{
            return false;
        }
    }
}


