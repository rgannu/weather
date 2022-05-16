package com.utopian.weather.persistence.repository;

import com.utopian.weather.persistence.model.CityWeather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityWeatherRepository extends CrudRepository<CityWeather, Long> {


}
