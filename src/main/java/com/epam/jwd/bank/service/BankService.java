package com.epam.jwd.bank.service;


import com.epam.jwd.Servlet.dao.AbstractDAO;

import java.util.Map;


public class BankService {

    private final String PAY_TRANSACTION = "update bank.accounts set balance=balance-? where card_number=? and name=? and cvv=?";

    public boolean makePaymentTransaction(Map<String, String> credentialsMap){
        String cardNumber = credentialsMap.get("cardNumber");
        String name = credentialsMap.get("name");
        String cvv = credentialsMap.get("cvv");
        double change = Double.parseDouble(credentialsMap.get("change"));
        return AbstractDAO.updateByCriteria(PAY_TRANSACTION, "dsss", change, cardNumber, name, cvv);
    }
}
