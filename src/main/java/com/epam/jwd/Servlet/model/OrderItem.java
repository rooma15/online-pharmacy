package com.epam.jwd.Servlet.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class OrderItem extends Entity{
    private final int orderId;
    private final int amount;
    private final double price;
    private final String name;
    private final int dose;
    private final String consistency;
    private final int medicineId;

    public OrderItem(int id, int orderId, int amount, double price, String name,
                     int dose, String consistency, int medicineId) {
        super(id);
        this.orderId = orderId;
        this.amount = amount;
        this.price = price;
        this.name = name;
        this.dose = dose;
        this.consistency = consistency;
        this.medicineId = medicineId;
    }

    public OrderItem(int orderId, int amount, double price, String name,
                     int dose, String consistency, int medicineId) {
        this.orderId = orderId;
        this.amount = amount;
        this.price = price;
        this.name = name;
        this.dose = dose;
        this.consistency = consistency;
        this.medicineId = medicineId;
    }

    public OrderItem(ResultSet resultSet) throws SQLException{
        super(resultSet.getInt(1));
        this.orderId = resultSet.getInt(2);
        this.amount = resultSet.getInt(3);
        this.price = resultSet.getDouble(4);
        this.name = resultSet.getString(5);
        this.dose = resultSet.getInt(6);
        this.consistency = resultSet.getString(7);
        this.medicineId = resultSet.getInt(8);
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

    public int getId(){
        return id;
    }

    public int getMedicineId() {
        return medicineId;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return orderId == orderItem.orderId &&
                amount == orderItem.amount &&
                Double.compare(orderItem.price, price) == 0 &&
                dose == orderItem.dose &&
                medicineId == orderItem.medicineId &&
                Objects.equals(name, orderItem.name) &&
                Objects.equals(consistency, orderItem.consistency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, amount, price, name, dose, consistency, medicineId);
    }
}
