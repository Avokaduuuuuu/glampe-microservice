package com.avocado.payment;

import com.avocado.payment.dto.req.PaymentIntentRequest;
import com.avocado.payment.dto.resp.PaymentIntentResponse;
import com.avocado.payment.dto.resp.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentIntentResponse createPayment(PaymentIntentRequest paymentRequest);
    PaymentResponse processPayment(String paymentIntentId);
}
