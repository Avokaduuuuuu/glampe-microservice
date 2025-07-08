package com.avocado.exception;

import org.springframework.http.HttpStatus;

public enum ResultCode {
    ERROR(500, "Error", HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * User
     */
    USER_NOT_FOUND(1000, "User not found", HttpStatus.NOT_FOUND),

    /**
     * CampSite
     */
    CAMPSITE_NOT_FOUND(2000, "Camp site not found", HttpStatus.NOT_FOUND),

    /**
     * Selection
     */
    SELECTION_NOT_FOUND(3000, "Selection not found", HttpStatus.NOT_FOUND),

    /**
     * Place Type
     */
    PLACE_TYPE_NOT_FOUND(5000, "Place type not found", HttpStatus.NOT_FOUND),

    /**
     * Utility
     */
    UTILITY_NOT_FOUND(4000, "Utility not found", HttpStatus.NOT_FOUND),

    /**
     * Facility
     */
    FACILITY_NOT_FOUND(6000, "Facility not found", HttpStatus.NOT_FOUND),

    /**
     * Gallery
     */
    INVALID_FILE(7000, "Invalid file", HttpStatus.BAD_REQUEST),


    /**
     * Camptype
     */
    CAMPTYPE_NOT_FOUND(8000, "Camp type not found", HttpStatus.NOT_FOUND);




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
