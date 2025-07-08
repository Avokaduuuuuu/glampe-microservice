package com.avocado.payment;

import com.avocado.client.booking.BookingClient;
import com.avocado.client.booking.req.BookingUpdateRequest;
import com.avocado.payment.dto.req.PaymentIntentRequest;
import com.avocado.payment.dto.resp.PaymentIntentResponse;
import com.avocado.payment.dto.resp.PaymentResponse;
import com.avocado.payment.enums.PaymentStatusEnum;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    @Value("${stripe.secret.key}")
    String SECRET_KEY;

    private final PaymentRepository paymentRepository;
    private final BookingClient bookingClient;

    @Override
    public PaymentIntentResponse createPayment(PaymentIntentRequest paymentRequest){
        Stripe.apiKey = SECRET_KEY;

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(paymentRequest.getStripeAmount())
                .setCurrency("USD")
                .addPaymentMethodType(paymentRequest.method())
                .build();

        PaymentIntent intent;
        try {
            intent = PaymentIntent.create(params);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        PaymentEntity payment = PaymentEntity.builder()
                .bookingId(paymentRequest.bookingId())
                .paymentMethod(paymentRequest.method())
                .status(PaymentStatusEnum.Pending)
                .totalAmount(paymentRequest.amount())
                .paymentIntentId(intent.getId())
                .build();

        paymentRepository.save(payment);

        return PaymentIntentResponse.builder()
                .clientSecret(intent.getClientSecret())
                .paymentIntentId(intent.getId())
                .build();
    }

    @Override
    public PaymentResponse processPayment(String paymentIntentId) {
        PaymentEntity payment = paymentRepository.findByPaymentIntentId(paymentIntentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        updatePaymentStatus(payment);
        return PaymentMapper.INSTANCE.toResponse(payment);
    }

    private void updatePaymentStatus(PaymentEntity payment) {
        Stripe.apiKey = SECRET_KEY;
        try {
            PaymentIntent intent = PaymentIntent.retrieve(payment.getPaymentIntentId());
            PaymentStatusEnum newStatus;
            switch (intent.getStatus()) {
                case "succeeded" -> {
                    newStatus = PaymentStatusEnum.Completed;
                    bookingClient.updateBooking(new BookingUpdateRequest("Deposit"), payment.getBookingId());
                }
                case "payment_failed", "canceled" -> newStatus = PaymentStatusEnum.Failed;
                default -> newStatus = PaymentStatusEnum.Pending;
            }

            payment.setStatus(newStatus);
            paymentRepository.save(payment);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }


    }


}
