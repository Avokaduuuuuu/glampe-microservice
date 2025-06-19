package com.avocado.booking;

import com.avocado.booking.enums.BookingStatusEnum;
import com.avocado.bookingdetail.BookingDetailEntity;
import com.avocado.bookingselection.BookingSelectionEntity;
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
@Table(name = "booking")
@EntityListeners(AuditingEntityListener.class)
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "id_user")
    Long userId;

    @Column(name = "id_camp_site")
    Long campSiteId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    BookingStatusEnum status;

    @Column(name = "check_in_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime checkInAt;

    @Column(name = "check_out_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime checkOutAt;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

    @Column(name = "system_fee")
    BigDecimal systemFee;

    @Column(name = "net_amount")
    BigDecimal netAmount;

    @Column(name = "comment")
    @Builder.Default
    String comment = "";

    @Column(name = "rating")
    @Builder.Default
    Integer rating = 0;

    @Column(name = "message")
    @Builder.Default
    String message = "";

    @Column(name = "is_point_used")
    @Builder.Default
    Boolean isPointUsed = false;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BookingDetailEntity> bookingDetails;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BookingSelectionEntity> bookingSelections;
}
