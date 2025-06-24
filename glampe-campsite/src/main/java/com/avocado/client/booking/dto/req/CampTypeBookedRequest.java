package com.avocado.client.booking.dto.req;

import java.time.LocalDate;
import java.util.List;

public record CampTypeBookedRequest(
        List<Long> campTypeIds,
        LocalDate checkInAt,
        LocalDate checkOutAt
) {
}
