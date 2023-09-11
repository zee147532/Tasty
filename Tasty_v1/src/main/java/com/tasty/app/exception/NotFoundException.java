package com.tasty.app.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public NotFoundException(String message) {
        super(message);
        this.code = HttpStatus.NOT_FOUND.value();
    }

}
