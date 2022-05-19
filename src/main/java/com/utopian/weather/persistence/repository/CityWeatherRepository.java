package com.utopian.weather.persistence.repository;

import com.utopian.weather.persistence.model.CityWeather;
import com.utopian.weather.persistence.model.Geocode;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityWeatherRepository extends CrudRepository<CityWeather, Long> {

    @Query("select cw from CityWeather cw where cw.measurementDate = :measurementDate and cw.geocode = :geocode")
    Optional<CityWeather> findByGeocodeAtDate(LocalDate measurementDate, Geocode geocode);
}
