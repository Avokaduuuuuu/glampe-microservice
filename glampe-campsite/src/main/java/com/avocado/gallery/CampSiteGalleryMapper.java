package com.avocado.gallery;

import com.avocado.gallery.dto.resp.CampSiteGalleryResponse;
import com.avocado.s3.S3Service;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CampSiteGalleryMapper {
    CampSiteGalleryMapper INSTANCE = Mappers.getMapper(CampSiteGalleryMapper.class);

    @Named("withS3")
    @Mapping(target = "path", expression = "java(s3Service.generateUrl(entity.getPath()))")
    CampSiteGalleryResponse toResponseWithS3(CampSiteGalleryEntity entity, @Context S3Service s3Service);

    @Named("raw")
    @Mapping(target = "path", source = "path")
    CampSiteGalleryResponse toResponseRaw(CampSiteGalleryEntity entity);
}
