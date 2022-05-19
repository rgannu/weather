package com.utopian.weather.service.internal;

import com.utopian.weather.persistence.model.ExchangeRate;
import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateInternalService {

    ExchangeRate getExchangeRate(LocalDate rateDate, String baseCurrencyCode,
            String targetCurrencyCode);

    List<ExchangeRate> getExchangeRateTimeseries(LocalDate startDate, LocalDate endDate,
            String baseCurrencyCode, String targetCurrencyCode);
}
