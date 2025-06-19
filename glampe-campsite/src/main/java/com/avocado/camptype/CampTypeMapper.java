package com.avocado.camptype;

import com.avocado.camp.CampMapper;
import com.avocado.camptype.dto.resp.CampTypeResponse;
import com.avocado.facility.FacilityMapper;
import com.avocado.placetype.PlaceTypeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {FacilityMapper.class, CampMapper.class}
)
public interface CampTypeMapper {
    CampTypeMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CampTypeMapper.class);
    CampTypeResponse toResponse(CampTypeEntity entity);
}
