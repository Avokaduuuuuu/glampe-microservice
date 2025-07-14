package com.avocado.fcmtoken;

import com.avocado.exception.ResultCode;
import com.avocado.exception.UserException;
import com.avocado.fcmtoken.dto.req.FcmTokenUpdateRequest;
import com.avocado.fcmtoken.dto.resp.FcmTokenResponse;
import com.avocado.user.UserEntity;
import com.avocado.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmTokenServiceImpl implements FcmTokenService{

    private final FcmTokenRepository fcmTokenRepository;
    private final UserRepository userRepository;

    @Override
    public FcmTokenResponse saveFcmToken(FcmTokenUpdateRequest request) {
        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserException(ResultCode.USER_NOT_FOUND));
        if (!fcmTokenRepository.existsByUserIdAndDeviceId(user.getId(), request.deviceId())) {
            return FcmTokenMapper.INSTANCE.toResponse(fcmTokenRepository.save(
                    FcmTokenEntity.builder()
                            .token(request.token())
                            .user(user)
                            .deviceId(request.deviceId())
                            .build()
            ));
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteFcmToken(Long userId, String deviceId) {
        if (fcmTokenRepository.existsByUserIdAndDeviceId(userId, deviceId)) {
            fcmTokenRepository.deleteByUserIdAndDeviceId(userId, deviceId);
        }
    }
}
