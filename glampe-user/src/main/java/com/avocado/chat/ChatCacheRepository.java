package com.avocado.chat;

import com.avocado.cache.Cache;
import com.avocado.exception.ResultCode;
import com.avocado.exception.UserException;
import com.avocado.user.UserMapper;
import com.avocado.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ChatCacheRepository {

    private final RedisTemplate<String, Object> redis;   // generic template
    private final UserRepository userRepository;

    /* ───────────────────────── SAVE ──────────────────────────── */
    public void save(ChatMessageDto dto) {
        String chatKey = buildChatKey(dto.getSenderId(), dto.getReceiverId());

        // 1) store message in conversation ZSET (score = timestamp)
        redis.opsForZSet().add(chatKey, dto, dto.getTimestamp());

        // 2) update “recent conversations” index for both users
        String idxSender   = "conversations:" + dto.getSenderId();
        String idxReceiver = "conversations:" + dto.getReceiverId();

        redis.opsForZSet().add(idxSender,   dto.getReceiverId(), dto.getTimestamp());
        redis.opsForZSet().add(idxReceiver, dto.getSenderId(),   dto.getTimestamp());
    }

    /* ───────────────────────── HISTORY ───────────────────────── */
    public List<ChatMessageDto> getChatHistory(Long u1, Long u2, int limit) {
        String key = buildChatKey(u1, u2);
        log.info("history key {}", key);
        Set<Object> raw = redis.opsForZSet().reverseRange(key, 0, limit - 1);
        if (raw == null) return List.of();

        return raw.stream()
                .filter(ChatMessageDto.class::isInstance)
                .map(obj -> (ChatMessageDto) obj)
                .toList();
    }

    /* ──────────────── USER’S CONVERSATION LIST ───────────────── */
    public List<ConversationInfo> getUserConversations(Long userId) {
        String idxKey = "conversations:" + userId;

        // ZSET of partner IDs (values) ordered by last timestamp (score)
        Set<Object> rawPartners = redis.opsForZSet().reverseRange(idxKey, 0, -1);
        if (rawPartners == null) {
            log.info("No Conversations");
            return new ArrayList<>();
        }

        List<Long> partnerIds = (rawPartners == null) ? List.of()
                : rawPartners.stream()
                .map(Object::toString)   // "1" → "1"
                .map(Long::valueOf)      //  "1" → 1L
                .toList();

        log.info("Partner Ids : {}", partnerIds);

        if (partnerIds.isEmpty()) return List.of();

        return partnerIds.stream()
                .map(otherId -> {
                    String chatKey = buildChatKey(userId, otherId);

                    // fetch newest message in that conversation
                    Set<Object> latestRaw = redis.opsForZSet().reverseRange(chatKey, 0, 0);
                    ChatMessageDto last = latestRaw != null && !latestRaw.isEmpty() &&
                            latestRaw.iterator().next() instanceof ChatMessageDto msg
                            ? msg : null;

                    return ConversationInfo.builder()
                            .user(UserMapper.INSTANCE.toResponse(userRepository.findById(otherId)
                                            .orElseThrow(() -> new UserException(ResultCode.USER_NOT_FOUND))))
                            .lastMessage(last != null ? last.getContent() : null)
                            .timestamp(last != null ? last.getTimestamp() : 0)
                            .build();
                })
                .toList();
    }

    /* ───────────────────────── UTILITY ───────────────────────── */
    private String buildChatKey(Long a, Long b) {
        return "chat:" + Math.min(a, b) + ":" + Math.max(a, b);
    }
}

