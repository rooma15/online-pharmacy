package com.epam.jwd.Servlet.model;

import java.util.Objects;

public class MedicineDto {
    private final int id;
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



    public MedicineDto(String name,
                       int dose,
                       boolean prescriptionDrug,
                       String description,
                       String indicationsForUse,
                       String contraindications,
                       String sideEffects,
                       String consistency,
                       String composition,
                       double price,
                       String category,
                       String path) {
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
        id = 0;
        this.path = path;
    }

    public MedicineDto(Medicine medicine){
        this.id = medicine.getId();
        this.name = medicine.getName();
        this.dose = medicine.getDose();
        this.prescriptionDrug = medicine.getPrescriptionDrug();
        this.description = medicine.getDescription();
        this.indicationsForUse = medicine.getIndicationsForUse();
        this.contraindications = medicine.getContraindications();
        this.sideEffects = medicine.getSideEffects();
        this.consistency = medicine.getConsistency();
        this.composition = medicine.getComposition();
        this.price = medicine.getPrice();
        this.category = medicine.getCategory();
        this.path = medicine.getPath();
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

    public int getId(){
        return id;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        MedicineDto that = (MedicineDto) o;
        return dose == that.dose &&
                prescriptionDrug == that.prescriptionDrug &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(indicationsForUse, that.indicationsForUse) &&
                Objects.equals(contraindications, that.contraindications) &&
                Objects.equals(sideEffects, that.sideEffects) &&
                Objects.equals(consistency, that.consistency) &&
                Objects.equals(composition, that.composition) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dose, prescriptionDrug,
                description, indicationsForUse, contraindications,
                sideEffects, consistency, composition, price, category);
    }
}
