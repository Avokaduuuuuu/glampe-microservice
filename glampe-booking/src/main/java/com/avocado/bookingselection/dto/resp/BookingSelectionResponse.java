package com.avocado.bookingselection.dto.resp;

import com.avocado.client.campsite.dto.BookingCampSiteSelectionResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingSelectionResponse {

    Long id;
    BookingCampSiteSelectionResponse selection;
    String name;
    Integer quantity;
}
