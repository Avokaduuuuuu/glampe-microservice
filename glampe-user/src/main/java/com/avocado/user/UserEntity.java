package com.avocado.user;

import com.avocado.fcmtoken.FcmTokenEntity;
import com.avocado.pointtransaction.PointTransactionEntity;
import com.avocado.user.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email")
    String email;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "address")
    String address;

    @Column(name = "dob")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    UserRoleEnum role;

    @Column(name = "connection_id")
    String connectionId;

    @Column(name = "is_deleted")
    @Builder.Default
    Boolean isDeleted = false;

    @Column(name = "avatar")
    String avatar;

    @Column(name = "last_login_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    LocalDate lastLoginDate;

    @Column(name = "is_owner")
    @Builder.Default
    Boolean isOwner = false;

    @Column(name = "total_points")
    @Builder.Default
    Long totalPoints = 0L;

    @Column(name = "created_at")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate updatedAt;

    @OneToMany(mappedBy = "user")
    List<PointTransactionEntity> pointTransactions;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<FcmTokenEntity> fcmTokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
