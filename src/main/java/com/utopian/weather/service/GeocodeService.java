package com.utopian.weather.service;

import com.utopian.weather.persistence.model.GeocodeInfo;

public interface GeocodeService {

    GeocodeInfo getGeocode(String city, String country);

}
