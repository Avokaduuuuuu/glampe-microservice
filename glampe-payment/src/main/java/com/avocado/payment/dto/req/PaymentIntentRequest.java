package com.avocado.payment.dto.req;

import java.math.BigDecimal;

public record PaymentIntentRequest(
        BigDecimal amount,
        String currency,
        Long bookingId,
        String method
) {

    public Long getStripeAmount() {
        return amount.multiply(BigDecimal.valueOf(100)).longValue();
    }
}
