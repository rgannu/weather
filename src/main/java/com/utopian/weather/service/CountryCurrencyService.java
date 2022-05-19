package com.utopian.weather.service;

import com.utopian.weather.persistence.model.Country;
import com.utopian.weather.persistence.model.CountryCurrency;
import com.utopian.weather.persistence.model.Currency;

public interface CountryCurrencyService {

    CountryCurrency create(Country country, Currency currency);

    CountryCurrency update(CountryCurrency countryCurrency);
}
