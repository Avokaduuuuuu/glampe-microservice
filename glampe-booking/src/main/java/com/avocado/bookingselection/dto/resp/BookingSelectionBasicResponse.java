package com.avocado.bookingselection.dto.resp;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingSelectionBasicResponse {

    Long id;
    Long selectionId;
    String name;
    Integer quantity;
    Long bookingId;
}
