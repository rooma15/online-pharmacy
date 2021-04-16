package com.epam.jwd.Servlet.service.impl;

import com.epam.jwd.Servlet.dao.impl.MedicineDAO;
import com.epam.jwd.Servlet.model.Medicine;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.model.Prescription;
import com.epam.jwd.Servlet.model.PrescriptionDto;
import com.epam.jwd.Servlet.service.CommonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * service for handling medicines
 */

public class MedicineService implements CommonService<MedicineDto> {

    private final MedicineDAO medicineDAO;

    /**
     * initializes medicine DAO {@link MedicineDAO}
     */
    public MedicineService(){
        medicineDAO = new MedicineDAO();
    }

    /**
     * finds all medicines
     * @return {@link List} list of all medicines
     */
    @Override
    public List<MedicineDto> findAll() {
        List<Medicine> medicines = new ArrayList<>();
         medicineDAO.findAll().forEach((elem) -> elem.ifPresent((medicines::add)));
        return medicines.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * delete medicine by concrete id
     * @param id id of medicine
     * @return true if medicine was deleted? false otherwise
     */
    @Override
    public boolean deleteById(int id) {
        return medicineDAO.deleteById(id);
    }


    /**
     * finds medicine by concrete id
     * @param id id of medicine
     * @return {@link Optional} of founded medicine {@link MedicineDto}, empty  {@link Optional} if not founded
     */
    @Override
    public Optional<MedicineDto> findById(int id) {
        return medicineDAO.findById(id).map(this::convertToDto);
    }


    /**
     * add medicine in database with data from given {@link MedicineDto} object
     * @param medicineDto
     * @return true if micine was added, false otherwise
     */
    public boolean create(MedicineDto medicineDto) {
        Medicine newMedicine = new Medicine(medicineDto.getName(),
                                        medicineDto.getDose(),
                                        medicineDto.getPrescriptionDrug(),
                                        medicineDto.getDescription(),
                                        medicineDto.getIndicationsForUse(),
                                        medicineDto.getContraindications(),
                                        medicineDto.getSideEffects(),
                                        medicineDto.getConsistency(),
                                        medicineDto.getComposition(),
                                        medicineDto.getPrice(),
                                        medicineDto.getCategory());
        return medicineDAO.create(newMedicine);
    }

    /**
     * updates information about concrete medicine in database
     * @param id id of medicine
     * @param medicineDto {@link MedicineDto} with new information
     * @return true if update was successful, false otherwise
     */
    public boolean updateById(int id, MedicineDto medicineDto){
        Medicine newMedicine = new Medicine(medicineDto.getName(),
                medicineDto.getDose(),
                medicineDto.getPrescriptionDrug(),
                medicineDto.getDescription(),
                medicineDto.getIndicationsForUse(),
                medicineDto.getContraindications(),
                medicineDto.getSideEffects(),
                medicineDto.getConsistency(),
                medicineDto.getComposition(),
                medicineDto.getPrice(),
                medicineDto.getCategory());
        return medicineDAO.updateById(id, newMedicine);
    }

    /**
     * coverts {@link Medicine} to {@link MedicineDto}
     * @param medicine to be converted
     * @return converted{@link MedicineDto}
     */
    private MedicineDto convertToDto(Medicine medicine){
        return new MedicineDto(medicine);
    }
}
