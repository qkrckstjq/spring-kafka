package com.kafka.kafka.domain.api.controller;

import com.kafka.kafka.domain.api.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    }
}
