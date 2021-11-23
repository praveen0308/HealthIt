package com.jmm.healthit.model;

public class FoodModel {

    private String foodId;
    private String title;
    private String imageUrl;
    private String description;

    public FoodModel() {
    }

    public FoodModel(String foodId, String title, String imageUrl, String description) {
        this.foodId = foodId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
