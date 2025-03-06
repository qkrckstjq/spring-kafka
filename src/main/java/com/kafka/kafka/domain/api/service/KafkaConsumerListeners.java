package com.kafka.kafka.domain.api.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListeners {
    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "new-group-id", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        System.out.println(message);
        System.out.println("===================================================================================");
    }
}
