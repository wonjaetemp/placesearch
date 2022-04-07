package com.onejae.placesearch.domain.entity;

import java.util.ArrayList;

public class VendorSearchResult {
    public String getVendor() {
        return vendor;
    }

    private final String vendor;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    private int priority;

    public ArrayList<Place> getPlaceArrayList() {
        return placeArrayList;
    }

    private ArrayList<Place> placeArrayList;

    public VendorSearchResult(String vendor, int priority) {
        this.placeArrayList = new ArrayList<>();
        this.vendor = vendor;
        this.priority = priority;
    }

    public void appendPlace(Place p) {
        this.placeArrayList.add(p);
    }
}
