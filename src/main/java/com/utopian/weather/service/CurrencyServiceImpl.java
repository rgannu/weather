package com.utopian.weather.service;

import com.utopian.weather.mapper.ServiceMapper;
import com.utopian.weather.persistence.model.CurrencyInfo;
import com.utopian.weather.service.internal.CurrencyInternalService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyInternalService currencyInternalService;
    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public CurrencyInfo findByCode(String code) {
        return serviceMapper.map(currencyInternalService.findByCode(code), CurrencyInfo.class);
    }

    @Override
    public CurrencyInfo findByName(String name) {
        return serviceMapper.map(currencyInternalService.findByName(name), CurrencyInfo.class);
    }

    @Override
    public List<CurrencyInfo> getAllCurrencies() {
        return currencyInternalService.getAllCurrencies().stream()
                .map(e -> serviceMapper.map(e, CurrencyInfo.class))
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyInfo getCurrencyInfo(String code) {
        return serviceMapper.map(currencyInternalService.getCurrencyInfo(code), CurrencyInfo.class);
    }
}
