package com.avocado.kafka;

import com.avocado.notification.email.EmailEntity;
import com.avocado.notification.email.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "notification", groupId = "notification-group")
    public void consume(String email){
        System.out.println(email);
        EmailEntity emailEntity;
        try {
            emailEntity = objectMapper.readValue(email, EmailEntity.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        emailService.sendEmail(emailEntity);
    }

}
