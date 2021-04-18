package com.epam.jwd.Servlet.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CartItem extends Entity {

    private final int medicineId;
    private final int user_id;
    private final int amount;
    private final double price;

    public int getId() {
        return super.id;
    }

    public CartItem(int id, int medicineId, int user_id, int amount, double price) {
        super(id);
        this.user_id = user_id;
        this.medicineId = medicineId;
        this.amount = amount;
        this.price = price;
    }

    public CartItem(int medicineId, int user_id, int amount, double price) {
        this.user_id = user_id;
        this.medicineId = medicineId;
        this.amount = amount;
        this.price = price;
    }


    public CartItem(ResultSet result) throws SQLException {
        id = result.getInt(1);
        medicineId = result.getInt(2);
        amount = result.getInt(3);
        price = result.getInt(4);
        user_id = result.getInt(5);
    }

    public int getMedicineId() {
        return medicineId;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public int getUserId() {
        return user_id;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return medicineId == cartItem.medicineId &&
                user_id == cartItem.user_id &&
                amount == cartItem.amount &&
                Double.compare(cartItem.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineId, user_id, amount, price);
    }
}
