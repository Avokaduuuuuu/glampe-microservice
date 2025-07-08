package com.avocado.utils;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private String message;
    private Integer statusCode;
    @Builder.Default
    private Boolean isSuccess = true;
    private T data;
}
