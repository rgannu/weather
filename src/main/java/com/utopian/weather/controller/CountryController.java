package com.utopian.weather.controller;

import com.utopian.weather.persistence.model.CountryInfo;
import com.utopian.weather.service.CountryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CountryInfo> getAllCountriesInfo() {
        // ToDo: Handle as proper ResponseEntity
        return countryService.getAllCountries();
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CountryInfo getCountryInfo(@PathVariable(name = "code") final String cca3CountryCode) {
        // ToDo: Handle as proper ResponseEntity
        return countryService.getCountryInfo(cca3CountryCode.toUpperCase());
    }

}
