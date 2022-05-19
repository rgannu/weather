package com.utopian.weather.persistence.model;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.NONE)
@ToString
public class WeatherInfo implements Serializable {

    private LocalDate date;
    private CityWeatherInfo cityWeather;
    private CountryInfo country;
    private ExchangeRateInfo exchangeRate;

}
