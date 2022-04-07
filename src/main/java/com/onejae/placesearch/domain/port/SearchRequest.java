package com.onejae.placesearch.domain.port;

import com.onejae.placesearch.domain.entity.VendorSearchResult;

public abstract class SearchRequest {
    public abstract VendorSearchResult request(String keyword);

    protected abstract String buildRequestUri(String keyword);
}