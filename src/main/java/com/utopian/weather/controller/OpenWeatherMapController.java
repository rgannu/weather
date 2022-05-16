package com.utopian.weather.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weathermap")
public class OpenWeatherMapController {

/*
    private final OpenWeatherMapApiService openWeatherMapApiService;

    public OpenWeatherMapController(OpenWeatherMapApiService openWeatherMapApiService) {
        this.openWeatherMapApiService = openWeatherMapApiService;
    }

    @GetMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CityWeather getCurrentCityWeather(@RequestParam(name = "city") final String city,
            @RequestParam(name = "country") final String country) {
        return openWeatherMapApiService.getCurrentCityWeather(city.toUpperCase(), country.toUpperCase());
    }

    @GetMapping(value = "/geocode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Geocode getGeocode(@RequestParam(name = "city") final String city,
            @RequestParam(name = "country") final String country) {
        return openWeatherMapApiService.getGeocode(city.toUpperCase(), country.toUpperCase());
    }
*/

}
