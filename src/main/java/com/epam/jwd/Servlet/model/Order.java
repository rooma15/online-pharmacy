package com.epam.jwd.Servlet.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Order extends Entity{
    private final int userId;
    private final Timestamp orderDate;
    private final double orderPrice;

    public Order(int id, int userId, Timestamp orderDate, double orderPrice) {
        super(id);
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
    }

    public Order(int userId, Timestamp orderDate, double orderPrice) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
    }

    public Order(ResultSet resultSet) throws SQLException {
        super(resultSet.getInt(1));
        this.userId = resultSet.getInt(2);
        this.orderDate = resultSet.getTimestamp(3);
        this.orderPrice = resultSet.getDouble(4);
    }

    public int getUserId() {
        return userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public int getId(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return userId == order.userId &&
                Double.compare(order.orderPrice, orderPrice) == 0 &&
                Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orderDate, orderPrice);
    }
}
