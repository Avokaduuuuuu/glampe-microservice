package com.avocado.payment.dto.resp;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentIntentResponse {

    String clientSecret;
    String paymentIntentId;
}
