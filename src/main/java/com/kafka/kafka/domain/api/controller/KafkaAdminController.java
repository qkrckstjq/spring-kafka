package com.kafka.kafka.domain.api.controller;

import com.kafka.kafka.domain.api.service.KafkaAdminService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/kafka/admin")
@RequiredArgsConstructor
public class KafkaAdminController {
    private final AdminClient adminClient;
    private final KafkaAdminService kafkaAdminService;

    @GetMapping("/topic")
    public List<String> getTopicList() throws ExecutionException, InterruptedException {
        return kafkaAdminService.getTopicList();
    }

    @PostMapping("/topic")
    public String createNewTopic(
            @RequestParam String topic
    ) {
        kafkaAdminService.createNewTopic(topic);
        return "success to create topic '" + topic + "'";
    }
}
