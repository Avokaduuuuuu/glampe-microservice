package com.avocado.booking.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BookingRequest(
        @NotNull(message = "User Id must not be null")
        Long userId,
        @NotNull(message = "Campsite Id must not be null")
        Long campSiteId,
        BigDecimal totalAmount,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime checkInTime,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime checkOutTime,
        List<BookingDetailRequest> bookingDetailRequests,
        List<BookingSelectionRequest> bookingSelectionRequests
) {
}
