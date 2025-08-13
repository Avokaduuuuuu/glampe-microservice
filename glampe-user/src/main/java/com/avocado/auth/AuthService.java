package com.avocado.auth;

import com.avocado.user.dto.resp.UserResponse;

public interface AuthService {
    UserResponse authenticate(String email);
}
