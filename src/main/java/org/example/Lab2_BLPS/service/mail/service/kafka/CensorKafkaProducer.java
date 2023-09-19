package org.example.Lab2_BLPS.service.mail.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.Lab2_BLPS.service.mail.model.MessageEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CensorKafkaProducer {

    @Value("${kafka.topic.name}")
    private String orderTopic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    public void sendForCensor(MessageEntity message) {
        kafkaTemplate.send(orderTopic, objectMapper.writeValueAsString(message));
    }
}
