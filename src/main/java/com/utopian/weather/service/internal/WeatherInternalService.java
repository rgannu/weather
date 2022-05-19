package com.utopian.weather.service.internal;

import com.utopian.weather.persistence.model.Weather;
import com.utopian.weather.persistence.model.WeatherInfoCsv;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface WeatherInternalService {

    Map<String, List<Weather>> getWeather(LocalDate startDate, LocalDate endDate);

    List<WeatherInfoCsv> getWeatherInfoCsvList(LocalDate startDate, LocalDate endDate);
}
