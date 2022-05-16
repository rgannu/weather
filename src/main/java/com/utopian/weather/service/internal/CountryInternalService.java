package com.utopian.weather.service.internal;

import com.utopian.weather.persistence.model.CountryInfo;
import java.util.List;

public interface CountryInternalService {

    List<CountryInfo> getAllCountries();

    CountryInfo getCountryInfo(String cca3);
}
