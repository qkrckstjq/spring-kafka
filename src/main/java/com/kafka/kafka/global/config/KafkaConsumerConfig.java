package com.kafka.kafka.global.config;

import com.kafka.kafka.global.properties.KafkaProperties;
import com.kafka.kafka.global.properties.KafkaProperties.*;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    private final KafkaProperties kafkaProperties;

    @Bean("normalConsumer")
    public ConsumerFactory<String, String> consumerConfig() {
        Map<String, Object> configProps = new HashMap<>();

        ConsumerProperties consumerProperties = kafkaProperties.getConsumer();
        CommonProperties securityProperties = consumerProperties.getProperties();
        SslProperties sslProperties = securityProperties.getSsl();
        StoreDetailsProperties keyStore = sslProperties.getKeyStore();
        StoreDetailsProperties trustStore = sslProperties.getTrustStore();

        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        configProps.put("security.protocol", "SSL");
        configProps.put("ssl.keystore.type", keyStore.getType());
        configProps.put("ssl.keystore.location", keyStore.getLocation());
        configProps.put("ssl.keystore.password", keyStore.getPassword());
        configProps.put("ssl.truststore.type", trustStore.getType());
        configProps.put("ssl.truststore.location", trustStore.getLocation());
        configProps.put("ssl.truststore.password", trustStore.getPassword());

        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean("kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            @Qualifier("normalConsumer") ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
