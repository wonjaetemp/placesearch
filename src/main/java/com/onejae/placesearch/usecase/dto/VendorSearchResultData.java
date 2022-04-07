package com.onejae.placesearch.usecase.dto;

import com.onejae.placesearch.domain.entity.VendorSearchResult;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
@Setter
public class VendorSearchResultData {
    private String vendor;
    private int priority;
    private ArrayList<PlaceData> placeArrayList;

    public static VendorSearchResultData fromEntity(VendorSearchResult entity) {
        VendorSearchResultData v = new VendorSearchResultData();
        v.vendor = entity.getVendor();
        v.priority = entity.getPriority();

        v.placeArrayList = (ArrayList<PlaceData>) entity.getPlaceArrayList().stream()
                .map(PlaceModelMapper.INSTANCE::placeToPlaceData)
                .collect(Collectors.toList());

        return v;
    }
}
