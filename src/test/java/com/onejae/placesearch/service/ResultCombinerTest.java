package com.onejae.placesearch.service;

import com.onejae.placesearch.domain.entity.Place;
import com.onejae.placesearch.domain.entity.VendorSearchResult;
import com.onejae.placesearch.usecase.dto.PlaceData;
import com.onejae.placesearch.usecase.dto.VendorSearchResultData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResultCombinerTest {
    @Test
    void testCombine1() {
        ResultCombiner r = new ResultCombiner();
        VendorSearchResult v1 = new VendorSearchResult("KAKAO", 0);
        VendorSearchResult v2 = new VendorSearchResult("NAVER", 1);

        v1.appendPlace(new Place("A곱창"));
        v1.appendPlace(new Place("B곱창"));
        v1.appendPlace(new Place("C곱창"));
        v1.appendPlace(new Place("D곱창"));

        v2.appendPlace(new Place("A곱창"));
        v2.appendPlace(new Place("E곱창"));
        v2.appendPlace(new Place("D곱창"));
        v2.appendPlace(new Place("C곱창"));

        VendorSearchResultData[] testSet = {VendorSearchResultData.fromEntity(v1), VendorSearchResultData.fromEntity(v2)};

        ArrayList<PlaceData> result = r.combine(testSet);

        assertEquals(result.get(0).getName(), "A곱창");
        assertEquals(result.get(1).getName(), "C곱창");
        assertEquals(result.get(2).getName(), "D곱창");
        assertEquals(result.get(3).getName(), "B곱창");
        assertEquals(result.get(4).getName(), "E곱창");
    }

    @Test
    void testCombine2() {
        ResultCombiner r = new ResultCombiner();
        VendorSearchResult v1 = new VendorSearchResult("KAKAO", 0);
        VendorSearchResult v2 = new VendorSearchResult("NAVER", 1);

        v1.appendPlace(new Place("카카오뱅크"));
        v1.appendPlace(new Place("우리은행"));
        v1.appendPlace(new Place("국민은행"));
        v1.appendPlace(new Place("부산은행"));
        v1.appendPlace(new Place("새마을금고"));

        v2.appendPlace(new Place("카카오뱅크"));
        v2.appendPlace(new Place("부산은행"));
        v2.appendPlace(new Place("하나은행"));
        v2.appendPlace(new Place("국민은행"));
        v2.appendPlace(new Place("기업은행"));

        VendorSearchResultData[] testSet = {VendorSearchResultData.fromEntity(v1), VendorSearchResultData.fromEntity(v2)};

        ArrayList<PlaceData> result = r.combine(testSet);

        assertEquals(result.get(0).getName(), "카카오뱅크");
        assertEquals(result.get(1).getName(), "국민은행");
        assertEquals(result.get(2).getName(), "부산은행");
        assertEquals(result.get(3).getName(), "우리은행");
        assertEquals(result.get(4).getName(), "새마을금고");
        assertEquals(result.get(5).getName(), "하나은행");
        assertEquals(result.get(6).getName(), "기업은행");
    }
}