package com.avocado.fcmtoken;


import com.avocado.config.ApiResponse;
import com.avocado.fcmtoken.dto.req.FcmTokenUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users/fcm-tokens")
@RequiredArgsConstructor
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @PostMapping
    public ResponseEntity<?> saveFcmToken(@RequestBody FcmTokenUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Saved Fcm token")
                        .data(fcmTokenService.saveFcmToken(request))
                        .build()
        );
    }

    @DeleteMapping
    public ResponseEntity<?> deleteToken(@RequestParam("userId") Long userId, @RequestParam("deviceId") String deviceId) {
        fcmTokenService.deleteFcmToken(userId, deviceId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                    .message("Deleted Fcm token")
                        .data("Delete Fcm token")
                    .build()
        );
    }
}
