package com.avocado.client.campsite;

import com.avocado.client.campsite.dto.BookingBasicCampSiteResponse;
import com.avocado.client.campsite.dto.BookingCampSiteResponse;
import com.avocado.client.campsite.dto.BookingCampSiteSelectionResponse;
import com.avocado.client.campsite.dto.BookingCampTypeResponse;
import com.avocado.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "campsite-service", url = "http://localhost:8082", path = "/api/v1/campsites")
@Component
public interface CampSiteClient {
    @GetMapping("/{id}")
    ApiResponse<BookingCampSiteResponse> getBookingCampSite(@PathVariable("id") Long id);


    @PostMapping("/camp-types/batch")
    ApiResponse<Map<Long, BookingCampTypeResponse>> getBookingCampTypes(@RequestBody List<Long> ids);

    @PostMapping("/batch")
    ApiResponse<Map<Long, BookingBasicCampSiteResponse>> getCampSitesBatch(@RequestBody List<Long> ids);

    @PostMapping("/selections/batch")
    ApiResponse<Map<Long, BookingCampSiteSelectionResponse>> getCampSitesSelections(@RequestBody List<Long> ids);
}
