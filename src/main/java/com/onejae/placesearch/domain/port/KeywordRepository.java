package com.onejae.placesearch.domain.port;

import com.onejae.placesearch.adapter.repository.KeywordData;

import java.util.ArrayList;

public interface KeywordRepository {
    void increaseKeywordCount(String keyword);

    ArrayList<KeywordData> findPopularKeywords();
}
