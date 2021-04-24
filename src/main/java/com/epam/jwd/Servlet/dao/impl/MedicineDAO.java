package com.epam.jwd.Servlet.dao.impl;

import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.Medicine;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MedicineDAO extends AbstractDAO<Medicine> {


    private final String GET_MEDICINES = "select * from pharmacy.Medicines " +
            "join pharmacy.Images on pharmacy.Medicines.id=pharmacy.Images.medicine_id";
    private final String GET_MEDICINE_BY_ID = "select * from pharmacy.Medicines" +
            " join pharmacy.Images on pharmacy.Medicines.id=pharmacy.Images.medicine_id where id=?";
    private final String DELETE_MEDICINE_BY_ID = "delete from pharmacy.Medicines where id=?";
    private final String CREATE_MEDICINE = "insert into pharmacy.Medicines values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_MEDICINE = "update pharmacy.Medicines set name=?, " +
            "dose=?, prescription_drug=?, description=?, indications_for_use=?, contraindications=?," +
            "side_effects=?, consistency=?, composition=?, price=?, category=? where id=?";

    private final Function<ResultSet, Optional<Medicine>> adder = resultSet -> {
        try {
            return Optional.of(new Medicine(resultSet));
        } catch (SQLException e) {
            return Optional.empty();
        }
    };

    @Override
    public List<Optional<Medicine>> findAll(){
        return super.findAll(GET_MEDICINES, adder);
    }

    @Override
    public Optional<Medicine> findById(int id){
        return super.findById(id, GET_MEDICINE_BY_ID, adder);
    }

    @Override
    public boolean deleteById(int id) {
        return super.deleteById(id, DELETE_MEDICINE_BY_ID);
    }

    @Override
    public boolean create(Medicine medicine) {
        return AbstractDAO.updateByCriteria(CREATE_MEDICINE, "sibssssssds", medicine.getName(),
                                            medicine.getDose(),
                                            medicine.getPrescriptionDrug(),
                                            medicine.getDescription(),
                                            medicine.getIndicationsForUse(),
                                            medicine.getContraindications(),
                                            medicine.getSideEffects(),
                                            medicine.getConsistency(),
                                            medicine.getComposition(),
                                            medicine.getPrice(),
                                            medicine.getCategory());
    }

    public boolean updateById(int id, Medicine medicine){
        return AbstractDAO.updateByCriteria(UPDATE_MEDICINE, "sibssssssdsi", medicine.getName(),
                                            medicine.getDose(),
                                            medicine.getPrescriptionDrug(),
                                            medicine.getDescription(),
                                            medicine.getIndicationsForUse(),
                                            medicine.getContraindications(),
                                            medicine.getSideEffects(),
                                            medicine.getConsistency(),
                                            medicine.getComposition(),
                                            medicine.getPrice(),
                                            medicine.getCategory(),
                                            id);
    }
}
