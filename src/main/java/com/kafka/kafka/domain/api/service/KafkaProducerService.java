package com.kafka.kafka.domain.api.service;

import com.kafka.kafka.global.properties.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public String sendMessage(String message) {
        kafkaTemplate.send(kafkaProperties.getTopic().getName(), message);
        return "success to send message " + message;
    }
}
