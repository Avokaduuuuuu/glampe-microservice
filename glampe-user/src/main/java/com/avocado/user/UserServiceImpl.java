package com.avocado.user;

import com.avocado.exception.ResultCode;
import com.avocado.exception.UserException;
import com.avocado.cache.Cache;
import com.avocado.cache.RedisPrefix;
import com.avocado.user.dto.req.UserAddRequest;
import com.avocado.user.dto.req.UserUpdateRequest;
import com.avocado.user.dto.req.UserVerifyRequest;
import com.avocado.user.dto.resp.AuthUserResponse;
import com.avocado.user.dto.resp.UserResponse;
import com.avocado.user.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final Cache<UserResponse> userCache;

    @Override
    public UserResponse getUserById(Long id) {
        UserResponse userResponse = userCache.get(RedisPrefix.USER.getPrefix() + id);
        if (userResponse != null) return userResponse;
        userResponse = UserMapper.INSTANCE.toResponse(
                userRepository.findById(id)
                        .orElseThrow(() -> new UserException(ResultCode.USER_NOT_FOUND))
        );
        userCache.put(RedisPrefix.USER.getPrefix() + id, userResponse);
        return userResponse;
    }

    @Override
    public Map<Long, UserResponse> getUsersByIds(List<Long> ids) {
        List<UserEntity> userEntities = userRepository.findAllByIdIn(ids);
        return userEntities.stream()
                .collect(Collectors.toMap(UserEntity::getId, UserMapper.INSTANCE::toResponse));
    }

    @Override
    public AuthUserResponse verifyUser(UserVerifyRequest request) {
        Optional<UserEntity> user = userRepository.findByEmail(request.email());
        AuthUserResponse authUserResponse = new AuthUserResponse();
        if (user.isEmpty()) {
            authUserResponse.setUser(null);
            authUserResponse.setIsNew(true);
        }else {
            UserEntity userEntity = user.get();
            userEntity.setLastLoginDate(LocalDate.now());
            userRepository.save(userEntity);
            authUserResponse.setUser(UserMapper.INSTANCE.toResponse(userEntity));
        }
        return authUserResponse;
    }

    @Override
    public AuthUserResponse addNewUser(UserAddRequest request) {
        Optional<UserEntity> user = userRepository.findByEmail(request.email());
        if (user.isPresent()) {
            throw  new UserException(ResultCode.USER_EXISTS);
        }
        UserResponse userResponse = UserMapper.INSTANCE.toResponse(userRepository.save(
                UserEntity.builder()
                        .email(request.email())
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .address(request.address())
                        .phoneNumber(request.phoneNumber())
                        .dob(request.dob())
                        .role(UserRoleEnum.ROLE_USER)
                        .build()
        ));
        return AuthUserResponse.builder()
                .user(userResponse)
                .build();
    }

    @Override
    public AuthUserResponse updateUser(UserUpdateRequest request, Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserException(ResultCode.USER_NOT_FOUND));

        if (request.firstName() != null) user.setFirstName(request.firstName());
        if (request.lastName() != null) user.setLastName(request.lastName());
        if (request.address() != null) user.setAddress(request.address());
        if (request.phoneNumber() != null) user.setPhoneNumber(request.phoneNumber());
        if (request.dob() != null) user.setDob(request.dob());
        return AuthUserResponse.builder()
                .user(UserMapper.INSTANCE.toResponse(user))
                .isNew(false)
                .build();
    }
}
