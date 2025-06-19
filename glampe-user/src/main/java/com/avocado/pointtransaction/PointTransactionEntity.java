package com.avocado.pointtransaction;

import com.avocado.pointtransaction.enums.PointTransactionTypeEnum;
import com.avocado.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "point_transaction")
public class PointTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    PointTransactionTypeEnum type;

    @Column(name = "amount")
    Long amount;

    @Column(name = "reason")
    String reason;

    @Column(name = "id_booking")
    Long bookingId;

    @Column(name = "description")
    String description;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    UserEntity user;
}
