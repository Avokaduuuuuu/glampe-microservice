package com.avocado.campsite.dto.req;

import com.avocado.camptype.dto.req.CampTypeCreateRequest;
import com.avocado.selection.dto.req.SelectionCreateRequest;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CampSiteCreateRequest(
        @NotEmpty(message = "Campsite name must not be empty")
        String name,
        @NotEmpty(message = "Campsite's address must not be empty")
        String address,
        @NotEmpty(message = "Campsite's city must not be empty")
        String city,
        @NotNull(message = "Campsite's latitude must be declared")
        Double latitude,
        @NotNull(message = "Campsite's longitude must be declared")
        Double longitude,
        @NotNull(message = "Deposit Rate must be declared")
        @DecimalMin(value = "0.1", message = "Deposit Rate must be at least 0.1")
        @DecimalMax(value = "1.0", message = "Deposit Rate must not exceed 1.0")
        Double depositRate,
        @NotEmpty(message = "Campsite's description must not be empty")
        String description,
        @NotNull(message = "User Id must be declared")
        Long uerId,
        List<Long> utilityIds,
        List<Long> placeTypeIds,
        List<SelectionCreateRequest> selections,
        List<CampTypeCreateRequest> campTypes
) {
}
