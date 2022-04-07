package com.onejae.placesearch.adapter.apiprovider;

import com.onejae.placesearch.domain.entity.VendorSearchResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class NaverSearchApiTest {
    @Autowired
    private NaverSearchApi naverSearchApi;

    @Test
    void request() {
        VendorSearchResult result = naverSearchApi.request("마포");

        assertEquals(result.getVendor(), "NAVER");
        assertNotNull(result.getPlaceArrayList());
        assertNotNull(result);
    }
}