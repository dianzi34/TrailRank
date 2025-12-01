package com.example.movierating.dto;

public class Badge {
    private Integer threshold;
    private String imageName;
    private boolean earned;

    public Badge(Integer threshold, boolean earned) {
        this.threshold = threshold;
        this.imageName = threshold + ".png";
        this.earned = earned;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isEarned() {
        return earned;
    }

    public void setEarned(boolean earned) {
        this.earned = earned;
    }
}