package com.utopian.weather.service;

import com.utopian.weather.persistence.model.CountryInfo;
import com.utopian.weather.service.internal.CountryInternalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CountryServiceImpl implements CountryService {

    private final CountryInternalService countryInternalService;

    @Autowired
    public CountryServiceImpl(final CountryInternalService countryInternalService) {
        this.countryInternalService = countryInternalService;
    }

    @Override
    public List<CountryInfo> getAllCountries() {
        return countryInternalService.getAllCountries();

    }

    @Override
    public CountryInfo getCountryInfo(String cca3) {
        return countryInternalService.getCountryInfo(cca3);

    }

    @Override
    public CountryInfo findByCca2(String cca2) {
        return null;
    }

    @Override
    public CountryInfo findByName(String name) {
        return null;
    }

    @Override
    public CountryInfo findByCca3(String cca3) {
        return null;
    }

}
