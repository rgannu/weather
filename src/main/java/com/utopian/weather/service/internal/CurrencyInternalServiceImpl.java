package com.utopian.weather.service.internal;

import com.utopian.weather.exception.CurrencyNotFoundException;
import com.utopian.weather.persistence.model.Currency;
import com.utopian.weather.persistence.repository.CurrencyRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CurrencyInternalServiceImpl implements CurrencyInternalService {

    private final CurrencyRepository currencyRepository;

    public CurrencyInternalServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new CurrencyNotFoundException(code));
    }

    @Override
    public Currency findByName(String name) {
        return currencyRepository.findByName(name)
                .orElseThrow(() -> new CurrencyNotFoundException(name));
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return StreamSupport.stream(currencyRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Currency getCurrencyInfo(String code) {
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new CurrencyNotFoundException(code));
    }

}
