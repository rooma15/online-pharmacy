package com.epam.jwd.Servlet.dao.impl;

import com.epam.jwd.ConnectionPool.Impl.DBConnectionPool;
import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.CartItem;
import com.epam.jwd.Servlet.model.User;
import com.epam.jwd.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class CartDAO extends AbstractDAO<CartItem> {

    private String DELETE_BY_ID = "delete from pharmacy.Cart where id=?";
    private String GET_CART_ITEM_BY_ID = "select * from pharmacy.Cart where id=?";
    private String CREATE_CART_ITEM = "insert into pharmacy.Cart values(null, ?, ?, ?, ?)";

    private final Function<ResultSet, Optional<CartItem>> adder = resultSet -> {
        try {
            return Optional.of(new CartItem(resultSet));
        } catch (SQLException e) {
            return Optional.empty();
        }
    };

    @Override
    public List<Optional<CartItem>> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<CartItem> findById(int id) {
        return super.findById(id, GET_CART_ITEM_BY_ID, adder);
    }

    @Override
    public boolean deleteById(int id) {
        return super.deleteById(id, DELETE_BY_ID);
    }

    @Override
    public boolean create(CartItem item) {
        return AbstractDAO.updateByCriteria(CREATE_CART_ITEM, "iidi", item.getMedicineId(),
                                            item.getAmount(),
                                            item.getPrice(),
                                            item.getUserId());
    }
}
