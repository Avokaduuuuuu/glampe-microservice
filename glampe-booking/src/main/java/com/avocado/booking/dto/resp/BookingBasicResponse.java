package com.avocado.booking.dto.resp;

import com.avocado.bookingdetail.dto.resp.BookingDetailResponse;
import com.avocado.bookingselection.dto.resp.BookingSelectionBasicResponse;
import com.avocado.client.campsite.dto.BookingBasicCampSiteResponse;
import com.avocado.client.user.dto.BookingUserResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingBasicResponse {
    Long id;
    BookingUserResponse user;
    String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime checkInAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime checkOutAt;
    BigDecimal totalAmount;
    BigDecimal systemFee;
    BigDecimal netAmount;
    String comment;
    Integer rating;
    String message;
    Boolean isPointUsed;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;
    BookingBasicCampSiteResponse campSite;
    List<BookingDetailResponse> bookingDetails;
    List<BookingSelectionBasicResponse> bookingSelections;
}
