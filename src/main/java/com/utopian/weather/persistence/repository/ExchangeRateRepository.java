package com.utopian.weather.persistence.repository;

import com.utopian.weather.persistence.model.Currency;
import com.utopian.weather.persistence.model.ExchangeRate;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {


    @Query("select e from ExchangeRate e where e.date = :rateDate and e.base = :base and e.target = :target")
    Optional<ExchangeRate> queryAndFind(LocalDate rateDate, Currency base, Currency target);
}
