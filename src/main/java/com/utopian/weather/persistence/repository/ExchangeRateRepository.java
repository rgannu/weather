package com.utopian.weather.persistence.repository;

import com.utopian.weather.persistence.model.ExchangeRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {


}
