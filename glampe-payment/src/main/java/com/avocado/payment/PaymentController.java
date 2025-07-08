package com.avocado.payment;


import com.avocado.payment.dto.req.PaymentIntentRequest;
import com.avocado.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-intent")
    public ResponseEntity<?> createIntent(@RequestBody PaymentIntentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.builder()
                                .message("Create Intent")
                                .statusCode(HttpStatus.CREATED.value())
                                .data(paymentService.createPayment(request))
                                .build()
                );
    }

    @PostMapping("/process-charge")
    public ResponseEntity<?> processCharge(@RequestParam String paymentIntentId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.builder()
                                .message("Process Charge")
                                .statusCode(HttpStatus.OK.value())
                                .data(paymentService.processPayment(paymentIntentId))
                                .build()
                );
    }
}
