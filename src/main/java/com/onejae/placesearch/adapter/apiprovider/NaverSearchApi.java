package com.onejae.placesearch.adapter.apiprovider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onejae.placesearch.domain.entity.Place;
import com.onejae.placesearch.domain.entity.VendorSearchResult;
import com.onejae.placesearch.domain.port.SearchRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
class NaverPlaceInfo {
    private String title;
    private String link;
    private String category;
    private String description;
    private String telephone;
    private String address;
    private String roadAddress;
    private int mapx;
    private int mapy;
}

@Component
@Slf4j
@ConfigurationProperties(prefix = "api.naver")
public class NaverSearchApi extends SearchRequest {
    @Setter
    private String clientId;

    @Setter
    private String clientSecret;

    @Setter
    private int priority;

    @Setter
    private String vendorName;

    private final RestTemplate restTemplate;

    @Autowired
    public NaverSearchApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public VendorSearchResult request(String keyword) {
        VendorSearchResult r = new VendorSearchResult(this.vendorName, this.priority);

        String requestUrl = this.buildRequestUri(keyword);

        // 인증용 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", this.clientId);
        headers.set("X-Naver-Client-Secret", this.clientSecret);

        HttpEntity request = new HttpEntity(headers);

        try {
            ResponseEntity<Object> response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, Object.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                log.debug("naver api has responded, but not ok");
                return null;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            LinkedHashMap<String, Object> hm = (LinkedHashMap<String, Object>) response.getBody();
            Object documents = hm.get("items");
            List<NaverPlaceInfo> placeInfos = ((ArrayList<Object>) documents).stream()
                                                                             .map(obj -> objectMapper.convertValue(obj, NaverPlaceInfo.class))
                                                                             .collect(Collectors.toList());

            placeInfos.forEach(p -> r.appendPlace(new Place(p.getTitle())));

            log.info(String.format("Naver api call succeed with uri %s", requestUrl));
            return r;
        } catch (Exception e) {
            log.debug("http request for kakao api has failed");
            log.debug(e.toString());

            return null;
        }
    }

    @Override
    protected String buildRequestUri(String keyword) {
        UriComponents builder = UriComponentsBuilder.newInstance()
                                                    .scheme("https")
                                                    .host("openapi.naver.com")
                                                    .path("/v1/search/local.json")
                                                    .queryParam("query", keyword)
                                                    .queryParam("display", 5)
                                                    .build();
        return builder.toUriString();
    }
}
