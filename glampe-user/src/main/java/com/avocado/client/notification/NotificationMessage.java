package com.avocado.client.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotificationMessage {
    private String recipientToken;
    private String title;
    private String body;
    private String image;
}
