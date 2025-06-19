package com.avocado.exception;

import com.avocado.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookingException.class)
    public ResponseEntity<?> handleRuntimeException(BookingException e) {
        return ResponseEntity
                .status(e.getResultCode().getHttpStatus())
                .body(
                        ApiResponse
                                .builder()
                                .message(e.getResultCode().getMessage())
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
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build()
                );
    }
}
