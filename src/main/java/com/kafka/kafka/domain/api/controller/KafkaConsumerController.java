package com.kafka.kafka.domain.api.controller;

import com.kafka.kafka.domain.api.dto.KafkaConsumerResponseDto.KafkaConsumerTopicMsgDto;
import com.kafka.kafka.domain.api.service.KafkaConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka/consumer")
@RequiredArgsConstructor
public class KafkaConsumerController {
    private final KafkaConsumerService kafkaConsumerService;

    @GetMapping("/topic")
    public KafkaConsumerTopicMsgDto getTopicMessages(
            @RequestParam String topic
    ) {
        return kafkaConsumerService.getTopicMessages(topic);
    }
}
