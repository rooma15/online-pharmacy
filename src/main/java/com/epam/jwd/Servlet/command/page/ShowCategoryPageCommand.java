package com.epam.jwd.Servlet.command.page;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.service.impl.MedicineService;
import com.epam.jwd.Util;

import java.util.List;
import java.util.stream.Collectors;

public enum ShowCategoryPageCommand implements Command {

    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return false;
    }

    private final ResponseContext MAIN_PAGE_CONTEXT = new ResponseContext() {
        @Override
        public boolean isAjax() {
            return false;
        }

        @Override
        public String getPage() {
            return "index.jsp";
        }

        @Override
        public String getErrorCode() {
            return null;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        String category = req.getParameter("category");
        if(category != null){
            MedicineService medicineService = new MedicineService();
            List<MedicineDto> allMedicines = medicineService.findAll();
            List<String> categories = AbstractDAO.getOptions("select category from pharmacy.Categories");
            boolean categoryExists = false;
            for(String value : categories) {
                if(value.equalsIgnoreCase(category)){
                    categoryExists = true;
                }
            }
            if(categoryExists){
                List<MedicineDto> sortedMedicines = allMedicines.stream()
                        .filter(medicine -> category.equalsIgnoreCase(medicine.getCategory()))
                        .collect(Collectors.toList());

                req.setAttribute("allItems", sortedMedicines);
            }else{
                return Util.NOT_FOUND_REQUEST_CONTEXT;
            }
        }else {
            ShowMainPageCommand.INSTANCE.execute(req);
        }
        return MAIN_PAGE_CONTEXT;
    }
}
