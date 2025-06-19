package com.avocado.selection.dto.req;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record SelectionCreateRequest(
        @NotEmpty(message = "Selection's name must not be empty")
        String name,
        @NotEmpty(message = "Selection's description must not be empty")
        String description,
        @NotNull(message = "Price must not be null")
        @Positive(message = "Price must be positive")
        @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
        @DecimalMax(value = "999999.99", message = "Price must not exceed 999,999.99")
        BigDecimal price
) {
}
