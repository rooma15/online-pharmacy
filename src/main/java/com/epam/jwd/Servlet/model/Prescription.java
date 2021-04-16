package com.epam.jwd.Servlet.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Prescription extends Entity{
    private final int userId;
    private final int medicineId;
    private final boolean isAccepted;

    public int getUserId() {
        return userId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public boolean getAccepted() {
        return isAccepted;
    }

    public int getId(){
        return id;
    }

    public Prescription(int id, int userId, int medicineId, boolean isAccepted) {
        super(id);
        this.userId = userId;
        this.medicineId = medicineId;
        this.isAccepted = isAccepted;
    }

    public Prescription(int userId, int medicineId, boolean isAccepted) {
        this.userId = userId;
        this.medicineId = medicineId;
        this.isAccepted = isAccepted;
    }


    public Prescription(ResultSet result) throws SQLException{
        this.id = result.getInt(1);
        this.userId = result.getInt(2);
        this.medicineId = result.getInt(3);
        this.isAccepted = result.getBoolean(4);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return userId == that.userId &&
                medicineId == that.medicineId &&
                isAccepted == that.isAccepted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, medicineId, isAccepted);
    }
}
