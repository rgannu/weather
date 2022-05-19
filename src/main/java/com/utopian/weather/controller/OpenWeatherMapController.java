package com.utopian.weather.controller;

import com.utopian.weather.persistence.model.CityWeatherInfo;
import com.utopian.weather.persistence.model.GeocodeInfo;
import com.utopian.weather.service.CityWeatherService;
import com.utopian.weather.service.GeocodeService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weathermap")
public class OpenWeatherMapController {

    @Autowired
    private GeocodeService geocodeService;

    @Autowired
    private CityWeatherService cityWeatherService;

    public OpenWeatherMapController(GeocodeService geocodeService,
            CityWeatherService cityWeatherService) {
        this.geocodeService = geocodeService;
        this.cityWeatherService = cityWeatherService;
    }

    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CityWeatherInfo getCurrentCityWeather(@RequestParam(name = "city") final String city,
            @RequestParam(name = "country") final String country) {
        return getCityWeather(LocalDate.now(), city, country);
    }

    @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CityWeatherInfo getCityWeather(
            @RequestParam(name = "date")
            @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate date,
            @RequestParam(name = "city") final String city,
            @RequestParam(name = "country") final String country) {
        return cityWeatherService.getCityWeather(date, city.toUpperCase(), country.toUpperCase());
    }

    @GetMapping(value = "/geocode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GeocodeInfo getGeocode(@RequestParam(name = "city") final String city,
            @RequestParam(name = "country") final String country) {
        return geocodeService.getGeocode(city.toUpperCase(), country.toUpperCase());
    }

}
