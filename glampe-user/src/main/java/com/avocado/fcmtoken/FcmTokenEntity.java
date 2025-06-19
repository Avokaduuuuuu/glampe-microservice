package com.avocado.fcmtoken;

import com.avocado.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "fcm_token")
public class FcmTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "token")
    String token;

    @Column(name = "id_device")
    String deviceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    UserEntity user;
}