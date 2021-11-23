package com.jmm.healthit.model;

public class FactModel {
    private String factId;
    private String message;

    public FactModel() {
    }

    public FactModel(String factId, String message) {
        this.factId = factId;
        this.message = message;
    }

    public String getFactId() {
        return factId;
    }

    public void setFactId(String factId) {
        this.factId = factId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
