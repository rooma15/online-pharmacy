package com.epam.jwd.Servlet.command.medicine;

import com.epam.jwd.Servlet.command.AjaxResponseContext;
import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.model.UserDto;
import com.epam.jwd.Servlet.service.impl.CartService;
import com.epam.jwd.Servlet.service.impl.MedicineService;
import com.epam.jwd.Servlet.service.impl.PrescriptionService;
import com.epam.jwd.Servlet.service.impl.UserService;
import com.epam.jwd.Servlet.Util.Util;
import com.google.gson.Gson;


import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public enum BuyMedicineCommand implements Command {

    INSTANCE;


    @Override
    public boolean isUnregisteredClientForbidden() {
        return false;
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        String loc = (String)req.getSession().getAttribute("locale");
        ResourceBundle locale;
        if(loc != null){
            locale = Util.getLocaleBundle((String)req.getSession().getAttribute("locale"));
        }else {
            locale = Util.getLocaleBundle("ru_RU");
        }
        HttpSession session = req.getSession();
        Map<String, String> answer = new HashMap<String, String>();
        int id;
        int amount;
        try {
            if(req.getParameter("amount") != null){
                amount = Integer.parseInt(req.getParameter("amount"));
                if(amount < 1){
                    return Util.ERROR_REQUEST_CONTEXT;
                }
            }else {
                amount = 1;
            }
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException e){
            return Util.ERROR_REQUEST_CONTEXT;
        }
        final ResponseContext CONTEXT;
        CartService cartService = new CartService(req);
        MedicineService medicineService = new MedicineService();
        Optional<MedicineDto> medicine = medicineService.findById(id);

        if(medicine.isPresent()){
            if(medicine.get().getPrescriptionDrug()){
                if(session.getAttribute("user") == null){
                    answer.put("message", locale.getString("buyMesg.noReg"));
                }else {
                    UserService userService = new UserService();
                    UserDto user = (UserDto)session.getAttribute("user");
                    int userId = userService.findByLogin(user.getLogin()).get().getId();
                    PrescriptionService prescriptionService = new PrescriptionService();
                    if(prescriptionService.checkPrescription(medicine.get().getId(), userId)){
                        boolean isAdded = cartService.addToCart(medicine.get(), amount);
                        if(isAdded){
                            answer.put("message", locale.getString("buyMesg.successAdded"));
                        }else {
                            answer.put("message", locale.getString("buyMesg.errorAdd"));
                        }
                    }else {
                        answer.put("message", locale.getString("buyMesg.noRecipe"));
                    }
                }
            }else{
                boolean isAdded = cartService.addToCart(medicine.get(), amount);
                if(isAdded){
                    answer.put("message", locale.getString("buyMesg.successAdded"));
                }else {
                    answer.put("message", locale.getString("buyMesg.errorAdd"));
                }
            }
        }else {
            return Util.NOT_FOUND_REQUEST_CONTEXT;
        }
        String json = new Gson().toJson(answer);
        CONTEXT = new AjaxResponseContext(json);
        return CONTEXT;
    }

}
