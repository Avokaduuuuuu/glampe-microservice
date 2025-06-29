package com.avocado.user.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserAddRequest(
        @NotBlank(message = "Email must not be blank")
        @NotNull(message = "Email must not be null")
        String email,
        @NotBlank(message = "Firstname must not be blank")
        @NotNull(message = "Firstname must not be null")
        String firstName,
        @NotBlank(message = "Lastname must not be blank")
        @NotNull(message = "Lastname must not be null")
        String lastName,
        @NotBlank(message = "Phone must not be blank")
        @NotNull(message = "Phone must not be null")
        String phoneNumber,
        @NotBlank(message = "Address must not be blank")
        @NotNull(message = "Address must not be null")
        String address,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dob
) {
}
