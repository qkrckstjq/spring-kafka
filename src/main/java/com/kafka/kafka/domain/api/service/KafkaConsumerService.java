package com.kafka.kafka.domain.api.service;

import com.kafka.kafka.domain.api.dto.KafkaConsumerResponseDto.*;
import com.kafka.kafka.global.properties.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final KafkaProperties kafkaProperties;

    public KafkaConsumerTopicMsgDto getTopicMessages(String topic) {
        KafkaConsumer<String, String> newKafkaConsumer = createConsumerProps(topic);
        List<String> messageList = new ArrayList<>();

        try {
            newKafkaConsumer.poll(Duration.ofSeconds(1));
            Set<TopicPartition> assignedPartitions = newKafkaConsumer.assignment();
            while (assignedPartitions.isEmpty()) {
                newKafkaConsumer.poll(Duration.ofMillis(100));
                assignedPartitions = newKafkaConsumer.assignment();
            }
            newKafkaConsumer.seekToBeginning(assignedPartitions);

            int maxEmptyPolls = 3;
            int emptyPollCount = 0;

            while (emptyPollCount < maxEmptyPolls) {
                ConsumerRecords<String, String> messages = newKafkaConsumer.poll(Duration.ofSeconds(1));

                if (messages.isEmpty()) {
                    emptyPollCount++;
                } else {
                    messages.forEach(record -> messageList.add(record.value()));
                    emptyPollCount = 0;
                }
            }

            newKafkaConsumer.commitSync();
        } catch (Exception e) {
            return new KafkaConsumerTopicMsgDto(List.of("Error: " + e.getMessage()));
        } finally {
            newKafkaConsumer.close();
        }

        return new KafkaConsumerTopicMsgDto(messageList.isEmpty() ? List.of("No messages found") : messageList);
    }



    private KafkaConsumer<String, String> createConsumerProps(String topic) {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> newKafkaConsumer = new KafkaConsumer<>(consumerProps);
        Collection<String> topicList = List.of(topic);

        newKafkaConsumer.subscribe(topicList);
        return newKafkaConsumer;
    }

    @KafkaListener(topics = "test-topic", groupId = "test-group", containerFactory = "kafkaListenerContainerFactory")
    public void getTopicTestTopic(ConsumerRecord<String, String> record) {
        System.out.println(record.value());
    }
}
