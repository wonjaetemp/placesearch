package com.onejae.placesearch.adapter.apiprovider;

import com.onejae.placesearch.domain.port.SearchRequest;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ApiVendorProvider implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final static Class[] apiVendors = {
            KakaoSearchApi.class,
            NaverSearchApi.class
    };

    @Bean
    public SearchRequest[] generateApiVendors() {
        return Arrays.stream(ApiVendorProvider.apiVendors)
                .map(v -> applicationContext.getBean(v))
                .toArray(SearchRequest[]::new);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
