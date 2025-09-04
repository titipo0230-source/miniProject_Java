package com.mini.model;

public class SpreadMethod {
    private String name;
    private int gainScore;
    private int loseScore; 

    public SpreadMethod(String name, int gainScore, int loseScore) {
        this.name = name;
        this.gainScore = gainScore;
        this.loseScore = loseScore;
    }

    public String getName() {
        return name;
    }

    public int getGainScore() {
        return gainScore;
    }

    public int getLoseScore() {
        return loseScore;
    }
}
