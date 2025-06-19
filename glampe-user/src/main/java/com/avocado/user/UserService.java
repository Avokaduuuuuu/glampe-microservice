package com.avocado.user;

import com.avocado.user.dto.resp.UserResponse;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserResponse getUserById(Long id);

    Map<Long, UserResponse> getUsersByIds(List<Long> ids);
}
