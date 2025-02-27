package com.kafka.kafka.domain.api.service;

import com.kafka.kafka.global.properties.KafkaProperties;
import com.kafka.kafka.global.properties.KafkaProperties.*;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProperties kafkaProperties;
    private final AdminClient adminClient;

    public String sendMessage(String message) {
        kafkaTemplate.send(kafkaProperties.getTopic().getName(), message);
        return "success to send message " + message;
    }

    public void createTopic(String topic) {
        TopicProperties topicProperties = kafkaProperties.getTopic();
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
