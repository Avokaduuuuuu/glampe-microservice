package com.avocado.s3;

import com.avocado.campsite.CampSiteEntity;
import com.avocado.campsite.CampSiteRepository;
import com.avocado.campsite.dto.resp.CampSiteResponse;
import com.avocado.exception.CampSiteException;
import com.avocado.exception.ResultCode;
import com.avocado.gallery.CampSiteGalleryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final CampSiteRepository campSiteRepository;

    @Value("${aws.services.s3.bucket.name}")
    private String bucketName;

    @Override
    public List<String> uploadCampSiteImages(List<MultipartFile> files, Long id) {
        CampSiteEntity campSite = campSiteRepository.findById(id)
                .orElseThrow(() -> new CampSiteException(ResultCode.CAMPSITE_NOT_FOUND));
        String prefix = campSite.getName() + "/images";
        String folderName = "camp_sites";
        List<CampSiteGalleryEntity> galleries = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = folderName + "/" + prefix + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            try {
                s3Client.putObject(
                        PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(fileName)
                                .contentType(file.getContentType())
                                .build(),
                        RequestBody.fromBytes(file.getBytes())
                );
                galleries.add(
                        CampSiteGalleryEntity.builder()
                                .path(fileName)
                                .campSite(campSite)
                                .build()
                );
            } catch (IOException e) {
                throw new CampSiteException(ResultCode.INVALID_FILE);
            }
        }
        campSite.setGalleries(galleries);
        campSiteRepository.save(campSite);

        return galleries.stream().map(CampSiteGalleryEntity::getPath).collect(Collectors.toList());
    }

    @Override
    public String generateUrl(String fileName) {

        String url = "";
        if (fileName != null && !fileName.isEmpty()) {
            GetObjectPresignRequest request = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofHours(2))
                    .getObjectRequest(req -> req.bucket(bucketName).key(fileName))
                    .build();
            PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(request);
            url = presignedGetObjectRequest.url().toString();
        }

        return url;
    }
}
