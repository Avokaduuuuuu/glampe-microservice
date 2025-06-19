package com.avocado.bookingselection;

import com.avocado.booking.BookingEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "booking_selection")
public class BookingSelectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "id_selection")
    Long selectionId;

    @Column(name = "name")
    String name;

    @Column(name = "quantity")
    Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_booking")
    BookingEntity booking;
}
