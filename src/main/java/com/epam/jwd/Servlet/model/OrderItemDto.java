package com.epam.jwd.Servlet.model;

import java.util.Objects;

public class OrderItemDto {
    private final int orderId;
    private final int amount;
    private final double price;
    private final String name;
    private final int dose;
    private final String consistency;
    private final String category;
    private final int id;

    public OrderItemDto(int id,
                        int orderId,
                        int amount,
                        double price,
                        String name,
                        int dose,
                        String consistency,
                        String category) {
        this.orderId = orderId;
        this.amount = amount;
        this.price = price;
        this.name = name;
        this.dose = dose;
        this.consistency = consistency;
        this.category = category;
        this.id = id;
    }

    public OrderItemDto(int orderId,
                        int amount,
                        double price,
                        String name,
                        int dose,
                        String consistency,
                        String category) {
        this.orderId = orderId;
        this.amount = amount;
        this.price = price;
        this.name = name;
        this.dose = dose;
        this.consistency = consistency;
        this.category = category;
        this.id = 0;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getDose() {
        return dose;
    }

    public String getConsistency() {
        return consistency;
    }

    public String getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OrderItemDto that = (OrderItemDto) o;
        return orderId == that.orderId &&
                amount == that.amount &&
                Double.compare(that.price, price) == 0 &&
                dose == that.dose &&
                id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(consistency, that.consistency) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, amount, price, name, dose, consistency, category, id);
    }
}
