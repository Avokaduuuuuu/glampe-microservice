package com.avocado.placetype;

import com.avocado.placetype.dto.resp.PlaceTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface PlaceTypeMapper {
    PlaceTypeMapper INSTANCE = Mappers.getMapper(PlaceTypeMapper.class);
    PlaceTypeResponse toResponse(PlaceTypeEntity entity);
}
