package com.utopian.weather.service.internal;

import com.utopian.weather.persistence.model.Geocode;

public interface GeocodeInternalService {

    Geocode getGeocode(String city, String country);

}
