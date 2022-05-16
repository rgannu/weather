package com.utopian.weather.exception;

public abstract class NotFoundException extends CodeException {

    protected NotFoundException(String message) {
        super("NOT_FOUND", message);
    }

    protected NotFoundException(String code, String message) {
        super(code, message);
    }
}
