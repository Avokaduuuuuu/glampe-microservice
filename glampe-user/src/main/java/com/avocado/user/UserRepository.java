package com.avocado.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByIdIn(List<Long> ids);
    Optional<UserEntity> findByEmail(String email);
}
