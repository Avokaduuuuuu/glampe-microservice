package com.avocado.bookingdetail;

import com.avocado.bookingdetail.dto.req.CampTypeBookedRequest;
import com.avocado.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings/booking-details")
@RequiredArgsConstructor
public class BookingDetailController {

    private final BookingDetailService bookingDetailService;

    @PostMapping("/camp-types")
    public ResponseEntity<?> getBookedCampType(@RequestBody CampTypeBookedRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Success")
                                .data(bookingDetailService.getBookedCampType(request))
                                .build()
                );
    }
}
