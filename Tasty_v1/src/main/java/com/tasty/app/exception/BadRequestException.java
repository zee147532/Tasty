package com.tasty.app.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public BadRequestException(String message) {
        super(message);
        this.code = HttpStatus.BAD_REQUEST.value();
    }

    public BadRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
