package com.avocado.chat;

import com.avocado.client.notification.NotificationClient;
import com.avocado.client.notification.NotificationMessage;
import com.avocado.exception.ResultCode;
import com.avocado.exception.UserException;
import com.avocado.user.UserEntity;
import com.avocado.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;



@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatCacheRepository chatCacheRepository;
    private final NotificationClient notificationClient;
    private final UserRepository userRepository;
    private final ChatService chatService;

    // Client sends to: /app/chat.send
    @MessageMapping("/chat.send")
    public void handleMessage(ChatMessageDto message) {
        if (message.getTimestamp() == 0) {
            message.setTimestamp(System.currentTimeMillis());
        }
        System.out.println("Received message: " + message);

        // Save message to Redis
        chatCacheRepository.save(message);

        // Send to both sender and receiver channels
        messagingTemplate.convertAndSend("/topic/chat/" + message.getSenderId(), message);
        messagingTemplate.convertAndSend("/topic/chat/" + message.getReceiverId(), message);

        chatService.pushChatNotification(message);
    }
}
