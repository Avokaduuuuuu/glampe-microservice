package com.avocado.selection.dto.resp;

import com.avocado.s3.S3Service;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SelectionResponse {
    Long id;
    String name;
    String description;
    BigDecimal price;
    String image;
    Boolean isDeleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;

    public String toS3PresignUrl(S3Service s3Service) {
        return s3Service.generateUrl(this.image);
    }
}
