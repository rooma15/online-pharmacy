package com.epam.jwd.Servlet.dao.impl;

import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.User;

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
    private final Function<ResultSet, Optional<User>> adder = resultSet -> {
        try {
            return Optional.of(new User(resultSet));
        } catch (SQLException e) {
            return Optional.empty();
        }
    };

    @Override
    public List<Optional<User>> findAll() {
        return super.findAll(GET_USERS, adder);
    }


    @Override
    public Optional<User> findById(int id) {
        return super.findById(id, GET_USER_BY_ID, adder);
    }

    @Override
    public boolean deleteById(int id) {
        return super.deleteById(id, DELETE_USER_BY_ID);
    }

    @Override
    public boolean create(User user) {
        return AbstractDAO.updateByCriteria(CREATE_USER, "sssi",
                user.getLogin(), user.getPassword(), user.getName(), user.getRole().getId());
    }

    public Optional<User> findByLogin(String login){
        List<Optional<User>> items = AbstractDAO.
                <User>findByCriteria(GET_USER_BY_LOGIN, "s", adder, login);
        if(items.isEmpty()){
            return Optional.empty();
        } else {
            return items.get(0);
        }
    }
}
