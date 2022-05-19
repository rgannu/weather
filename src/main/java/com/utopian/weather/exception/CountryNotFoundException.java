package com.utopian.weather.exception;

public class CountryNotFoundException extends NotFoundException {

    public CountryNotFoundException() {
        super("Country not found");
    }

    public CountryNotFoundException(String countryCode) {
        super("No country found with the country code " + countryCode);
    }
}
