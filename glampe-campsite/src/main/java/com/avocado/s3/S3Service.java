package com.avocado.s3;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {
    List<String> uploadCampSiteImages(List<MultipartFile> files, Long id);

    String generateUrl(String fileName);

    String uploadImage(MultipartFile file, String type, Long id);
}
