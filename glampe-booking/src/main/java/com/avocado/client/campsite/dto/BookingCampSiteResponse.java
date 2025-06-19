package com.avocado.client.campsite.dto;

import com.avocado.client.user.dto.BookingUserResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingCampSiteResponse {
    Long id;
    String name;
    String address;
    String city;
    Double latitude;
    Double longitude;
    String status;
    String message;
    Double depositRate;
    String description;
    BookingUserResponse user;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;
    List<BookingUtilityResponse> utilities;
    List<BookingPlaceTypeResponse> placeTypes;
    List<BookingSelectionResponse> selections;
    List<BookingCampSiteGalleryResponse> galleries;
    List<BookingCampTypeResponse> campTypes;

}
