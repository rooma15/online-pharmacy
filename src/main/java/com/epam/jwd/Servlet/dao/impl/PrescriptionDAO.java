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
        return  findByCriteria(GET_ACCEPTED_PRESCRIPTIONS, "b", isAccepted);
    }

    public List<Prescription> findByCriteria(String st, String paramString, Object... params) {
        List<Prescription> prescriptions = new ArrayList<>();
        try (Connection dbConnection = DBConnectionPool.getInstance().getConnection()) {
            try (PreparedStatement statement = dbConnection.prepareStatement(st)) {
                for(int i = 0; i < paramString.length(); i++) {
                    char type = paramString.charAt(i);
                    switch (type){
                        case 'i': statement.setInt(i + 1, (int)params[i]);break;
                        case 's': statement.setString(i + 1, (String)params[i]);break;
                        case 'b': statement.setBoolean(i + 1, (boolean)params[i]);break;
                        case 'd': statement.setDouble(i + 1, (double)params[i]);break;
                    }
                }
                try (ResultSet result = statement.executeQuery()) {
                    while(result.next()) {
                        Prescription prescription =new Prescription(
                                result.getInt(1),
                                result.getInt(2),
                                result.getInt(3),
                                result.getBoolean(4)
                        );
                        prescriptions.add(prescription);
                    }
                } catch (SQLException e) {
                    Util.lOGGER.error(e.getStackTrace());
                }
            } catch (SQLException e) {
                Util.lOGGER.error(e.getStackTrace());
            }
        } catch (InterruptedException | SQLException e) {
            Util.lOGGER.error(e.getStackTrace());
        }
        return prescriptions;
    }

}
