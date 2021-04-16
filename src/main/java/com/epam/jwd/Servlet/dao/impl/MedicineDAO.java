package com.epam.jwd.Servlet.dao.impl;

import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.Medicine;
import com.epam.jwd.Util;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MedicineDAO extends AbstractDAO<Medicine> {


    private final String GET_MEDICINES = "select * from pharmacy.Medicines " +
            "join pharmacy.Images on pharmacy.Medicines.name=pharmacy.Images.medicine_name";
    private final String GET_MEDICINE_BY_ID = "select * from pharmacy.Medicines" +
            " join pharmacy.Images on pharmacy.Medicines.name=pharmacy.Images.medicine_name where id=?";
    private final String DELETE_MEDICINE_BY_ID = "delete from pharmacy.Medicines where id=?";
    private final String CREATE_MEDICINE = "insert into pharmacy.Medicines values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_MEDICINE = "update pharmacy.Medicines set name=?, " +
            "dose=?, prescription_drug=?, description=?, indications_for_use=?, contraindications=?," +
            "side_effects=?, consistency=?, composition=?, price=?, category=? where id=?";

    @Override
    public List<Optional<Medicine>> findAll(){
        Function<ResultSet, Optional<Medicine>> adder = resultSet -> {
            try {
                return Optional.of(new Medicine(resultSet));
            } catch (SQLException e) {
                return Optional.empty();
            }
        };
        return super.findAll(GET_MEDICINES, adder);
    }

    @Override
    public Optional<Medicine> findById(int id){
        Function<ResultSet, Optional<Medicine>> f = res -> {
            try {
                return Optional.of(new Medicine(res));
            } catch (SQLException e) {
                return Optional.empty();
            }
        };
        return super.findById(id, GET_MEDICINE_BY_ID, f);
    }

    @Override
    public boolean deleteById(int id) {
        return super.deleteById(id, DELETE_MEDICINE_BY_ID);
    }

    @Override
    public boolean create(Medicine medicine) {
        try (Connection dbConnection = connectionPool.getConnection()) {
            try (PreparedStatement statement = dbConnection.prepareStatement(CREATE_MEDICINE)) {
                statement.setString(1, medicine.getName());
                statement.setInt(2, medicine.getDose());
                statement.setBoolean(3, medicine.getPrescriptionDrug());
                statement.setString(4, medicine.getDescription());
                statement.setString(5, medicine.getIndicationsForUse());
                statement.setString(6, medicine.getContraindications());
                statement.setString(7, medicine.getSideEffects());
                statement.setString(8, medicine.getConsistency());
                statement.setString(9, medicine.getComposition());
                statement.setDouble(10, medicine.getPrice());
                statement.setString(11, medicine.getCategory());
                int rows = statement.executeUpdate();
                if(rows > 0){
                    return true;
                }else {
                    return false;
                }
            } catch (SQLException e) {
                Util.lOGGER.error(e.getStackTrace());
            }
        } catch (InterruptedException | SQLException e) {
            Util.lOGGER.error(e.getStackTrace());
        }
        return false;
    }

    public boolean updateById(int id, Medicine medicine){
        try (Connection dbConnection = connectionPool.getConnection()) {
            try (PreparedStatement statement = dbConnection.prepareStatement(UPDATE_MEDICINE)) {
                statement.setString(1, medicine.getName());
                statement.setInt(2, medicine.getDose());
                statement.setBoolean(3, medicine.getPrescriptionDrug());
                statement.setString(4, medicine.getDescription());
                statement.setString(5, medicine.getIndicationsForUse());
                statement.setString(6, medicine.getContraindications());
                statement.setString(7, medicine.getSideEffects());
                statement.setString(8, medicine.getConsistency());
                statement.setString(9, medicine.getComposition());
                statement.setDouble(10, medicine.getPrice());
                statement.setString(11, medicine.getCategory());
                statement.setInt(12, id);
                int rows = statement.executeUpdate();
                if(rows > 0){
                    return true;
                }else {
                    return false;
                }
            } catch (SQLException e) {
                Util.lOGGER.error(e.getStackTrace());
            }
        } catch (InterruptedException | SQLException e) {
            Util.lOGGER.error(e.getStackTrace());
        }
        return false;
    }
}
