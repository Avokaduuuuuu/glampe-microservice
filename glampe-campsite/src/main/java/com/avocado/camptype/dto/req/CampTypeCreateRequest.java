package com.avocado.camptype.dto.req;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record CampTypeCreateRequest(
        @NotEmpty(message = "Campsite type must not be empty")
        String type,
        @NotNull(message = "Campsite capacity must not be null")
        @Min(value = 1, message = "Campsite capacity must not be less than 1")
        Integer capacity,
        @NotNull(message = "Price must not be null")
        @Positive(message = "Price must be positive")
        @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
        @DecimalMax(value = "999999.99", message = "Price must not exceed 999,999.99")
        BigDecimal price,
        @NotNull(message = "Price must not be null")
        @Positive(message = "Price must be positive")
        @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
        @DecimalMax(value = "999999.99", message = "Price must not exceed 999,999.99")
        BigDecimal weekendPrice,
        @NotNull(message = "Campsite quantity must not be null")
        @Min(value = 1, message = "Campsite quantity must not be less than 1")
        Integer quantity,
        List<Long> facilityIds
) {
}
