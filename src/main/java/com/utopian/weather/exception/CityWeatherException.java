package com.utopian.weather.exception;

public class CityWeatherException extends CodeException {

    public CityWeatherException() {
        super("City weather processing exception");
    }

    public CityWeatherException(String message) {
        super(message);
    }
}
