package com.avocado.payment;

import com.avocado.payment.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "id_booking")
    Long bookingId;

    @Column(name = "payment_method")
    String paymentMethod;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

    @Column(name = "status")
    PaymentStatusEnum status;

    @Column(name = "id_transaction")
    String transactionId;

    @Column(name = "id_session")
    String sessionId;

    @Column(name = "url")
    String url;

    @Column(name = "completed_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    String completedAt;

    @Column(name = "created_at")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    String createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    String updatedAt;

}
