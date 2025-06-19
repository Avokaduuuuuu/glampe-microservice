package com.avocado.booking;

import com.avocado.booking.dto.req.BookingRequest;
import com.avocado.booking.dto.resp.BookingResponse;
import com.avocado.utils.PageResponse;
import jakarta.validation.Valid;

public interface BookingService {
    BookingResponse fetchBookingById(Long id);

    BookingResponse createBooking(@Valid BookingRequest request);

    PageResponse<BookingResponse> fetchAll(BookingFilterParams params);
}
