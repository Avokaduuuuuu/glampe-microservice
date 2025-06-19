package com.avocado.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookingException extends RuntimeException {
    private String message;
    private ResultCode resultCode;
    public BookingException(ResultCode resultCode, String message) {
        this.message = message;
        this.resultCode = resultCode;
    }

    public BookingException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public BookingException(String message) {
        this.message = message;
        this.resultCode = ResultCode.ERROR;
    }
}
