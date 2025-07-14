package com.avocado.placetype.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaceTypeResponse {
    Long id;
    String name;
    String image;
    Boolean isDeleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    String createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    String updatedAt;
}
