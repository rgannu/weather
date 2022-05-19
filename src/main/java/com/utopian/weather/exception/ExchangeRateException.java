package com.utopian.weather.exception;

public class ExchangeRateException extends CodeException {

    public ExchangeRateException() {
        super("Exchange Rate processing exception");
    }

    public ExchangeRateException(String message) {
        super(message);
    }
}
