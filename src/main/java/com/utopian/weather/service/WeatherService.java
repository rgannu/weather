package com.utopian.weather.service;

import com.utopian.weather.persistence.model.WeatherInfo;
import com.utopian.weather.persistence.model.WeatherInfoCsv;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface WeatherService {

    Map<String, List<WeatherInfo>> getWeatherInfo(LocalDate startDate, LocalDate endDate);

    List<WeatherInfoCsv> getWeatherInfoCsvList(LocalDate startDate, LocalDate endDate);
}
