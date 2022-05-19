package com.utopian.weather.service;

import com.utopian.weather.mapper.ServiceMapper;
import com.utopian.weather.persistence.model.GeocodeInfo;
import com.utopian.weather.service.internal.GeocodeInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional(propagation = Propagation.REQUIRED)
public class GeocodeServiceImpl implements GeocodeService {

    @Autowired
    private GeocodeInternalService geocodeInternalService;

    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public GeocodeInfo getGeocode(String city, String country) {
        return serviceMapper.map(geocodeInternalService.getGeocode(city, country),
                GeocodeInfo.class);
    }
}
