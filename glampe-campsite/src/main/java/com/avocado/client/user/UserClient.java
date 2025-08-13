package com.avocado.client.user;

import com.avocado.campsite.dto.resp.CampSiteUserResponse;
import com.avocado.config.FeignConfig;
import com.avocado.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-service", path = "/api/v1/users", configuration = FeignConfig.class)
@Component
public interface UserClient {

    @GetMapping("/{id}")
    ApiResponse<CampSiteUserResponse> getUserById(@PathVariable("id") Long id);

    @PostMapping("/batch")
    ApiResponse<Map<Long, CampSiteUserResponse>> getUsersByIds(List<Long> ids);
}
