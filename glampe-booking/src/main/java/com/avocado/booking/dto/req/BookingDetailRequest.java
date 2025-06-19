package com.avocado.booking.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BookingDetailRequest(
        @NotNull(message = "CampType's Id must not be null")
        Long campTypeId,
        @NotNull(message = "Quantity must not be null")
        @Min(value = 1, message = "Quantity must be greater than 0")
        Integer quantity
) {
}
