package com.utopian.weather.service.internal;

import com.utopian.weather.exception.CurrencyNotFoundException;
import com.utopian.weather.mapper.ServiceMapper;
import com.utopian.weather.persistence.model.Currency;
import com.utopian.weather.persistence.model.CurrencyInfo;
import com.utopian.weather.persistence.repository.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CurrencyInternalServiceImpl implements CurrencyInternalService {


    private final CurrencyRepository currencyRepository;
    private final ServiceMapper serviceMapper;

    public CurrencyInternalServiceImpl(CurrencyRepository currencyRepository,
            ServiceMapper serviceMapper) {
        this.currencyRepository = currencyRepository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public CurrencyInfo findByCode(String code) {
        Currency currency = currencyRepository.findByCode(code)
                .orElseThrow(() -> new CurrencyNotFoundException(code));

        return serviceMapper.map(currency, CurrencyInfo.class);
    }

    @Override
    public CurrencyInfo findByName(String name) {
        Currency currency = currencyRepository.findByName(name)
                .orElseThrow(() -> new CurrencyNotFoundException(name));

        return serviceMapper.map(currency, CurrencyInfo.class);
    }

}
