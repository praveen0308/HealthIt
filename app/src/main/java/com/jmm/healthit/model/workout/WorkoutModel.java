package com.jmm.healthit.model.workout;

public class WorkoutModel {
    int workoutId;
    String name;
    int reps;
    int resId;

    public WorkoutModel(int workoutId, String name, int reps, int resId) {
        this.workoutId = workoutId;
        this.name = name;
        this.reps = reps;
        this.resId = resId;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
