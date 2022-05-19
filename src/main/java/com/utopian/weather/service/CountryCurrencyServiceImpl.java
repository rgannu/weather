package com.utopian.weather.service;

import com.utopian.weather.persistence.model.Country;
import com.utopian.weather.persistence.model.CountryCurrency;
import com.utopian.weather.persistence.model.Currency;
import com.utopian.weather.persistence.repository.CountryCurrencyRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CountryCurrencyServiceImpl implements CountryCurrencyService {

    private final CountryCurrencyRepository countryCurrencyRepository;

    @Autowired
    public CountryCurrencyServiceImpl(final CountryCurrencyRepository countryCurrencyRepository) {
        this.countryCurrencyRepository = countryCurrencyRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public CountryCurrency create(Country country, Currency currency) {
        Optional<CountryCurrency> countryCurrency = countryCurrencyRepository.findByCountryAndCurrencyId(
                country.getId(), currency.getId()).findAny();
        return countryCurrency.orElseGet(
                () -> countryCurrencyRepository.save(CountryCurrency.builder()
                        .country(country)
                        .currency(currency)
                        .build()));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public CountryCurrency update(CountryCurrency countryCurrency) {
        return countryCurrencyRepository.save(countryCurrency);
    }
}

