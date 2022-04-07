package com.onejae.placesearch.service;

import com.onejae.placesearch.domain.port.KeywordAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AggregationConfig {

    @Autowired
    private KeywordAggregator keywordAggregator;
}
