package com.utopian.weather.exception;

public class CurrencyNotFoundException extends NotFoundException {

    public CurrencyNotFoundException() {
        super("Currency not found");
    }

    public CurrencyNotFoundException(String code) {
        super("Currency not found with the code " + code);
    }
}
