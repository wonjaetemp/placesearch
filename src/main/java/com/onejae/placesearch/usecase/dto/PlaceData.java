package com.onejae.placesearch.usecase.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceData {
    private String name;

    public PlaceData(String name) {
        this.name = name;
    }

    public String generateKey() {
        // refine title (remove tags and spaces)
        return name.replaceAll("<[^>]*>", "").replaceAll("\\s", "");
    }
}
