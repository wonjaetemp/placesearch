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
class KakaoPlaceInfo {
    private String address_name;
    private String category_group_code;
    private String category_group_name;
    private String category_name;
    private String distance;
    private int id;
    private String phone;
    private String place_name;
    private String place_url;
    private String road_address_name;
    private float x;
    private float y;
}

@Component
@Slf4j
@ConfigurationProperties(prefix = "api.kakao")
public class KakaoSearchApi extends SearchRequest {
    @Setter
    private String restApiKey;

    private final RestTemplate restTemplate;

    @Setter
    private int priority;

    @Setter
    private String vendorName;

    @Autowired
    public KakaoSearchApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public VendorSearchResult request(String keyword) {
        VendorSearchResult r = new VendorSearchResult(this.vendorName, this.priority);

        String requestUrl = this.buildRequestUri(keyword);

        // 인증용 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("KakaoAK %s", this.restApiKey));
        HttpEntity request = new HttpEntity(headers);

        try {
            ResponseEntity<Object> response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, Object.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                log.debug("kakao api has responded, but not ok");
                return null;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            LinkedHashMap<String, Object> hm = (LinkedHashMap<String, Object>) response.getBody();
            Object documents = hm.get("documents");
            List<KakaoPlaceInfo> placeInfos = ((ArrayList<Object>) documents).stream()
                                                                             .map(obj -> objectMapper.convertValue(obj, KakaoPlaceInfo.class))
                                                                             .collect(Collectors.toList());

            placeInfos.forEach(p -> r.appendPlace(new Place(p.getPlace_name())));

            log.info(String.format("Kakao api call succeed with uri %s", requestUrl));
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
                                                    .host("dapi.kakao.com")
                                                    .path("/v2/local/search/keyword.json")
                                                    .queryParam("query", keyword)
                                                    .queryParam("size", 5)
                                                    .build();

        return builder.toUriString();
    }
}
