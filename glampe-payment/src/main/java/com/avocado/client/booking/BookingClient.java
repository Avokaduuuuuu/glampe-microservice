package com.avocado.client.booking;

import com.avocado.client.booking.req.BookingUpdateRequest;
import com.avocado.client.booking.resp.PaymentBookingBasicResponse;
import com.avocado.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name = "booking-service", url = "http://localhost:8083", path = "/api/v1/bookings")
public interface BookingClient {

    @PutMapping("/{id}")
    ApiResponse<PaymentBookingBasicResponse> updateBooking(@RequestBody BookingUpdateRequest request, @PathVariable("id") Long id);
}
