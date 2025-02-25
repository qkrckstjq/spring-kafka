package com.kafka.kafka.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {
    private String bootstrapServers;
    private ConsumerProperties consumer;
    private ProducerProperties producer;
    private TopicProperties topic;

    @Getter
    @Setter
    public static class ConsumerProperties {
        private String groupId;
        private String autoOffsetReset;
    }

    @Getter
    @Setter
    public static class ProducerProperties {
        private int retries ;
        private String acks;
    }

    @Getter
    @Setter
    public static class TopicProperties {
        private String name;
        private int partitions;
        private short replicationFactor;
    }
}
