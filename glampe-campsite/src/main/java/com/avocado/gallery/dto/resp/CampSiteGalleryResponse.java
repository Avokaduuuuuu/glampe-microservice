package com.avocado.gallery.dto.resp;

import com.avocado.s3.S3Service;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CampSiteGalleryResponse {
    Long id;
    String path;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;

    public String toS3PresignedUrl(S3Service s3Service){
        return s3Service.generateUrl(this.path);
    }
}
