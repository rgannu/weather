package com.utopian.weather.service;

import com.utopian.weather.persistence.model.CountryInfo;
import java.util.List;

public interface CountryService {

    CountryInfo findByCca2(String cca2);

    CountryInfo findByName(String name);

    CountryInfo findByCca3(String cca3);

    List<CountryInfo> getAllCountries();

    CountryInfo getCountryInfo(String cca3);
}
