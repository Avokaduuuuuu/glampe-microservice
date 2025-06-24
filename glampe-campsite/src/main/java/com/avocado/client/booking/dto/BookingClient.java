package com.avocado.client.booking.dto;

import com.avocado.client.booking.dto.req.CampTypeBookedRequest;
import com.avocado.client.booking.dto.resp.CampTypeBookedResponse;
import com.avocado.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "booking-service", url = "http://localhost:8083", path = "/api/v1/bookings")
public interface BookingClient {

    @PostMapping("/booking-details/camp-types")
    ApiResponse<List<CampTypeBookedResponse>> bookedCampTypes(@RequestBody CampTypeBookedRequest request);
}
