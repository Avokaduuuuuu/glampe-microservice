package com.avocado.booking;

import com.avocado.booking.dto.resp.BookingBasicResponse;
import com.avocado.booking.dto.resp.BookingResponse;
import com.avocado.bookingselection.BookingSelectionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {BookingSelectionMapper.class}
)
public interface BookingMapper {
    BookingMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(BookingMapper.class);
    BookingBasicResponse toBasicResponse(BookingEntity entity);

    BookingResponse toResponse(BookingEntity entity);
}
