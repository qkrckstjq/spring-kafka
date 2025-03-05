package com.kafka.kafka.domain.api.service;

import com.kafka.kafka.domain.api.dto.KafkaProducerRequestDto.KafkaProducerSendMsgDto;
import com.kafka.kafka.global.properties.KafkaProperties;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> normalKafkaTemplate;
//    private final KafkaTemplate<String, String> batchKafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public KafkaProducerService(
            @Qualifier("kafkaTemplate") KafkaTemplate<String, String> normalKafkaTemplate,
//            @Qualifier("batchKafkaTemplate") KafkaTemplate<String, String> batchKafkaTemplate,
            KafkaProperties kafkaProperties
    ) {
        this.normalKafkaTemplate = normalKafkaTemplate;
//        this.batchKafkaTemplate = batchKafkaTemplate;
        this.kafkaProperties = kafkaProperties;
    }

    public String sendMessage(KafkaProducerSendMsgDto body) {
        String topic = body.getTopic();
        String message = body.getMessage();

        normalKafkaTemplate.send(topic, message);
        System.out.println("success to send message");
        return "success to send message " + message;
    }


    public String sendBatchMessage(KafkaProducerSendMsgDto body) {
        String topic = body.getTopic();
        String message = body.getMessage();

//        batchKafkaTemplate.send(topic, message);
        return "success to send message " + message;
    }
}
