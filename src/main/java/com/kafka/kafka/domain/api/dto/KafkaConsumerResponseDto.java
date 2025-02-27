package com.kafka.kafka.domain.api.dto;

import lombok.Getter;

import java.util.List;

public class KafkaConsumerResponseDto {

    @Getter
    public static class KafkaConsumerTopicMsgDto {
        List<String> messages;

        public KafkaConsumerTopicMsgDto(List<String> messages) {
            this.messages = messages;
        }
    }
}
