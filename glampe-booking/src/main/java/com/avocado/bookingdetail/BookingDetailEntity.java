package com.avocado.bookingdetail;

import com.avocado.booking.BookingEntity;
import com.avocado.bookingdetail.enums.BookingDetailStatusEnum;
import com.avocado.bookingdetailorder.BookingDetailOrderEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "booking_detail")
@EntityListeners(AuditingEntityListener.class)
public class BookingDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "id_camp_type")
    Long campTypeId;

    @Column(name = "id_camp")
    Long campId;

    @Column(name = "check_in_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime checkInAt;

    @Column(name = "check_out_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime checkOutAt;

    @Column(name = "amount")
    BigDecimal amount;

    @Column(name = "add_on", columnDefinition = "DECIMAL(10,2) DEFAULT 0.0")
    BigDecimal addOn;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    BookingDetailStatusEnum status;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_booking")
    BookingEntity booking;

    @OneToMany(mappedBy = "bookingDetail")
    List<BookingDetailOrderEntity> bookingDetailOrders;

    @PrePersist
    public void prePersist() {
        if (checkInAt == null) checkInAt = LocalDateTime.now();
        if (checkOutAt == null) checkOutAt = LocalDateTime.now();
        if (addOn == null) addOn = BigDecimal.ZERO;
    }
}
