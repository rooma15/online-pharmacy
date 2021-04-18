package com.epam.jwd.Servlet.model;

import java.util.Objects;

public class OrderDto {
    private final int userId;
    private final String orderDate;
    private final double orderPrice;
    private final int id;

    public OrderDto(int id, int userId, String orderDate, double orderPrice) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.id = id;
    }

    public OrderDto(int userId, String orderDate, double orderPrice) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.id = 0;
    }

    public int getUserId() {
        return userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return userId == orderDto.userId &&
                Double.compare(orderDto.orderPrice, orderPrice) == 0 &&
                id == orderDto.id &&
                Objects.equals(orderDate, orderDto.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orderDate, orderPrice, id);
    }
}
