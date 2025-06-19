package com.avocado.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ServiceErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 404 -> new CampSiteException(ResultCode.USER_NOT_FOUND);
            default -> new CampSiteException(ResultCode.ERROR);
        };
    }
}
