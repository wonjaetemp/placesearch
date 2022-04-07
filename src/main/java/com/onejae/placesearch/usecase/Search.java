package com.onejae.placesearch.usecase;

import com.onejae.placesearch.adapter.repository.KeywordData;
import com.onejae.placesearch.domain.entity.SearchAction;
import com.onejae.placesearch.domain.entity.VendorSearchResult;
import com.onejae.placesearch.domain.port.KeywordPublisher;
import com.onejae.placesearch.domain.port.KeywordRepository;
import com.onejae.placesearch.domain.port.SearchRequest;
import com.onejae.placesearch.service.ResultCombiner;
import com.onejae.placesearch.usecase.dto.PlaceData;
import com.onejae.placesearch.usecase.dto.VendorSearchResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class Search {
    @Autowired
    private KeywordPublisher keywordPublisher;

    @Autowired
    private SearchRequest[] apiVendors;

    @Autowired
    private ResultCombiner resultCombiner;

    @Autowired
    private KeywordRepository keywordRepository;

    public ArrayList<PlaceData> searchByKeyword(String keyword) {
        try {
            keywordPublisher.publishSearchKeyword(keyword);
        } catch (Exception e) {
            log.debug("Failed to send keyword, aggregation may not work");
        }

        List<VendorSearchResult> vendorSearchResultList =
                Arrays.stream(apiVendors)
                      .map(request -> SearchAction.createFromKeyword(keyword).goSearchWithApi(request))
                      .toList();

        List<VendorSearchResultData> dtos = vendorSearchResultList.stream()
                                                                  .map(VendorSearchResultData::fromEntity)
                                                                  .toList();

        return resultCombiner.combine(dtos.toArray(VendorSearchResultData[]::new));
    }

    public ArrayList<KeywordData> findPopularKeywords() {
        return keywordRepository.findPopularKeywords();
    }
}
