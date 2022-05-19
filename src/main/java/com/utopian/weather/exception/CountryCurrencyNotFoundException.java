package com.utopian.weather.exception;

public class CountryCurrencyNotFoundException extends NotFoundException {

    public CountryCurrencyNotFoundException() {
        super("Country Currency not found");
    }

    public CountryCurrencyNotFoundException(String code) {
        super("Country Currency not found with the code " + code);
    }
}
