package com.avocado.booking.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BookingSelectionRequest(
        @NotNull(message = "Selection Id must not be null")
        Long selectionId,
        @NotNull(message = "Selection's quantity must not be null")
        @Min(value = 1, message = "Selection's quantity must be greater than 0")
        Integer quantity
) {
}
