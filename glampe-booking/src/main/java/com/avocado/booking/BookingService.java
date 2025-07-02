package com.avocado.booking;

import com.avocado.booking.dto.req.BookingRequest;
import com.avocado.booking.dto.resp.BookingBasicResponse;
import com.avocado.booking.dto.resp.BookingResponse;
import com.avocado.utils.PageResponse;
import jakarta.validation.Valid;

public interface BookingService {
    BookingResponse fetchBookingById(Long id);

    BookingBasicResponse createBooking(@Valid BookingRequest request);

    PageResponse<BookingBasicResponse> fetchAll(BookingFilterParams params);
}
