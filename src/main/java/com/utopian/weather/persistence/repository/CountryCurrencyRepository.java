package com.utopian.weather.persistence.repository;

import com.utopian.weather.persistence.model.CountryCurrency;
import java.util.stream.Stream;
import org.springframework.data.repository.CrudRepository;

public interface CountryCurrencyRepository extends CrudRepository<CountryCurrency, Long> {

    Stream<CountryCurrency> findByCountryAndCurrencyId(Long countryId, Long currencyId);
}
