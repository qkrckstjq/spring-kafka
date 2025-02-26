package com.kafka.kafka.domain.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessage(String message) {
        kafkaTemplate.send("test-topic", message);
        return "success to send message " + message;
    }
}
