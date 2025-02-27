package com.kafka.kafka.domain.api.controller;

import com.kafka.kafka.domain.api.service.KafkaAdminService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
