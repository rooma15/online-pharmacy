package com.epam.jwd.Servlet.dao.impl;

import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.User;
import com.epam.jwd.Servlet.model.UserRole;
import com.epam.jwd.Util;
import org.apache.commons.text.StringEscapeUtils;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserDAO extends AbstractDAO<User> {


    private final String GET_USERS = "select * from pharmacy.Users";
    private final String GET_USER_BY_ID = "select * from pharmacy.Users where id=?";
    private final String DELETE_USER_BY_ID = "delete from pharmacy.Users where id=?";
    private final String CREATE_USER = "insert into pharmacy.Users values(null, ?, ?, ?, ?)";
    private final String GET_USER_BY_LOGIN = "select * from pharmacy.Users where login=?";

    @Override
    public List<Optional<User>> findAll() {
        Function<ResultSet, Optional<User>> adder = resultSet -> {
            try {
                return Optional.of(new User(resultSet));
            } catch (SQLException e) {
                return Optional.empty();
            }
        };
        return super.findAll(GET_USERS, adder);
    }


    @Override
    public Optional<User> findById(int id) {
        Function<ResultSet, Optional<User>> f = res -> {
            try {
                return Optional.of(new User(res));
            } catch (SQLException e) {
                return Optional.empty();
            }
        };
        return super.findById(id, GET_USER_BY_ID, f);
    }

    @Override
    public boolean deleteById(int id) {
        return super.deleteById(id, DELETE_USER_BY_ID);
    }

    @Override
    public boolean create(User user) {

        try (Connection dbConnection = connectionPool.getConnection()) {
            try (PreparedStatement statement = dbConnection.prepareStatement(CREATE_USER)) {
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getName());
                statement.setInt(4, user.getRole().getId());
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

    public Optional<User> findByLogin(String login){
        login = StringEscapeUtils.escapeHtml4(login);
        Optional<User> user = Optional.empty();
        try (Connection dbConnection = connectionPool.getConnection()) {
            try (PreparedStatement statement = dbConnection.prepareStatement(GET_USER_BY_LOGIN)) {
                statement.setString(1, login);
                try (ResultSet result = statement.executeQuery()) {
                    while(result.next()) {
                        user = Optional.of(new User(result.getInt(1),
                                result.getString(2),
                                result.getString(3),
                                result.getString(4),
                                UserRole.of(result.getInt(5))));
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
        return user;
    }
}
