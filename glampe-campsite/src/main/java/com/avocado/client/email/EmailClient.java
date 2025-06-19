package com.avocado.client.email;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-service", url = "http://localhost:8085", path = "/api/v1/notifications")
public interface EmailClient {

    @PostMapping
    void sendEmail(@RequestBody EmailEntity emailEntity);
}
