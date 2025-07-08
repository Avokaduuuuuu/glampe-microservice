package com.avocado.campsite;

import com.avocado.camp.CampEntity;
import com.avocado.camp.enums.CampStatusEnum;
import com.avocado.campsite.cache.Cache;
import com.avocado.campsite.cache.RedisPrefix;
import com.avocado.campsite.dto.req.CampSiteCreateRequest;
import com.avocado.campsite.dto.req.CampSiteUpdateRequest;
import com.avocado.campsite.dto.resp.CampSiteUserResponse;
import com.avocado.campsite.enums.CampSiteStatusEnum;
import com.avocado.camptype.CampTypeEntity;
import com.avocado.camptype.dto.req.CampTypeCreateRequest;
import com.avocado.client.user.UserClient;
import com.avocado.campsite.dto.resp.CampSiteResponse;
import com.avocado.client.email.EmailEntity;
import com.avocado.exception.CampSiteException;
import com.avocado.exception.ResultCode;
import com.avocado.facility.FacilityEntity;
import com.avocado.facility.FacilityRepository;
import com.avocado.gallery.CampSiteGalleryMapper;
import com.avocado.kafka.NotificationProducer;
import com.avocado.placetype.PlaceTypeEntity;
import com.avocado.placetype.PlaceTypeRepository;
import com.avocado.s3.S3Service;
import com.avocado.selection.SelectionEntity;
import com.avocado.selection.dto.req.SelectionCreateRequest;
import com.avocado.utility.UtilityEntity;
import com.avocado.utility.UtilityRepository;
import com.avocado.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampSiteServiceImpl implements CampSiteService{

    private final UserClient userClient;
    private final CampSiteRepository campSiteRepository;
    private final Cache<CampSiteResponse> cache;
    private final NotificationProducer notificationProducer;
    private final UtilityRepository utilityRepository;
    private final PlaceTypeRepository placeTypeRepository;
    private final FacilityRepository facilityRepository;
    private final S3Service s3Service;

    @Override
    public CampSiteResponse getCampSiteById(Long id) {
        CampSiteResponse response = cache.get(RedisPrefix.CAMPSITE.getPrefix() + id);
        if (response != null) {
            response.getGalleries().forEach(gallery -> gallery.setPath(gallery.toS3PresignedUrl(s3Service)));
            response.getCampTypes().forEach(campType -> campType.setImage(campType.toS3PresignedUrl(s3Service)));
            response.getSelections().forEach(selection -> selection.setImage(selection.toS3PresignUrl(s3Service)));
//            notificationProducer.send(EmailEntity.builder()
//                    .subject("Fetch Campsite")
//                    .content("Fetch Campsite with id: " + id)
//                    .to("huatanthinh1207@gmail.com")
//                    .build());
            return response;
        }

        CampSiteEntity campSiteEntity = campSiteRepository
                .findById(id)
                .orElseThrow(() -> new CampSiteException(ResultCode.CAMPSITE_NOT_FOUND));

        response = CampSiteMapper.INSTANCE.toResponseWithoutImage(campSiteEntity);
        response.setUser(userClient.getUserById(campSiteEntity.getUserId()).getData());
        cache.put(RedisPrefix.CAMPSITE.getPrefix() + id, response);
        response.setGalleries(campSiteEntity.getGalleries().stream().map(gallery -> CampSiteGalleryMapper.INSTANCE.toResponseWithS3(gallery, s3Service)).toList());
        response.getCampTypes().forEach(campType -> campType.setImage(campType.toS3PresignedUrl(s3Service)));
        response.getSelections().forEach(selection -> selection.setImage(selection.toS3PresignUrl(s3Service)));

        return response;
    }

    @Override
    public PageResponse<CampSiteResponse> getAll(CampSiteFilterParams filterParams) {
        Specification<CampSiteEntity> specification = filterParams.getSpecification();
        Sort sort = Sort.by(Sort.Direction.fromString(filterParams.getSortOrder()), filterParams.getSortBy());
        Pageable pageable = PageRequest.of(filterParams.getCurrentPage(), filterParams.getPageSize(), sort);

        Page<CampSiteEntity> entities = campSiteRepository.findAll(specification, pageable);

        List<Long> userIds = entities.stream()
                .map(CampSiteEntity::getUserId)
                .distinct()
                .toList();

        Map<Long, CampSiteUserResponse> userMap = userClient.getUsersByIds(userIds).getData();

        return PageResponse.<CampSiteResponse>builder()
                .currentPage(filterParams.getCurrentPage())
                .pageSize(filterParams.getPageSize())
                .sortBy(filterParams.getSortBy())
                .sortOrder(filterParams.getSortOrder())
                .totalPages(entities.getTotalPages())
                .totalRecords(entities.getTotalElements())
                .records(
                        entities.getContent().stream()
                                .map(entity -> {
                                    CampSiteResponse response = CampSiteMapper.INSTANCE.toResponse(entity, s3Service);
                                    response.setUser(userMap.get(entity.getUserId()));
                                    return response;
                                })
                                .toList()
                )
                .build();
    }

    @Override
    public CampSiteResponse addCampSite(CampSiteCreateRequest request) {
        userClient.getUserById(request.uerId());
        CampSiteEntity campSiteEntity = CampSiteEntity.builder()
                .name(request.name())
                .address(request.address())
                .city(request.city())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .description(request.description())
                .depositRate(request.depositRate())
                .userId(request.uerId())
                .utilities(toUtilities(request.utilityIds()))
                .placeTypes(toPlaceTypes(request.placeTypeIds()))
                .status(CampSiteStatusEnum.Pending)
                .build();
        campSiteEntity.setSelections(toSelections(request.selections(), campSiteEntity));
        campSiteEntity.setCampTypes(toCampType(request.campTypes(), campSiteEntity));
        return CampSiteMapper.INSTANCE.toResponse(campSiteRepository.save(campSiteEntity), s3Service);
    }

    @Override
    public CampSiteResponse updateCampSite(CampSiteUpdateRequest request, Long id) {
        CampSiteResponse response = cache.get(RedisPrefix.CAMPSITE.getPrefix() + id);
        if (response != null) {
            cache.remove(RedisPrefix.CAMPSITE.getPrefix() + id);
        }

        CampSiteEntity campSiteEntity = campSiteRepository.save(
                request.toModifiedCampSite(campSiteRepository.findById(id)
                        .orElseThrow(() -> new CampSiteException(ResultCode.CAMPSITE_NOT_FOUND)))
        );
        response = CampSiteMapper.INSTANCE.toResponse(campSiteEntity, s3Service);
        response.setUser(userClient.getUserById(id).getData());

        cache.put(RedisPrefix.CAMPSITE.getPrefix() + id, response);
        return response;
    }

    @Override
    public Map<Long, CampSiteResponse> fetchBatch(List<Long> ids) {
        return campSiteRepository.findAllByIdIn(ids)
                .stream().collect(Collectors.toMap(CampSiteEntity::getId, entity -> CampSiteMapper.INSTANCE.toResponse(entity, s3Service)));
    }

    private List<UtilityEntity> toUtilities(List<Long> ids){
        return ids.stream()
                .map(id -> utilityRepository
                        .findById(id)
                        .orElseThrow(() -> new CampSiteException(ResultCode.UTILITY_NOT_FOUND))
                ).toList();
    }

    private List<PlaceTypeEntity> toPlaceTypes(List<Long> ids){
        return ids.stream()
                .map(id -> placeTypeRepository
                        .findById(id)
                        .orElseThrow(() -> new CampSiteException(ResultCode.PLACE_TYPE_NOT_FOUND)))
                .toList();
    }

    private List<FacilityEntity> toFacilities(List<Long> ids){
        return ids.stream()
                .map(id -> facilityRepository
                        .findById(id)
                        .orElseThrow(() -> new CampSiteException(ResultCode.FACILITY_NOT_FOUND)))
                .toList();
    }

    private List<SelectionEntity> toSelections(List<SelectionCreateRequest> requests, CampSiteEntity campSiteEntity){
        return requests.stream()
                .map(request -> SelectionEntity
                        .builder()
                        .name(request.name())
                        .description(request.description())
                        .price(request.price())
                        .campSite(campSiteEntity)
                        .build()
                ).toList();
    }

    private List<CampTypeEntity> toCampType(List<CampTypeCreateRequest> requests, CampSiteEntity campSiteEntity){

        return requests.stream()
                .map(request -> {
                    CampTypeEntity campType = CampTypeEntity.builder()
                            .type(request.type())
                            .capacity(request.capacity())
                            .quantity(request.quantity())
                            .price(request.price())
                            .weekendPrice(request.weekendPrice())
                            .campSite(campSiteEntity)
                            .facilities(toFacilities(request.facilityIds()))
                            .build();

                    List<CampEntity> campEntities = new ArrayList<>();
                    for (int i = 0; i < request.quantity(); i++) {
                        campEntities.add(CampEntity
                                .builder()
                                .name(request.type() + " " + (i + 1))
                                .status(CampStatusEnum.Unassigned)
                                .campType(campType)
                                .build());
                    }
                    campType.setCamps(campEntities);
                    return campType;
                }
                ).toList();
    }


}
