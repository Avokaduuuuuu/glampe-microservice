package com.avocado.client.user;

import com.avocado.client.user.dto.BookingUserResponse;
import com.avocado.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8081", path = "/api/v1/users")
@Component
public interface UserClient {

    @GetMapping("/{id}")
    ApiResponse<BookingUserResponse> getUserById(@PathVariable("id") Long id);
}
