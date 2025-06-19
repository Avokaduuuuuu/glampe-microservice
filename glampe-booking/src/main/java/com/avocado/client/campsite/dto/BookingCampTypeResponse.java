package com.avocado.client.campsite.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingCampTypeResponse {
    Long id;
    String type;
    Integer capacity;
    BigDecimal price;
    BigDecimal weekendPrice;
    Integer quantity;
    String image;
    Boolean isDeleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;
    List<BookingFacilityResponse> facilities;
    List<BookingCampResponse> camps;
}
