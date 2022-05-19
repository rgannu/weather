package com.utopian.weather.service;

import com.utopian.weather.mapper.ServiceMapper;
import com.utopian.weather.persistence.model.CityWeatherInfo;
import com.utopian.weather.persistence.model.CountryInfo;
import com.utopian.weather.persistence.model.CurrencyInfo;
import com.utopian.weather.persistence.model.ExchangeRateInfo;
import com.utopian.weather.persistence.model.Weather;
import com.utopian.weather.persistence.model.WeatherInfo;
import com.utopian.weather.persistence.model.WeatherInfoCsv;
import com.utopian.weather.service.internal.WeatherInternalService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional(propagation = Propagation.REQUIRED)
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherInternalService weatherInternalService;
    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public Map<String, List<WeatherInfo>> getWeatherInfo(LocalDate startDate, LocalDate endDate) {
        Map<String, List<Weather>> weatherInfo = weatherInternalService.getWeather(startDate,
                endDate);
        Map<String, List<WeatherInfo>> weatherInfoListMap = new HashMap<>();
        weatherInfo.forEach((key, value) -> {
            List<WeatherInfo> weatherInfoList = new ArrayList<>();
            value.forEach(
                    weather -> {
                        System.out.println("Exchange Rate: " + weather.getExchangeRate());
                        System.out.println("Exchange RateInfo: " + serviceMapper.map(weather.getExchangeRate(), ExchangeRateInfo.class));
                        weatherInfoList.add(WeatherInfo.builder()
                                .date(weather.getDate())
                                .exchangeRate(ExchangeRateInfo.builder()
                                        .rateDate(weather.getExchangeRate().getDate())
                                        .baseCurrency(serviceMapper.map(weather.getExchangeRate().getBase(),
                                                CurrencyInfo.class))
                                        .targetCurrency(serviceMapper.map(weather.getExchangeRate().getTarget(),
                                                CurrencyInfo.class))
                                        .rate(weather.getExchangeRate().getRate())
                                        .build())
                                .country(serviceMapper.map(weather.getCountry(), CountryInfo.class))
                                .cityWeather(serviceMapper.map(weather.getCityWeather(), CityWeatherInfo.class))
                                .build());
                    });
            weatherInfoListMap.put(key, weatherInfoList);
        });
        return weatherInfoListMap;
    }

    @Override
    public List<WeatherInfoCsv> getWeatherInfoCsvList(LocalDate startDate, LocalDate endDate) {
        return weatherInternalService.getWeatherInfoCsvList(startDate, endDate);
    }

}
