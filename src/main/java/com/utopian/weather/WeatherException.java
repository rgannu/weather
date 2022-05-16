package com.utopian.weather;

public class WeatherException extends RuntimeException {

    public WeatherException(String message, Throwable cause) {
        super(message, cause);
    }
}
