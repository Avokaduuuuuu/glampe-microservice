package com.avocado.s3;

import com.avocado.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/campsites/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCampSiteImages(@RequestParam("files") List<MultipartFile> files, @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully uploaded images")
                        .data(s3Service.uploadCampSiteImages(files, id))
                        .build()
        );
    }

    @PostMapping(value = "/media/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMedia(@RequestParam("file") MultipartFile file, @RequestParam String type , @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully uploaded media")
                        .data(s3Service.uploadImage(file, type, id))
                        .build()
        );
    }
}
