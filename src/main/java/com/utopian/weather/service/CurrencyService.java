package com.utopian.weather.service;

import com.utopian.weather.persistence.model.CurrencyInfo;
import java.util.List;

public interface CurrencyService {

    CurrencyInfo findByCode(String code);

    CurrencyInfo findByName(String name);

    List<CurrencyInfo> getAllCurrencies();

    CurrencyInfo getCurrencyInfo(String code);
}
