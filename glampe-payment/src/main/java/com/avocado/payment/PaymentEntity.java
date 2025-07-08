package com.avocado.payment;

import com.avocado.payment.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "payment")
@EntityListeners(AuditingEntityListener.class)
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "id_booking")
    Long bookingId;

    @Column(name = "payment_method")
    String paymentMethod;

    @Column(name = "payment_intent_id")
    String paymentIntentId;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    PaymentStatusEnum status;


    @Column(name = "completed_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime completedAt;

    @Column(name = "created_at")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    LocalDateTime updatedAt;

}
