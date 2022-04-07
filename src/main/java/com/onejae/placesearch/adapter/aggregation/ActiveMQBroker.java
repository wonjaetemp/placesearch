package com.onejae.placesearch.adapter.aggregation;

import com.onejae.placesearch.domain.port.KeywordPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActiveMQBroker implements KeywordPublisher {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void publishSearchKeyword(String keyword) {
        jmsTemplate.convertAndSend("keyword-box", keyword);
    }
}
