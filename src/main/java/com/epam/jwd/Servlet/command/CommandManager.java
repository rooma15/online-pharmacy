package com.epam.jwd.Servlet.command;

import com.epam.jwd.Servlet.command.cart.DeleteOrderItemCommand;
import com.epam.jwd.Servlet.command.locale.ChangeLocaleCommand;
import com.epam.jwd.Servlet.command.medicine.AddMedicineCommand;
import com.epam.jwd.Servlet.command.medicine.BuyMedicineCommand;
import com.epam.jwd.Servlet.command.medicine.DeleteMedicineCommand;
import com.epam.jwd.Servlet.command.medicine.UpdateMedicineCommand;
import com.epam.jwd.Servlet.command.page.*;
import com.epam.jwd.Servlet.command.prescription.CreatePrescriptionCommand;
import com.epam.jwd.Servlet.command.prescription.EditPrescriptionsCommand;
import com.epam.jwd.Servlet.command.user.DeleteUserCommand;
import com.epam.jwd.Servlet.command.user.LoginUserCommand;
import com.epam.jwd.Servlet.command.user.LogoutUserCommand;
import com.epam.jwd.Servlet.command.user.RegisterUserCommand;

public enum CommandManager {
    ADMIN_DELETE_USER(DeleteUserCommand.INSTANCE),
    ADMIN_REGISTER_USER(RegisterUserCommand.INSTANCE),
    REGISTER_USER(RegisterUserCommand.INSTANCE),
    MAIN_PAGE(ShowMainPageCommand.INSTANCE),
    ADMIN_ADD_MEDICINE(AddMedicineCommand.INSTANCE),
    LOGIN(LoginUserCommand.INSTANCE),
    LOG_OUT(LogoutUserCommand.INSTANCE),
    BUY_MEDICINE(BuyMedicineCommand.INSTANCE),
    ADMIN_EDIT_USER_PAGE(ShowEditUsersPageCommand.INSTANCE),
    ADMIN_SHOW_CREATE_MEDICINE_PAGE(ShowCreateMedicinePageCommand.INSTANCE),
    ADMIN_SHOW_ALL_MEDICINES(ShowAllMedicinesCommand.INSTANCE),
    ADMIN_DELETE_MEDICINE(DeleteMedicineCommand.INSTANCE),
    ADMIN_SHOW_UPDATE_MEDICINE_PAGE(ShowUpdateMedicinePageCommand.INSTANCE),
    ADMIN_UPDATE_MEDICINE(UpdateMedicineCommand.INSTANCE),
    SHOW_MEDICINE_PAGE(ShowMedicinePageCommand.INSTANCE),
    CREATE_PRESCRIPTION(CreatePrescriptionCommand.INSTANCE),
    SHOW_DOCTOR_PAGE(ShowDoctorPageCommand.INSTANCE),
    DOCTOR_EDIT_PRESCRIPTIONS(EditPrescriptionsCommand.INSTANCE),
    OPEN_CART(OpenCartCommand.INSTANCE),
    DELETE_ORDER_ITEM(DeleteOrderItemCommand.INSTANCE),
    CATEGORY_PAGE(ShowCategoryPageCommand.INSTANCE),
    CHANGE_LOCALE(ChangeLocaleCommand.INSTANCE);

     private final Command command;

     CommandManager(Command command){
         this.command = command;
     }

    static Command of(String name){
        for(CommandManager action : values()){
            if(action.name().equalsIgnoreCase(name)){
                return action.command;
            }
        }
        return ShowMainPageCommand.INSTANCE;
    }
}
