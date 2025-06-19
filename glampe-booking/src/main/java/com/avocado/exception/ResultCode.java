package com.avocado.exception;

import org.springframework.http.HttpStatus;

public enum ResultCode {
    ERROR(500, "Error", HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * CampSite
     */
    BOOKING_NOT_FOUND(20001,"Booking not found", HttpStatus.NOT_FOUND);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ResultCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }



    public int getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getHttpStatus() { return httpStatus; }
}
