package com.utopian.weather.service;

import com.utopian.weather.persistence.model.CityWeatherInfo;
import java.time.LocalDate;

public interface CityWeatherService {

    CityWeatherInfo getCityWeather(LocalDate date, String city, String country);
}
