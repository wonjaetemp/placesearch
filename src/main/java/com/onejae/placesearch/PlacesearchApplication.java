package com.onejae.placesearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PlacesearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlacesearchApplication.class, args);
    }
}
