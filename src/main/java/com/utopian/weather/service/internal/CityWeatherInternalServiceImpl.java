package com.utopian.weather.service.internal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utopian.weather.exception.CityWeatherException;
import com.utopian.weather.persistence.model.CityWeather;
import com.utopian.weather.persistence.model.CityWeatherInfo;
import com.utopian.weather.persistence.model.CityWeatherInfo.CityWeatherInfoBuilder;
import com.utopian.weather.persistence.model.Geocode;
import com.utopian.weather.persistence.repository.CityWeatherRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class CityWeatherInternalServiceImpl implements CityWeatherInternalService {

    private static final String WEATHER_URL = "/data/2.5/weather";
    private static final String HISTORICAL_URL = "/data/2.5/onecall/timemachine";

    private final RestTemplate restTemplate;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final String appId;
    private final String baseUrl;
    private final CityWeatherRepository cityWeatherRepository;
    private final GeocodeInternalService geocodeInternalService;

    public CityWeatherInternalServiceImpl(String appId, String baseUrl,
            RestTemplate restTemplate,
            GeocodeInternalService geocodeInternalService,
            CityWeatherRepository cityWeatherRepository) {
        this.appId = appId;
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
        this.geocodeInternalService = geocodeInternalService;
        this.cityWeatherRepository = cityWeatherRepository;
    }

    @Override
    public CityWeather getCityWeather(LocalDate date, String city, String country)
            throws CityWeatherException {
        try {
            Geocode geocode = geocodeInternalService.getGeocode(city, country);
            Optional<CityWeather> cityWeatherInDb = cityWeatherRepository.findByGeocodeAtDate(
                    date, geocode);
            if (cityWeatherInDb.isPresent()) {
                return cityWeatherInDb.get();
            }

            CityWeatherInfo cityWeatherInfo;
            if (date.equals(LocalDate.now())) {
                cityWeatherInfo = getCurrentCityWeather(city, country);
            } else {
                cityWeatherInfo = getHistoricalWeather(date, geocode);
            }

            // Save the city weather
            return cityWeatherRepository.save(CityWeather.builder()
                    .geocode(geocode)
                    .description(cityWeatherInfo.getDescription())
                    .measurementDate(cityWeatherInfo.getMeasurementDate())
                    .maxTemp(cityWeatherInfo.getMaxTemp())
                    .minTemp(cityWeatherInfo.getMinTemp())
                    .temp(cityWeatherInfo.getTemp())
                    .windSpeed(cityWeatherInfo.getWindSpeed())
                    .build());
        } catch (Exception e) {
            log.error("City weather processing exception", e);
            throw new CityWeatherException(
                    String.format("City weather processing exception. %s", e.getMessage()));
        }
    }

    private CityWeatherInfo getHistoricalWeather(LocalDate date, Geocode geocode) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                            this.baseUrl + HISTORICAL_URL)
                    .queryParam("lat", geocode.getLat())
                    .queryParam("lon", geocode.getLon())
                    .queryParam("dt", date.toEpochSecond(LocalTime.NOON, ZoneOffset.UTC))
                    .queryParam("units", "metric")
                    .queryParam("appid", this.appId);
            String jsonResponse = restTemplate.getForObject(builder.build().toUri(), String.class);
            return processHistoricalWeatherResponse(OBJECT_MAPPER.readTree(jsonResponse));
        } catch (Exception e) {
            log.error("Error in processing", e);
            throw new CityWeatherException(
                    String.format("Error in Exchange Rate retrieval %s", e.getMessage()));
        }
    }

    private CityWeatherInfo processHistoricalWeatherResponse(JsonNode historicalJsonNode) {
        CityWeatherInfoBuilder cityWeatherInfoBuilder = CityWeatherInfo.builder();
        JsonNode jsonCurrentNode = historicalJsonNode.get("current");
        if (jsonCurrentNode != null && jsonCurrentNode.isObject()) {
            LocalDate measurementDate =
                    Instant.ofEpochSecond(jsonCurrentNode.get("dt").asLong())
                            .atZone(ZoneId.systemDefault()).toLocalDate();

            cityWeatherInfoBuilder
                    .measurementDate(measurementDate)
                    .temp(jsonCurrentNode.get("temp").doubleValue())
                    .windSpeed(jsonCurrentNode.get("wind_speed").doubleValue());

            JsonNode jsonWeatherNode = jsonCurrentNode.get("weather");
            if (jsonWeatherNode != null && jsonWeatherNode.isArray()) {
                cityWeatherInfoBuilder.description(
                        jsonWeatherNode.get(0).get("description").asText());
            }
        }
        return cityWeatherInfoBuilder.build();
    }

    private CityWeatherInfo getCurrentCityWeather(String city, String country) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                            this.baseUrl + WEATHER_URL)
                    .queryParam("q", String.format("%s,%s", city, country))
                    .queryParam("units", "metric")
                    .queryParam("appid", this.appId);
            String jsonResponse = restTemplate.getForObject(builder.build().toUri(), String.class);
            return processCurrentCityWeatherResponse(OBJECT_MAPPER.readTree(jsonResponse));
        } catch (Exception e) {
            log.error("Error in processing", e);
            throw new CityWeatherException(
                    String.format("Error in Exchange Rate retrieval %s", e.getMessage()));
        }
    }

    private CityWeatherInfo processCurrentCityWeatherResponse(JsonNode weatherMapJsonNode) {
        CityWeatherInfoBuilder cityWeatherInfoBuilder = CityWeatherInfo.builder();
        if (weatherMapJsonNode != null && weatherMapJsonNode.isObject()) {
            cityWeatherInfoBuilder.measurementDate(LocalDate.now());
            JsonNode jsonWeatherNode = weatherMapJsonNode.get("weather");
            JsonNode jsonMainNode = weatherMapJsonNode.get("main");
            JsonNode jsonWindNode = weatherMapJsonNode.get("wind");
            if (jsonWeatherNode != null && jsonWeatherNode.isArray()) {
                cityWeatherInfoBuilder.description(
                        jsonWeatherNode.get(0).get("description").asText());
            }
            if (jsonMainNode != null) {
                cityWeatherInfoBuilder.temp(jsonMainNode.get("temp").doubleValue())
                        .minTemp(jsonMainNode.get("temp_min").doubleValue())
                        .maxTemp(jsonMainNode.get("temp_max").doubleValue());
            }
            if (jsonWindNode != null) {
                cityWeatherInfoBuilder.windSpeed(jsonWindNode.get("speed").doubleValue());
            }
        }
        return cityWeatherInfoBuilder.build();
    }

}
