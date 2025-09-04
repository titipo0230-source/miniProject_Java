package com.mini.model;

public class Religion {
    private String name;
    private int score;

    public Religion(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int amount) {
        this.score += amount;
    }
}
