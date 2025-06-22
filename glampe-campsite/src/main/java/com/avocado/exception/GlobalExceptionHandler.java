package com.avocado.exception;

import com.avocado.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CampSiteException.class)
    public ResponseEntity<?> handleRuntimeException(CampSiteException e) {
        return ResponseEntity
                .status(e.getResultCode().getHttpStatus())
                .body(
                        ApiResponse
                                .builder()
                                .message(e.getResultCode().getMessage())
                                .isSuccess(false)
                                .statusCode(e.getResultCode().getCode())
                                .build()

                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.builder()
                                .message(e.getMessage())
                                .isSuccess(false)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build()
                );
    }
}
