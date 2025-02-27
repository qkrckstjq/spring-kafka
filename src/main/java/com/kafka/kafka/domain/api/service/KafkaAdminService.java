package com.kafka.kafka.domain.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class KafkaAdminService {
    private final AdminClient adminClient;
    public List<String> getTopicList() throws ExecutionException, InterruptedException {
        return adminClient.listTopics()
                .listings()
                .get()
                .stream()
                .map(TopicListing::name)
                .toList();
    }
}
