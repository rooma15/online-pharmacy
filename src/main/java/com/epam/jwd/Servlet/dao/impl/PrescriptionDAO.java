package com.epam.jwd.Servlet.dao.impl;

import com.epam.jwd.ConnectionPool.Impl.DBConnectionPool;
import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.Prescription;
import com.epam.jwd.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PrescriptionDAO extends AbstractDAO<Prescription> {

    private final String GET_ALL_PRESCRIPTIONS = "select * from pharmacy.Prescriptions";
    private final String GET_PRESCRIPTION_BY_ID = "select * from pharmacy.Prescriptions where id=?";
    private final String DELETE_PRESCRIPTION_BY_ID = "delete from pharmacy.Prescriptions where id=?";
    private final String CREATE_PRESCRIPTION = "insert into pharmacy.Prescriptions values(null, ?, ?, ?)";
    private final String GET_ACCEPTED_PRESCRIPTIONS = "select * from pharmacy.Prescriptions where is_accepted=?";
    private final String GET_PRESCRIPTIONS_BY_USER_ID = "select * from pharmacy.Prescriptions where user_id=?";

    @Override
    public List<Optional<Prescription>> findAll() {
        Function<ResultSet, Optional<Prescription>> adder = resultSet -> {
            try {
                return Optional.of(new Prescription(resultSet));
            } catch (SQLException e) {
                return Optional.empty();
            }
        };
        return super.findAll(GET_ALL_PRESCRIPTIONS, adder);
    }

    @Override
    public Optional<Prescription> findById(int id) {
        Function<ResultSet, Optional<Prescription>> adder = res -> {
            try {
                return Optional.of(new Prescription(res));
            } catch (SQLException e) {
                return Optional.empty();
            }
        };
        return super.findById(id, GET_PRESCRIPTION_BY_ID, adder);
    }

    @Override
    public boolean deleteById(int id) {
        return super.deleteById(id, DELETE_PRESCRIPTION_BY_ID);
    }

    @Override
    public boolean create(Prescription prescription) {
        try (Connection dbConnection = connectionPool.getConnection()) {
            try (PreparedStatement statement = dbConnection.prepareStatement(CREATE_PRESCRIPTION)) {
                statement.setInt(1, prescription.getUserId());
                statement.setInt(2, prescription.getMedicineId());
                statement.setBoolean(3, prescription.getAccepted());
                int rows = statement.executeUpdate();
                if(rows > 0) {
                    return true;
                } else {
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




    public List<Prescription> findAccepted(boolean isAccepted) {
        Function<ResultSet, Optional<Prescription>> adder = resultSet -> {
            try {
                return Optional.of(new Prescription(resultSet));
            } catch (SQLException e) {
                return Optional.empty();
            }
        };

        return AbstractDAO.<Prescription>findByCriteria(GET_ACCEPTED_PRESCRIPTIONS, "b", adder, isAccepted)
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
