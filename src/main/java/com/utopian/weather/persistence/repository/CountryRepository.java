package com.utopian.weather.persistence.repository;

import com.utopian.weather.persistence.model.Country;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

    Optional<Country> findByCca2(String cca2);

    Optional<Country> findByName(String name);

    Optional<Country> findByCca3(String cca3);

}
