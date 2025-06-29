package com.avocado.user.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserVerifyRequest(
        @NotNull(message = "User email must not be null")
        @Email(message = "Email must follow the pattern")
        String email
) {
}
