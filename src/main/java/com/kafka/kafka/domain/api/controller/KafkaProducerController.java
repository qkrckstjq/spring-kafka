package com.kafka.kafka.domain.api.controller;

import com.kafka.kafka.domain.api.service.KafkaProducerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka/producer")
@RequiredArgsConstructor
public class KafkaProducerController {
    private final KafkaProducerService producerService;

    @PostMapping("/message")
    public String sendMessage(
            @RequestBody String message
    ) {
        return producerService.sendMessage(message);
    };

    @PostMapping("/topic")
    public String createTopic(
            @RequestParam String topic
    ) {
        producerService.createTopic(topic);
        return "success to create topic '" + topic + "'";
    }
}
