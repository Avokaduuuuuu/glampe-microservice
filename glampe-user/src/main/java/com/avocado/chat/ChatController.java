package com.avocado.chat;

import com.avocado.config.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatCacheRepository chatCacheRepository;

    @GetMapping("/conversations/{userId}")
    public ResponseEntity<?> getUserConversations(@PathVariable Long userId) {
        List<ConversationInfo> conversations = chatCacheRepository.getUserConversations(userId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .statusCode(200)
                        .message("Fetched conversations")
                        .data(conversations)
                        .build()
        );
    }

    @GetMapping("/history")
    public ResponseEntity<?> getChatHistory(
            @RequestParam Long userId1,
            @RequestParam Long userId2,
            @RequestParam(defaultValue = "50") int limit) {

        List<ChatMessageDto> messages = chatCacheRepository.getChatHistory(userId1, userId2, limit);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .statusCode(200)
                        .message("Fetched chat history")
                        .data(messages)
                        .build()
        );
    }
}
