package com.avocado.client.booking.dto.resp;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CampTypeBookedResponse {
    Long campTypeId;
    Long bookedQuantity;
}
