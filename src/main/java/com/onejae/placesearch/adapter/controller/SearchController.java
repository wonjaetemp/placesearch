package com.onejae.placesearch.adapter.controller;

import com.onejae.placesearch.adapter.controller.dto.KeywordResponseData;
import com.onejae.placesearch.adapter.controller.dto.ModelMapper;
import com.onejae.placesearch.adapter.controller.dto.PlaceResponseData;
import com.onejae.placesearch.adapter.controller.response.ListResponse;
import com.onejae.placesearch.adapter.repository.KeywordData;
import com.onejae.placesearch.usecase.Search;
import com.onejae.placesearch.usecase.dto.PlaceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1")
public class SearchController {
    @Autowired
    private Search search;

    @RequestMapping(value = "/place", method = RequestMethod.GET)
    public ResponseEntity<ListResponse<PlaceResponseData>> searchByKeyword(@RequestParam(value = "q", required = false) final String q) {
        if (q == null || q.isEmpty()) {
            throw new IllegalArgumentException("Parameter missing");
        }

        ArrayList<PlaceData> placeDataList = search.searchByKeyword(q);

        ListResponse<PlaceResponseData> response = new ListResponse<>("placesearch");

        for (PlaceData p : placeDataList) {
            response.appendData(ModelMapper.INSTANCE.placeDataToPlaceResponseData(p));
        }

        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/place/popularkeywords", method = RequestMethod.GET)
    public ResponseEntity<ListResponse<KeywordResponseData>> popularKeywords() {
        ArrayList<KeywordData> keywordDataList = search.findPopularKeywords();

        ListResponse<KeywordResponseData> response = new ListResponse<>("popularkeywords");

        for (KeywordData k : keywordDataList) {
            response.appendData(ModelMapper.INSTANCE.keywordDataToKeywordResponseData(k));
        }

        return ResponseEntity.ok().body(response);
    }
}
