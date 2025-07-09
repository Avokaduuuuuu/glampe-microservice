package com.avocado.user.dto.resp;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthUserResponse {
    UserResponse user;
    @Builder.Default
    Boolean isNew = false;
    String token;
}
