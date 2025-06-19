package com.avocado.bookingdetailorder;

import com.avocado.bookingdetail.BookingDetailEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "booking_detail_order")
public class BookingDetailOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

    @Column(name = "note")
    String note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_booking_detail")
    BookingDetailEntity bookingDetail;

}
