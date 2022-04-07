package com.onejae.placesearch.domain.entity;

import com.onejae.placesearch.domain.port.SearchRequest;

public class SearchAction {
    private String keyword;

    public VendorSearchResult goSearchWithApi(SearchRequest handler) {
        return handler.request(this.keyword);
    }

    public static SearchAction createFromKeyword(String keyword) {
        SearchAction sa = new SearchAction();
        sa.keyword = keyword;
        return sa;
    }
}