package com.kafka.kafka.domain.api.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/kafka/consumer")
@RequiredArgsConstructor
public class KafkaConsumerController {
    private final KafkaConsumerService kafkaConsumerService;

    @GetMapping("/topic")
    public List<String> getTopicMessages(
            @RequestParam String topic
    ) {
        return kafkaConsumerService.getTopicMessages(topic);
    }
}
