package com.avocado.kafka;

import com.avocado.client.email.EmailEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(EmailEntity email){
        try {
            String json = objectMapper.writeValueAsString(email);
            kafkaTemplate
                    .send("notification", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
