package com.avocado.booking.dto.req;

import com.avocado.booking.enums.BookingStatusEnum;
import jakarta.validation.constraints.NotNull;

public record BookingUpdateRequest(
        @NotNull(message = "status must not be null")
        BookingStatusEnum status
) {
}
