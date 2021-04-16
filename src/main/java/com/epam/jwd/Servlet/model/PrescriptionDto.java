package com.epam.jwd.Servlet.model;

import java.util.Objects;

public class PrescriptionDto {
    private final int id;
    private final String medicineName;
    private final int medicineDose;
    private final String medicineConsistency;
    private final String userName;
    private final String userLogin;

    public PrescriptionDto(int id,
                           String medicineName,
                           int medicineDose,
                           String medicineConsistency,
                           String userName,
                           String userLogin) {
        this.id = id;
        this.medicineName = medicineName;
        this.medicineDose = medicineDose;
        this.medicineConsistency = medicineConsistency;
        this.userName = userName;
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public int getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        PrescriptionDto that = (PrescriptionDto) o;
        return id == that.id &&
                medicineDose == that.medicineDose &&
                Objects.equals(medicineName, that.medicineName) &&
                Objects.equals(medicineConsistency, that.medicineConsistency) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(userLogin, that.userLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicineName, medicineDose, medicineConsistency, userName, userLogin);
    }
}