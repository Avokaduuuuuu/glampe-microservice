package com.avocado.bookingdetail;

import com.avocado.bookingdetail.dto.req.CampTypeBookedRequest;
import com.avocado.bookingdetail.dto.resp.BookingDetailAvailabilityResponse;

import java.util.List;

public interface BookingDetailService {
    List<BookingDetailAvailabilityResponse> getBookedCampType(CampTypeBookedRequest request);
}
