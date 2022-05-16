package com.utopian.weather.persistence.repository;

import com.utopian.weather.persistence.model.Weather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Long> {


}
