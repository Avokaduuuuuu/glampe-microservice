package com.avocado.email;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notifications")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;


    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestBody EmailEntity emailEntity){
        emailService.sendEmail(emailEntity);
        return ResponseEntity.status(HttpStatus.OK)
                .body(null);
    }
}
