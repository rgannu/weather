package com.utopian.weather.service.internal;

import com.utopian.weather.exception.CityWeatherException;
import com.utopian.weather.persistence.model.CityWeather;
import java.time.LocalDate;

public interface CityWeatherInternalService {

    CityWeather getCityWeather(LocalDate date, String city, String country)
            throws CityWeatherException;
}
