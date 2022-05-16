package com.utopian.weather.exception;


import lombok.Getter;

public abstract class CodeException extends RuntimeException {

    @Getter
    private final String code;

    protected CodeException(String code) {
        this.code = code;
    }

    protected CodeException(String code, String message) {
        super(message);
        this.code = code;
    }


}
