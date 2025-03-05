package com.kafka.kafka.global.config;

import com.kafka.kafka.global.properties.KafkaProperties;
import com.kafka.kafka.global.properties.KafkaProperties.*;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.SslConfigs;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.web.server.Ssl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

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

    @Bean
    public KafkaAdmin kafkaAdminConfig() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        properties.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSecurity().getProtocol());
        properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, kafkaProperties.getSslTruststoreLocation());
        properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, kafkaProperties.getSslTruststorePassword());
        properties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, kafkaProperties.getSslKeystoreLocation());
        properties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, kafkaProperties.getSslKeystorePassword());
//        properties.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, securityProperties.getSslKeyPassword());
        return new KafkaAdmin(properties);
    }

    @Bean
    public AdminClient createKafkaAdminClient(KafkaAdmin kafkaAdmin) {
        return AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }
}
