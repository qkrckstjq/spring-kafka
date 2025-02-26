package com.kafka.kafka.global.config;

import com.kafka.kafka.global.properties.KafkaProperties;
import com.kafka.kafka.global.properties.KafkaProperties.*;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    private final KafkaProperties kafkaProperties;

    @Bean
    public NewTopic createNewTopic() {
        TopicProperties topicProperties = kafkaProperties.getTopic();
        return new NewTopic(
                topicProperties.getName(),
                topicProperties.getPartitions(),
                topicProperties.getReplicationFactor()
        );
    }
}
