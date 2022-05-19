package com.utopian.weather.service.internal;

import com.utopian.weather.persistence.model.Country;
import java.util.List;

public interface CountryInternalService {

    List<Country> getAllCountries();

    Country getCountry(String cca3);
}
