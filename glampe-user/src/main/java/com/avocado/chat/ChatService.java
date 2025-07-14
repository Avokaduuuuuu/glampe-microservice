package com.avocado.chat;

import com.avocado.client.notification.NotificationClient;
import com.avocado.client.notification.NotificationMessage;
import com.avocado.exception.ResultCode;
import com.avocado.exception.UserException;
import com.avocado.user.UserEntity;
import com.avocado.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final UserRepository userRepository;
    private final NotificationClient notificationClient;

    @Transactional          // opens a session for the entire method
    public void pushChatNotification(ChatMessageDto msg) {
        UserEntity recipient = userRepository.findById(msg.getReceiverId())
                .orElseThrow(() -> new UserException(ResultCode.USER_NOT_FOUND));
        UserEntity sender = userRepository.findById(msg.getSenderId())
                .orElseThrow(() -> new UserException(ResultCode.USER_NOT_FOUND));

        recipient.getFcmTokens().forEach(token -> {
            notificationClient.pushNotification(
                    NotificationMessage.builder()
                            .title("New message from " + sender.getFirstName())
                            .image("https://cdn.dribbble.com/userupload/36626798/file/original-83c59b604abf17b46f2dafd5dd0c7e4f.png?resize=400x0")
                            .body(msg.getContent())
                            .recipientToken(token.getToken())
                            .build()
            );
        });
    }
}
