package com.utopian.weather.service.internal;

import com.utopian.weather.persistence.model.CurrencyInfo;

public interface CurrencyInternalService {

    CurrencyInfo findByCode(String code);

    CurrencyInfo findByName(String name);
}
