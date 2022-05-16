package com.utopian.weather.service;

import com.utopian.weather.persistence.model.CurrencyInfo;

public interface CurrencyService {
    CurrencyInfo findByCode(String code);

    CurrencyInfo findByName(String name);
}
