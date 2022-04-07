package com.onejae.placesearch.domain.port;

public interface KeywordAggregator {
    void onEvent(String keyword);
}
