package com.avocado.user;

import com.avocado.user.dto.req.UserAddRequest;
import com.avocado.user.dto.req.UserVerifyRequest;
import com.avocado.user.dto.resp.AuthUserResponse;
import com.avocado.user.dto.resp.UserResponse;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserResponse getUserById(Long id);

    Map<Long, UserResponse> getUsersByIds(List<Long> ids);
    AuthUserResponse verifyUser(UserVerifyRequest request);
    AuthUserResponse addNewUser(UserAddRequest request);
}
