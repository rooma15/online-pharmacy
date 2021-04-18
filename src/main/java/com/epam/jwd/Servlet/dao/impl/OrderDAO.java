package com.epam.jwd.Servlet.dao.impl;

import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.CartItem;
import com.epam.jwd.Servlet.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class OrderDAO extends AbstractDAO<Order> {

    private final String GET_ORDERS = "select * from pharmacy.Orders";
    private final String GET_ORDER_BY_ID = "select * from Orders where id=?";
    private final String DELETE_ORDER_BY_ID = "delete from Orders where id=?";
    private final String CREATE_ORDER = "insert into pharmacy.Orders values(null, ?, ?, ?)";

    private final Function<ResultSet, Optional<Order>> adder = resultSet -> {
        try {
            return Optional.of(new Order(resultSet));
        } catch (SQLException e) {
            return Optional.empty();
        }
    };

    @Override
    public List<Optional<Order>> findAll() {
        return super.findAll(GET_ORDERS, adder);
    }

    @Override
    public Optional<Order> findById(int id) {
        return super.findById(id, GET_ORDER_BY_ID, adder);
    }

    @Override
    public boolean deleteById(int id) {
        return super.deleteById(id, DELETE_ORDER_BY_ID);
    }

    @Override
    public boolean create(Order order) {
        return AbstractDAO.updateByCriteria(CREATE_ORDER, "itd", order.getUserId(),
                                            order.getOrderDate(),
                                            order.getOrderPrice());
    }
}
