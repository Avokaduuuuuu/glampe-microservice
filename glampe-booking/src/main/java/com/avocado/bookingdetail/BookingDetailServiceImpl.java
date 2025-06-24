package com.avocado.bookingdetail;

import com.avocado.bookingdetail.dto.req.CampTypeBookedRequest;
import com.avocado.bookingdetail.dto.resp.BookingDetailAvailabilityResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingDetailServiceImpl implements BookingDetailService {

    private final BookingDetailRepository bookingDetailRepository;

    @Override
    public List<BookingDetailAvailabilityResponse> getBookedCampType(CampTypeBookedRequest request) {
        List<BookingDetailAvailabilityResponse> bookingDetailAvailabilityResponses = new ArrayList<>();
        List<Object[]> results = bookingDetailRepository.findBookedBookingsDetail(request.campTypeIds(), request.checkInAt(), request.checkOutAt());
        for (Object[] result : results) {
            bookingDetailAvailabilityResponses.add(new BookingDetailAvailabilityResponse((Long) result[0], (Long) result[1]));
        }
        return bookingDetailAvailabilityResponses;
    }
}
