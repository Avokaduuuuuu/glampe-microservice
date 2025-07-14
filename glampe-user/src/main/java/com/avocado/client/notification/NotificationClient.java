package com.avocado.client.notification;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "http://localhost:8085", path = "/api/v1/notifications")
public interface NotificationClient {

    @PostMapping("/push")
    void pushNotification(@RequestBody NotificationMessage notificationMessage);
}
