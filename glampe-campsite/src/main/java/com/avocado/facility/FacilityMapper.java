package com.avocado.facility;

import com.avocado.facility.dto.resp.FacilityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface FacilityMapper {
    FacilityMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(FacilityMapper.class);
    FacilityResponse toResponse(FacilityEntity entity);
}
