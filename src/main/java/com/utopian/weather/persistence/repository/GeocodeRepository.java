package com.utopian.weather.persistence.repository;

import com.utopian.weather.persistence.model.Geocode;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeocodeRepository extends CrudRepository<Geocode, Long> {

    @Query("select g from Geocode g where ilike (g.city, :city) = true and ilike (g.country, :country) = true")
    Optional<Geocode> findByCityAndCountry(String city, String country);

}
