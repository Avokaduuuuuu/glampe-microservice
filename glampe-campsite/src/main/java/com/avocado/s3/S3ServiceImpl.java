package com.avocado.s3;

import com.avocado.campsite.CampSiteEntity;
import com.avocado.campsite.CampSiteRepository;
import com.avocado.campsite.cache.Cache;
import com.avocado.campsite.cache.RedisPrefix;
import com.avocado.campsite.dto.resp.CampSiteResponse;
import com.avocado.camptype.CampTypeEntity;
import com.avocado.camptype.CampTypeRepository;
import com.avocado.exception.CampSiteException;
import com.avocado.exception.ResultCode;
import com.avocado.facility.FacilityEntity;
import com.avocado.facility.FacilityRepository;
import com.avocado.gallery.CampSiteGalleryEntity;
import com.avocado.placetype.PlaceTypeEntity;
import com.avocado.placetype.PlaceTypeRepository;
import com.avocado.selection.SelectionEntity;
import com.avocado.selection.SelectionRepository;
import com.avocado.utility.UtilityEntity;
import com.avocado.utility.UtilityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final CampSiteRepository campSiteRepository;
    private final UtilityRepository utilityRepository;
    private final FacilityRepository facilityRepository;
    private final SelectionRepository selectionRepository;
    private final CampTypeRepository campTypeRepository;
    private final Cache<CampSiteResponse> cache;
    private final PlaceTypeRepository placeTypeRepository;

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
    public String uploadImage(MultipartFile file, String type, Long id) {
        String folder;
        String prefix;
        String fileName = "";
        String url = "https://" + bucketName + ".s3.ap-southeast-1.amazonaws.com/";
        log.info("Filename: " + file.getOriginalFilename());
        try {
            switch (type) {
                case "utility" -> {
                    UtilityEntity utilityEntity = utilityRepository.findById(id)
                            .orElseThrow(() -> new CampSiteException(ResultCode.UTILITY_NOT_FOUND));
                    folder = "utilities";
                    fileName = folder + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    url += fileName;
                    utilityEntity.setImage(url);
                    utilityRepository.save(utilityEntity);
                }
                case "facility" -> {
                    FacilityEntity facilityEntity = facilityRepository.findById(id)
                            .orElseThrow(() -> new CampSiteException(ResultCode.FACILITY_NOT_FOUND));
                    folder = "facilities";
                    fileName = folder + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    url += fileName;
                    facilityEntity.setImage(url);
                    facilityRepository.save(facilityEntity);
                }
                case "placeType" -> {
                    PlaceTypeEntity placeTypeEntity = placeTypeRepository.findById(id)
                            .orElseThrow(() -> new CampSiteException(ResultCode.PLACE_TYPE_NOT_FOUND));
                    folder = "places";
                    fileName = folder + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    url += fileName;
                    placeTypeEntity.setImage(url);
                    placeTypeRepository.save(placeTypeEntity);
                }
                case "selection" -> {
                    SelectionEntity selectionEntity = selectionRepository.findById(id)
                            .orElseThrow(() -> new CampSiteException(ResultCode.SELECTION_NOT_FOUND));
                    CampSiteEntity campSiteEntity = campSiteRepository.findById(selectionEntity.getCampSite().getId())
                            .orElseThrow(() -> new CampSiteException(ResultCode.CAMPSITE_NOT_FOUND));
                    folder = "camp_sites";
                    prefix = campSiteEntity.getName() + "/" + "selections";
                    fileName = folder + "/" + prefix + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    selectionEntity.setImage(fileName);
                    selectionRepository.save(selectionEntity);
                    cache.remove(RedisPrefix.CAMPSITE.getPrefix() + campSiteEntity.getId());
                }
                case "campType" -> {
                    CampTypeEntity campType = campTypeRepository.findById(id)
                            .orElseThrow(() -> new CampSiteException(ResultCode.CAMPTYPE_NOT_FOUND));
                    CampSiteEntity campSiteEntity = campSiteRepository.findById(campType.getCampSite().getId())
                            .orElseThrow(() -> new CampSiteException(ResultCode.CAMPSITE_NOT_FOUND));
                    folder = "camp_sites";
                    prefix = campSiteEntity.getName() + "/" + "camptypes";
                    fileName = folder + "/" + prefix + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    campType.setImage(fileName);
                    campTypeRepository.save(campType);
                    cache.remove(RedisPrefix.CAMPSITE.getPrefix() + campSiteEntity.getId());

                }
            }
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes())
            );
        }catch (IOException e) {
            e.printStackTrace();
            throw new CampSiteException(ResultCode.INVALID_FILE);
        }
        return fileName;
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
