package com.avocado.selection;

import com.avocado.selection.dto.resp.SelectionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SelectionMapper {
    SelectionMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(SelectionMapper.class);
    SelectionResponse toResponse(SelectionEntity entity);
}
