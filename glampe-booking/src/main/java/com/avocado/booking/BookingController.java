package com.avocado.booking;

import com.avocado.booking.dto.req.BookingRequest;
import com.avocado.booking.dto.req.BookingUpdateRequest;
import com.avocado.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Fetching booking by id")
                                .data(bookingService.fetchBookingById(id))
                                .build()
                );
    }

    @PostMapping
    public ResponseEntity<?> addBooking(@RequestBody @Valid BookingRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Booking created")
                                .data(bookingService.createBooking(request))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings(BookingFilterParams params){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Fetching all bookings")
                                .data(bookingService.fetchAll(params))
                                .build()
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@RequestBody @Valid BookingUpdateRequest request, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Booking updated")
                                .data(bookingService.updateBooking(request, id))
                                .build()
                );
    }
}
