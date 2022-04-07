package com.onejae.placesearch.service;

import com.onejae.placesearch.usecase.dto.PlaceData;
import com.onejae.placesearch.usecase.dto.VendorSearchResultData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class ResultCombiner {
    public ArrayList<PlaceData> combine(VendorSearchResultData[] resultDataList) {
        ArrayList<PlaceData> places = new ArrayList<>();

        class OrderInfo {
            public final PlaceData representer;
            public int counter;
            public final int priority;
            public final int index;

            public OrderInfo(PlaceData placeData, int priority, int index) {
                this.representer = placeData;
                this.priority = priority;
                this.index = index;
                this.counter = 1;
            }

            public void increaseCounter() {
                this.counter++;
            }
        }

        HashMap<String, OrderInfo> dupTable = new HashMap<>(); // key : title, value : num of title duplication

        var wrapper = new Object() {
            String previousVendorname = "";
            int index = 0;
        };

        Arrays.stream(resultDataList).forEachOrdered(r -> {
            wrapper.index = 0;
            r.getPlaceArrayList().stream().forEachOrdered(v ->
                    dupTable.compute(v.generateKey(), (key, value) -> {
                        if (value == null) {
                            return new OrderInfo(v, r.getPriority(), wrapper.index++);
                        }

                        if (!r.getVendor().equals(wrapper.previousVendorname)) {
                            value.increaseCounter();
                        }

                        return value;
                    })
            );

            wrapper.previousVendorname = r.getVendor();
        });

        ArrayList<OrderInfo> placeListWithOrderinfo = new ArrayList<>(dupTable.values());

        placeListWithOrderinfo.sort((p1, p2) -> {
            if (p1.counter > p2.counter) {
                return -1;
            } else if (p1.counter < p2.counter) {
                return 1;
            } else {
                if (p1.priority < p2.priority) {
                    return -1;
                } else if (p1.priority > p2.priority) {
                    return 1;
                } else {
                    if (p1.index > p2.index) {
                        return 1;
                    } else if (p1.index < p2.index) {
                        return -1;
                    }
                }
                return 0;
            }
        });

        return (ArrayList<PlaceData>) placeListWithOrderinfo.stream()
                                                            .map(o -> new PlaceData(o.representer.getName()))
                                                            .collect(Collectors.toList());
    }
}