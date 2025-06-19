package com.avocado.user;

import com.avocado.exception.ResultCode;
import com.avocado.exception.UserException;
import com.avocado.user.cache.Cache;
import com.avocado.user.cache.RedisPrefix;
import com.avocado.user.dto.resp.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
}
