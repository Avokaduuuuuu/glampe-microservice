package com.avocado.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CampSiteException extends RuntimeException {
    private String message;
    private ResultCode resultCode;
    public CampSiteException(ResultCode resultCode, String message) {
        this.message = message;
        this.resultCode = resultCode;
    }

    public CampSiteException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public CampSiteException(String message) {
        this.message = message;
        this.resultCode = ResultCode.ERROR;
    }
}
