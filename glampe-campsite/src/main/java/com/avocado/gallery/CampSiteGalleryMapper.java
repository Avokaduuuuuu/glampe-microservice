package com.avocado.gallery;

import com.avocado.gallery.dto.resp.CampSiteGalleryResponse;
import com.avocado.s3.S3Service;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CampSiteGalleryMapper {
    CampSiteGalleryMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CampSiteGalleryMapper.class);

    @Mapping(target = "path", expression = "java(s3Service.generateUrl(entity.getPath()))")
    CampSiteGalleryResponse toResponse(CampSiteGalleryEntity entity, @Context S3Service s3Service);
}
