package com.avocado.client.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingUserResponse {
    Long id;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;
    String role;
    String connectionId;
    Boolean isDeleted;
    String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate lastLoginDate;
    Boolean isOwner;
    Long totalPoints;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate updatedAt;
}
