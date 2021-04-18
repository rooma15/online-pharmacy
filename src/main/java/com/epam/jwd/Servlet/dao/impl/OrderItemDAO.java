package com.epam.jwd.Servlet.dao.impl;

import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.OrderItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class OrderItemDAO extends AbstractDAO<OrderItem> {

    private final String DELETE_BY_ID = "delete from pharmacy.Order_items where id=?";
    private final String GET_ORDER_ITEMS = "select * from pharmacy.Order_items";
    private final String GET_ORDER_ITEM_BY_ID = "select * from pharmacy.Order_items where id=?";
    private final String CREATE = "insert into pharmacy.Order_items values(null, ?, ?, ?, ?, ?, ?, ?)";
    private final Function<ResultSet, Optional<OrderItem>> adder = resultSet -> {
        try {
            return Optional.of(new OrderItem(resultSet));
        } catch (SQLException e) {
            return Optional.empty();
        }
    };


    @Override
    public List<Optional<OrderItem>> findAll() {
        return super.findAll(GET_ORDER_ITEMS, adder);
    }

    @Override
    public Optional<OrderItem> findById(int id) {
        return super.findById(id, GET_ORDER_ITEM_BY_ID, adder);
    }

    @Override
    public boolean deleteById(int id) {
        return AbstractDAO.updateByCriteria(DELETE_BY_ID, "i", id);
    }

    @Override
    public boolean create(OrderItem orderItem) {
        return AbstractDAO.updateByCriteria(CREATE, "iidsisi",
                orderItem.getOrderId(),
                orderItem.getAmount(),
                orderItem.getPrice(),
                orderItem.getName(),
                orderItem.getDose(),
                orderItem.getConsistency(),
                orderItem.getMedicineId()
                );
    }
}
