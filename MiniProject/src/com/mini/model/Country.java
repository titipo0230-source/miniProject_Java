package com.mini.model;

public class Country {
    private String name;
    private Religion religion;

    public Country(String name, Religion religion) {
        this.name = name;
        this.religion = religion;
    }

    public String getName() {
        return name;
    }

    public Religion getReligion() {
        return religion;
    }
}