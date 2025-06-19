package com.avocado.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserException extends RuntimeException {
    private String message;
    private ResultCode resultCode;

    public UserException(ResultCode resultCode, String message) {
        this.message = message;
        this.resultCode = resultCode;
    }

    public UserException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public UserException(String message) {
        this.message = message;
        this.resultCode = ResultCode.ERROR;
    }
}
