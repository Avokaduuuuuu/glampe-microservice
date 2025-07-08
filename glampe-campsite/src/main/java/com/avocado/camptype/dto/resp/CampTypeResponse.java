package com.avocado.camptype.dto.resp;

import com.avocado.camp.dto.resp.CampResponse;
import com.avocado.facility.dto.resp.FacilityResponse;
import com.avocado.s3.S3Service;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CampTypeResponse {
    Long id;
    String type;
    Integer capacity;
    BigDecimal price;
    BigDecimal weekendPrice;
    Integer quantity;
    Long remainQuantity;
    String image;
    Boolean isDeleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;
    List<FacilityResponse> facilities;
    List<CampResponse> camps;

    public String toS3PresignedUrl(S3Service s3Service) {
        return s3Service.generateUrl(this.image);
    }
}
