package com.epam.jwd.Servlet.command.page;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.criteria.MedicineCriteria;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.service.FilterService;
import com.epam.jwd.Servlet.service.impl.MedicineFilterService;
import com.epam.jwd.Servlet.service.impl.MedicineService;

import java.util.Arrays;
import java.util.List;

public enum FilterCommand implements Command {

    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return false;
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        MedicineService medicineService = new MedicineService();
        List<MedicineDto> allMedicines = medicineService.findAll();
        MedicineCriteria.MedicineCriteriaBuilder medicineCriteriaBuilder = new
                MedicineCriteria.MedicineCriteriaBuilder();

        String catParam = req.getParameter("category");
        List<String> categories;
        if(!"".equals(catParam) && catParam != null){
            categories = Arrays.asList(catParam.split("_").clone());
            medicineCriteriaBuilder.categories(categories);
        }

        String consistencyParam = req.getParameter("consistency");
        List<String> consistencies;
        if(!"".equals(consistencyParam) && consistencyParam != null){
            consistencies = Arrays.asList(consistencyParam.split("_").clone());
            medicineCriteriaBuilder.consistencies(consistencies);
        }

        MedicineCriteria medicineCriteria = medicineCriteriaBuilder.build();

        MedicineFilterService filterService = new MedicineFilterService();
        List<MedicineDto> sortedMedicines = filterService.filter(medicineCriteria, allMedicines);

        req.setAttribute("allItems", sortedMedicines);
        return ShowMainPageCommand.INSTANCE.execute(req);
    }
}
