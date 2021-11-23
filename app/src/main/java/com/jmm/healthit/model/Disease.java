package com.jmm.healthit.model;

public class Disease {
    private String diseaseId;
    private String diseaseName;
    private String diseaseDescription;

    public Disease() {

    }

    public Disease(String diseaseId, String diseaseName, String diseaseDescription) {
        this.diseaseId = diseaseId;
        this.diseaseName = diseaseName;
        this.diseaseDescription = diseaseDescription;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }
}
