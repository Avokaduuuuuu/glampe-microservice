package com.avocado.auth;


import com.avocado.auth.dto.req.AuthRequest;
import com.avocado.auth.dto.resp.AuthUser;
import com.avocado.auth.jwt.JwtProvider;
import com.avocado.user.dto.resp.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        UserResponse userResponse = authService.authenticate(authRequest.email());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(AuthUser.builder()
                        .token(jwtProvider.generateTokenWithClaims(List.of(userResponse.getRole()), userResponse.getEmail()))
                        .user(userResponse)
                        .build()
                );
    }
}
