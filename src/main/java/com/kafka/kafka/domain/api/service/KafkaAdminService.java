package com.kafka.kafka.domain.api.service;

import com.kafka.kafka.global.properties.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class KafkaAdminService {
    private final AdminClient adminClient;
    private final KafkaProperties kafkaProperties;

    public List<String> getTopicList() throws ExecutionException, InterruptedException {
        return adminClient.listTopics()
                .listings()
                .get()
                .stream()
                .map(TopicListing::name)
                .toList();
    }
    public void createNewTopic(String topic) {
        KafkaProperties.TopicProperties topicProperties = kafkaProperties.getTopic();
        NewTopic newTopic = new NewTopic(
                topic,
                topicProperties.getPartitions(),
                topicProperties.getReplicationFactor()
        );
        Collection<NewTopic> topicList = new ArrayList<>();
        topicList.add(newTopic);

        adminClient.createTopics(topicList);
    }
}
