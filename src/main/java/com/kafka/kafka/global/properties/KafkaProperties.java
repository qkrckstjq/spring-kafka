package com.kafka.kafka.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.kafka.topic")
public class KafkaProperties {
    private String name;
    private String partitions;
    private String replicationFactor;
}
