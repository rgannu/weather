package com.utopian.weather.persistence.repository;

import com.utopian.weather.persistence.model.Currency;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {

    Optional<Currency> findByCode(String code);

    Optional<Currency> findByName(String name);

}
