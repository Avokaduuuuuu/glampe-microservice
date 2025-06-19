package com.avocado.client.campsite.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingPlaceTypeResponse {
    Long id;
    String name;
    Boolean isDeleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    String createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    String updatedAt;
}
