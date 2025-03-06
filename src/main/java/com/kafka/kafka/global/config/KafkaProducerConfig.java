package com.kafka.kafka.global.config;

import com.kafka.kafka.global.properties.KafkaProperties;
import com.kafka.kafka.global.properties.KafkaProperties.*;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {
    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, String> normalProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        ProducerProperties producerProperties = kafkaProperties.getProducer();
        CommonProperties securityProperties = producerProperties.getProperties();
        SslProperties sslProperties = securityProperties.getSsl();
        StoreDetailsProperties keyStore = sslProperties.getKeyStore();
        StoreDetailsProperties trustStore = sslProperties.getTrustStore();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers()); // Kafka 서버 주소
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        config.put("security.protocol", "SSL");
        config.put("ssl.keystore.type", keyStore.getType());
        config.put("ssl.keystore.location", keyStore.getLocation());
        config.put("ssl.keystore.password", keyStore.getPassword());
        config.put("ssl.truststore.type", trustStore.getType());
        config.put("ssl.truststore.location", trustStore.getLocation());
        config.put("ssl.truststore.password", trustStore.getPassword());

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean("normalKafkaTemplate")
    public KafkaTemplate<String, String> normalKafkaTemplate() {
        return new KafkaTemplate<>(normalProducerFactory());
    }

    @Bean
    public ProducerFactory<String, String> batchProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        ProducerProperties producerProperties = kafkaProperties.getProducer();
        CommonProperties securityProperties = producerProperties.getProperties();
        SslProperties sslProperties = securityProperties.getSsl();
        StoreDetailsProperties keyStore = sslProperties.getKeyStore();
        StoreDetailsProperties trustStore = sslProperties.getTrustStore();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers()); // Kafka 서버 주소
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, 32 * 1024); //32kb
        config.put(ProducerConfig.LINGER_MS_CONFIG, 10); //대기 시간 10ms
        config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy"); //압축 방식

        config.put("security.protocol", "SSL");
        config.put("ssl.keystore.type", keyStore.getType());
        config.put("ssl.keystore.location", keyStore.getLocation());
        config.put("ssl.keystore.password", keyStore.getPassword());
        config.put("ssl.truststore.type", trustStore.getType());
        config.put("ssl.truststore.location", trustStore.getLocation());
        config.put("ssl.truststore.password", trustStore.getPassword());

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean("batchKafkaTemplate")
    public KafkaTemplate<String, String> batchKafkaTemplate() {
        return new KafkaTemplate<>(batchProducerFactory());
    }
}
