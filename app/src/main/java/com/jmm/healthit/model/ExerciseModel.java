package com.jmm.healthit.model;

public class ExerciseModel {

    private String exerciseId;
    private String title;
    private String imageUrl;
    private String description;

    public ExerciseModel() {
    }

    public ExerciseModel(String exerciseId, String title, String imageUrl, String description) {
        this.exerciseId = exerciseId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
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
