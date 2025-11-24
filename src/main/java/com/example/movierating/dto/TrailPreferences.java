package com.example.movierating.dto;

public class TrailPreferences {
    private String difficulty;      // easy, moderate, hard
    private Double minDistance;     // minimum km
    private Double maxDistance;     // maximum km
    private String sceneryType;     // lake, mountain, forest, desert
    private String region;          // user's preferred region
    private String mood;            // relaxing, challenging, scenic, adventure
    private String description;     // free text

    // Constructors
    public TrailPreferences() {}

    public TrailPreferences(String difficulty, Double minDistance, Double maxDistance, 
                          String sceneryType, String region, String mood, String description) {
        this.difficulty = difficulty;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.sceneryType = sceneryType;
        this.region = region;
        this.mood = mood;
        this.description = description;
    }

    // Getters and Setters
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Double minDistance) {
        this.minDistance = minDistance;
    }

    public Double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public String getSceneryType() {
        return sceneryType;
    }

    public void setSceneryType(String sceneryType) {
        this.sceneryType = sceneryType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

