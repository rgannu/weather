package com.utopian.weather.persistence.repository;

import com.utopian.weather.persistence.model.CityWeather;
import com.utopian.weather.persistence.model.Country;
import com.utopian.weather.persistence.model.ExchangeRate;
import com.utopian.weather.persistence.model.Weather;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Long> {

    @Query("select w from Weather w where w.date = :date and w.country = :country and w.exchangeRate = :exchangeRate and w.cityWeather = :cityWeather")
    Optional<Weather> findBy(LocalDate date, Country country, ExchangeRate exchangeRate,
            CityWeather cityWeather);

}
