package com.avocado.utility;

import com.avocado.utility.dto.resp.UtilityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UtilityMapper {
    UtilityMapper INSTANCE = Mappers.getMapper(UtilityMapper.class);
    UtilityResponse toResponse(UtilityEntity entity);
}
