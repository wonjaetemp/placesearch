package com.onejae.placesearch.domain.entity;

public class Place {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Place(String name) {
        this.name = name;
    }
}
