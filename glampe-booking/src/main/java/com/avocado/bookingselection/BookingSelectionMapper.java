package com.avocado.bookingselection;

import com.avocado.bookingselection.dto.resp.BookingSelectionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookingSelectionMapper {
    BookingSelectionMapper INSTANCE = Mappers.getMapper(BookingSelectionMapper.class);

    @Mapping(target = "bookingId", source = "id")
    BookingSelectionResponse toResponse (BookingSelectionEntity bookingSelectionEntity);
}
