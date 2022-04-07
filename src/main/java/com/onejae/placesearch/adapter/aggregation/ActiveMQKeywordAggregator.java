package com.onejae.placesearch.adapter.aggregation;

import com.onejae.placesearch.domain.port.KeywordAggregator;
import com.onejae.placesearch.domain.port.KeywordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActiveMQKeywordAggregator implements KeywordAggregator {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private KeywordRepository keywordRepository;

    @Override
    public void onEvent(String keyword) {
        log.info("Keyword arrived :" + keyword);
        try {
            keywordRepository.increaseKeywordCount(keyword);
        } catch (Exception e) {
            log.info("Failed to increase count, trying again..");
            throw e;
        }
    }

    @JmsListener(destination = "keyword-box")
    public void receiveMessage(String keyword) {
        onEvent(keyword);
    }
}
