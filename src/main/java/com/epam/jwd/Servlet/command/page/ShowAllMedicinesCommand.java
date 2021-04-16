package com.epam.jwd.Servlet.command.page;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.service.impl.MedicineService;

import java.util.List;

public enum ShowAllMedicinesCommand implements Command {
    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }

    private final ResponseContext CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "admin/editMedicines.jsp";
        }

        @Override
        public String getErrorCode() {
            return null;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public boolean isAjax() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        MedicineService service = new MedicineService();
        List<MedicineDto> medicines = service.findAll();
        req.setAttribute("medicines", medicines);
        req.setAttribute("choice", "update-delete");
        return CONTEXT;
    }
}
