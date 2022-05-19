package com.utopian.weather.service;

import com.utopian.weather.persistence.model.ExchangeRateInfo;
import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateService {

    ExchangeRateInfo getExchangeRate(LocalDate date, String toUpperCase, String toUpperCase1);

    List<ExchangeRateInfo> getExchangeRateTimeseries(LocalDate startDate, LocalDate endDate,
            String toUpperCase, String toUpperCase1);
}
