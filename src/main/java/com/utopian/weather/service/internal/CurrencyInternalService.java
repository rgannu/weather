package com.utopian.weather.service.internal;

import com.utopian.weather.persistence.model.Currency;
import java.util.List;

public interface CurrencyInternalService {

    Currency findByCode(String code);

    Currency findByName(String name);

    List<Currency> getAllCurrencies();

    Currency getCurrencyInfo(String code);
}
