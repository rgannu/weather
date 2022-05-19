package com.utopian.weather.service;

import com.utopian.weather.mapper.ServiceMapper;
import com.utopian.weather.persistence.model.CityWeatherInfo;
import com.utopian.weather.service.internal.CityWeatherInternalService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional(propagation = Propagation.REQUIRED)
public class CityWeatherServiceImpl implements CityWeatherService {

    @Autowired
    private CityWeatherInternalService cityWeatherInternalService;
    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public CityWeatherInfo getCityWeather(LocalDate date, String city, String country) {
        return serviceMapper.map(cityWeatherInternalService.getCityWeather(date, city, country),
                CityWeatherInfo.class);
    }
}
