package com.avocado.auth;

import com.avocado.exception.ResultCode;
import com.avocado.exception.UserException;
import com.avocado.user.UserEntity;
import com.avocado.user.UserMapper;
import com.avocado.user.UserRepository;
import com.avocado.user.dto.resp.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    @Override
    public UserResponse authenticate(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ResultCode.USER_NOT_FOUND));
        return UserMapper.INSTANCE.toResponse(user);
    }
}
