package com.kafka.kafka.domain.api.dto;

import lombok.Getter;

public class KafkaProducerRequestDto {

    @Getter
    public static class KafkaProducerSendMsgDto {
        String topic;
        String message;
    }

//    public class KafkaProducerCreateTopicDto {
//        String topic;
//    }
}
