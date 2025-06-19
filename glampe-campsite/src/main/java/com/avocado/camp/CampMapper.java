package com.avocado.camp;

import com.avocado.camp.dto.resp.CampResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface CampMapper {
    CampMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CampMapper.class);
    CampResponse toResponse(CampEntity entity);
}
