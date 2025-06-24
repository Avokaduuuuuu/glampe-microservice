package com.avocado.bookingdetail.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailAvailabilityResponse {
    private Long campTypeId;
    private Long bookedQuantity;
}
