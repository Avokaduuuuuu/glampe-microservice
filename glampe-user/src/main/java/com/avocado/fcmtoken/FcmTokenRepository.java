package com.avocado.fcmtoken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmTokenRepository extends JpaRepository<FcmTokenEntity, Long> {

    boolean existsByUserIdAndDeviceId(Long userId, String deviceId);
    void deleteByUserIdAndDeviceId(Long userId, String deviceId);
}
