package com.avocado.chat;

import com.avocado.user.dto.resp.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationInfo {
    private UserResponse user;
    private String lastMessage;
    private long   timestamp;
}
