package com.epam.jwd.Servlet.model;

import java.util.Objects;

public class CartItemDto {
    private final int id;
    private final int medicineId;
    private final String medicineName;
    private final int medicineDose;
    private final String medicineConsistency;
    private final double price;
    private final int amount;




    public CartItemDto(int id,
                       int medicineId,
                       String medicineName,
                       int medicineDose,
                       String medicineConsistency,
                       double price,
                       int amount) {
        this.id = id;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicineDose = medicineDose;
        this.medicineConsistency = medicineConsistency;
        this.price = price;
        this.amount = amount;
    }


    public int getId() {
        return id;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public int getMedicineDose() {
        return medicineDose;
    }

    public String getMedicineConsistency() {
        return medicineConsistency;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        CartItemDto that = (CartItemDto) o;
        return id == that.id &&
                medicineId == that.medicineId &&
                medicineDose == that.medicineDose &&
                Double.compare(that.price, price) == 0 &&
                amount == that.amount &&
                Objects.equals(medicineName, that.medicineName) &&
                Objects.equals(medicineConsistency, that.medicineConsistency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicineId, medicineName, medicineDose, medicineConsistency, price, amount);
    }
}
