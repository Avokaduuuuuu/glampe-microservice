package com.avocado.fcmtoken.dto.resp;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FcmTokenResponse {
    Long id;
    String token;
    String deviceId;
    Long userId;
}
