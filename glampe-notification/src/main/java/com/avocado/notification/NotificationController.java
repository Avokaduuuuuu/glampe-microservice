package com.avocado.notification;

import com.avocado.notification.email.EmailEntity;
import com.avocado.notification.email.EmailService;
import com.avocado.notification.push.NotificationMessage;
import com.avocado.notification.push.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;
    private final NotificationService notificationService;


    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailEntity emailEntity){
        emailService.sendEmail(emailEntity);
        return ResponseEntity.status(HttpStatus.OK)
                .body(null);
    }

    @PostMapping("/push")
    public ResponseEntity<?> pushNotification(@RequestBody NotificationMessage notificationMessage){
        notificationService.sendNotificationByToken(notificationMessage);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
