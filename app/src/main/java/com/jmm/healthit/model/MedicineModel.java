package com.jmm.healthit.model;

public class MedicineModel {

    private String medicineId;
    private String title;
    private String description;

    public MedicineModel() {
    }

    public MedicineModel(String medicineId, String title, String description) {
        this.medicineId = medicineId;
        this.title = title;
        this.description = description;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
