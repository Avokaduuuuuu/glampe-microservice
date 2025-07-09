package com.avocado.user.dto.req;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UserUpdateRequest(
        @NotBlank(message = "firstname must not be blank")
        String firstName,
        @NotBlank(message = "lastname must not be blank")
        String lastName,
        @NotBlank(message = "phone must not be blank")
        String phoneNumber,
        @NotBlank(message = "address must not be blank")
        String address,
        LocalDate dob
) {
}
