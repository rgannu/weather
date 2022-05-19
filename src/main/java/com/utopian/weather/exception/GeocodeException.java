package com.utopian.weather.exception;

public class GeocodeException extends CodeException {

    public GeocodeException() {
        super("Geocode processing exception");
    }

    public GeocodeException(String message) {
        super(message);
    }
}
