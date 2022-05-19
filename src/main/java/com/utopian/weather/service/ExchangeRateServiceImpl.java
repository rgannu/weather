package com.utopian.weather.service;

import com.utopian.weather.mapper.ServiceMapper;
import com.utopian.weather.persistence.model.ExchangeRateInfo;
import com.utopian.weather.service.internal.ExchangeRateInternalService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional(propagation = Propagation.REQUIRED)
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private ExchangeRateInternalService exchangeRateInternalService;
    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public ExchangeRateInfo getExchangeRate(LocalDate date, String baseCurrencyCode,
            String targetCurrencyCode) {
        return serviceMapper.map(exchangeRateInternalService.getExchangeRate(date, baseCurrencyCode,
                targetCurrencyCode), ExchangeRateInfo.class);
    }

    @Override
    public List<ExchangeRateInfo> getExchangeRateTimeseries(LocalDate startDate, LocalDate endDate,
            String baseCurrencyCode, String targetCurrencyCode) {
        return exchangeRateInternalService.getExchangeRateTimeseries(startDate, endDate,
                        baseCurrencyCode, targetCurrencyCode).stream()
                .map(exchangeRate -> serviceMapper.map(exchangeRate, ExchangeRateInfo.class))
                .collect(Collectors.toList());
    }
}
