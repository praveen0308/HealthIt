package com.jmm.healthit.model.workout;

public class WorkoutDayModel {
    int dayNumber;
    int progress;

    public WorkoutDayModel(int dayNumber, int progress) {
        this.dayNumber = dayNumber;
        this.progress = progress;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
