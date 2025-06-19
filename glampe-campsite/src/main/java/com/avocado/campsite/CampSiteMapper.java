package com.avocado.campsite;

import com.avocado.campsite.dto.resp.CampSiteResponse;
import com.avocado.camptype.CampTypeMapper;
import com.avocado.gallery.CampSiteGalleryMapper;
import com.avocado.placetype.PlaceTypeMapper;
import com.avocado.s3.S3Service;
import com.avocado.selection.SelectionMapper;
import com.avocado.utility.UtilityMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UtilityMapper.class, PlaceTypeMapper.class, SelectionMapper.class, CampSiteGalleryMapper.class, CampTypeMapper.class}
)
public interface CampSiteMapper {
    CampSiteMapper INSTANCE = Mappers.getMapper(CampSiteMapper.class);
    CampSiteResponse toResponse(CampSiteEntity entity, @Context S3Service s3Service);

}
