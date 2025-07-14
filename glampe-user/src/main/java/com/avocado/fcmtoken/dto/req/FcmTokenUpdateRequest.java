package com.avocado.fcmtoken.dto.req;

public record FcmTokenUpdateRequest(
        Long userId,
        String token,
        String deviceId
) {
}
