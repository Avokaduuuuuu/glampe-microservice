package com.avocado.bookingdetail;

import com.avocado.booking.BookingEntity;
import com.avocado.bookingdetail.dto.resp.BookingDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface BookingDetailMapper {
    BookingDetailMapper INSTANCE = Mappers.getMapper(BookingDetailMapper.class);

    @Mapping(target = "bookingId", source = "id")
    @Mapping(target = "add", source = "addOn")
    BookingDetailResponse toResponse(BookingDetailEntity bookingDetailEntity);
}
