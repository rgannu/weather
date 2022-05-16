package com.utopian.weather.persistence.repository;

import com.utopian.weather.persistence.model.Geocode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeocodeRepository extends CrudRepository<Geocode, Long> {


}
