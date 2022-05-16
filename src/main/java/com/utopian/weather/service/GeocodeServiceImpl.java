package com.utopian.weather.service;

import com.utopian.weather.persistence.repository.GeocodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class GeocodeServiceImpl implements GeocodeService {

    @Autowired
    private GeocodeRepository geocodeRepository;

}