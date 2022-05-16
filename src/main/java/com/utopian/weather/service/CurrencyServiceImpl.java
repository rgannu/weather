package com.utopian.weather.service;

import com.utopian.weather.persistence.model.CurrencyInfo;
import com.utopian.weather.persistence.repository.CurrencyRepository;
import com.utopian.weather.service.internal.CurrencyInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyInternalService currencyInternalService;

    @Autowired
    public CurrencyServiceImpl(final CurrencyInternalService currencyInternalService) {
        this.currencyInternalService = currencyInternalService;
    }

    @Override
    public CurrencyInfo findByCode(String code) {
        return currencyInternalService.findByCode(code);
    }

    @Override
    public CurrencyInfo findByName(String name) {
        return currencyInternalService.findByName(name);
    }
}
