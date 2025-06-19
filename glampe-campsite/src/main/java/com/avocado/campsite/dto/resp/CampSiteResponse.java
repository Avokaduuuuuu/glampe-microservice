package com.avocado.campsite.dto.resp;

import com.avocado.camptype.dto.resp.CampTypeResponse;
import com.avocado.gallery.dto.resp.CampSiteGalleryResponse;
import com.avocado.placetype.dto.resp.PlaceTypeResponse;
import com.avocado.selection.dto.resp.SelectionResponse;
import com.avocado.utility.dto.resp.UtilityResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CampSiteResponse {
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
    CampSiteUserResponse user;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;
    List<UtilityResponse> utilities;
    List<PlaceTypeResponse> placeTypes;
    List<SelectionResponse> selections;
    List<CampSiteGalleryResponse> galleries;
    List<CampTypeResponse> campTypes;
}
