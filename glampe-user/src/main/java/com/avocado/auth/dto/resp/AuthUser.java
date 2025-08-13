package com.avocado.auth.dto.resp;

import com.avocado.user.dto.resp.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthUser {
    String token;
    UserResponse user;
}
