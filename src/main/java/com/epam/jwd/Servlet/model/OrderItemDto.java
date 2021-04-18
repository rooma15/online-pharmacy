package com.epam.jwd.Servlet.model;

import java.util.Objects;

public class OrderItemDto {
    private final int orderId;
    private final int amount;
    private final double price;
    private final String name;
    private final int dose;
    private final String consistency;
    private final int id;
    private final int medicineId;

    public OrderItemDto(int id,int orderId, int amount, double price, String name,
                        int dose, String consistency, int medicineId) {
        this.orderId = orderId;
        this.amount = amount;
        this.price = price;
        this.name = name;
        this.dose = dose;
        this.consistency = consistency;
        this.id = id;
        this.medicineId = medicineId;
    }

    public OrderItemDto(int orderId,
                        int amount,
                        double price,
                        String name,
                        int dose,
                        String consistency,
                        int medicineId) {
        this.orderId = orderId;
        this.amount = amount;
        this.price = price;
        this.name = name;
        this.dose = dose;
        this.consistency = consistency;
        this.medicineId = medicineId;
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

    public int getId() {
        return id;
    }

    public int getMedicineId() {
        return medicineId;
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
                medicineId == that.medicineId &&
                Objects.equals(name, that.name) &&
                Objects.equals(consistency, that.consistency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, amount, price, name, dose, consistency, id, medicineId);
    }
}
