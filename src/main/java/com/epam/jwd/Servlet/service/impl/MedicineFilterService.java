package com.epam.jwd.Servlet.service.impl;
import com.epam.jwd.Servlet.criteria.MedicineCriteria;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.service.FilterService;

import java.util.List;
import java.util.stream.Collectors;

public class MedicineFilterService implements FilterService<MedicineCriteria, MedicineDto> {
    @Override
    public List<MedicineDto> filter(MedicineCriteria criteria, List<MedicineDto> items) {
        List<MedicineDto> sortedMedicines = items;
        List<String> categories = criteria.getCategories();
        List<String> consistencies = criteria.getConsistencies();
        if(categories != null){
            sortedMedicines = sortedMedicines
                    .stream()
                    .filter(medicine -> categories.contains(medicine.getCategory().toLowerCase()))
                    .collect(Collectors.toList());
        }
        if(consistencies != null){
            sortedMedicines = sortedMedicines
                    .stream()
                    .filter(medicine -> consistencies.contains(medicine.getConsistency().toLowerCase()))
                    .collect(Collectors.toList());
        }
        return sortedMedicines;
    }
}
