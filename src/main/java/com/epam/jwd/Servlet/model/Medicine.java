package com.epam.jwd.Servlet.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Medicine extends Entity{
    private final String name;
    private final int dose;
    private final boolean prescriptionDrug;
    private final String description;
    private final String indicationsForUse;
    private final String contraindications;
    private final String sideEffects;
    private final String consistency;
    private final String composition;
    private final double price;
    private final String category;
    private final String path;

    public Medicine(int id,
                    String name,
                    int dose,
                    boolean prescriptionDrug,
                    String description,
                    String indicationsForUse,
                    String contraindications,
                    String sideEffects,
                    String consistency,
                    String composition,
                    double price,
                    String category) {
        super(id);
        this.name = name;
        this.dose = dose;
        this.prescriptionDrug = prescriptionDrug;
        this.description = description;
        this.indicationsForUse = indicationsForUse;
        this.contraindications = contraindications;
        this.sideEffects = sideEffects;
        this.consistency = consistency;
        this.composition = composition;
        this.price = price;
        this.category = category;
        path = "";
    }

    public Medicine(String name,
                    int dose,
                    boolean prescriptionDrug,
                    String description,
                    String indicationsForUse,
                    String contraindications,
                    String sideEffects,
                    String consistency,
                    String composition,
                    double price,
                    String category) {
        this.name = name;
        this.dose = dose;
        this.prescriptionDrug = prescriptionDrug;
        this.description = description;
        this.indicationsForUse = indicationsForUse;
        this.contraindications = contraindications;
        this.sideEffects = sideEffects;
        this.consistency = consistency;
        this.composition = composition;
        this.price = price;
        this.category = category;
        path = "";
    }




    public Medicine(ResultSet resultSet) throws SQLException {
        super(resultSet.getInt(1));
        this.name = resultSet.getString(2);
        this.dose = resultSet.getInt(3);
        this.prescriptionDrug = resultSet.getBoolean(4);
        this.description = resultSet.getString(5);
        this.indicationsForUse = resultSet.getString(6);
        this.contraindications = resultSet.getString(7);
        this.sideEffects = resultSet.getString(8);
        this.consistency = resultSet.getString(9);
        this.composition = resultSet.getString(10);
        this.price = resultSet.getDouble(11);
        this.category = resultSet.getString(12);
        this.path = resultSet.getString(15);
    }


    public String getName() {
        return name;
    }

    public int getDose() {
        return dose;
    }

    public boolean getPrescriptionDrug() {
        return prescriptionDrug;
    }

    public String getDescription() {
        return description;
    }

    public String getIndicationsForUse() {
        return indicationsForUse;
    }

    public String getContraindications() {
        return contraindications;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public String getConsistency() {
        return consistency;
    }

    public String getComposition() {
        return composition;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return dose == medicine.dose &&
                prescriptionDrug == medicine.prescriptionDrug &&
                Double.compare(medicine.price, price) == 0 &&
                Objects.equals(name, medicine.name) &&
                Objects.equals(description, medicine.description) &&
                Objects.equals(indicationsForUse, medicine.indicationsForUse) &&
                Objects.equals(contraindications, medicine.contraindications) &&
                Objects.equals(sideEffects, medicine.sideEffects) &&
                Objects.equals(consistency, medicine.consistency) &&
                Objects.equals(composition, medicine.composition) &&
                Objects.equals(category, medicine.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,
                dose,
                prescriptionDrug,
                description,
                indicationsForUse,
                contraindications,
                sideEffects,
                consistency,
                composition,
                price,
                category);
    }
}
