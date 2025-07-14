package com.avocado.fcmtoken;

import com.avocado.fcmtoken.dto.req.FcmTokenUpdateRequest;
import com.avocado.fcmtoken.dto.resp.FcmTokenResponse;

public interface FcmTokenService {
    FcmTokenResponse saveFcmToken(FcmTokenUpdateRequest request);
    void deleteFcmToken(Long userId, String deviceId);
}
