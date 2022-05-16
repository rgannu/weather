package com.utopian.weather.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class OpenWeatherMapApiService {

/*    private static final String WEATHER_URL = "/data/2.5/weather";
    private static final String GEOCODE_URL = "/geo/1.0/direct";
    private static final String HISTORICAL_URL = "/data/2.5/onecall/timemachine";
    private final String appId;
    private final String baseUrl;
    private final RestTemplate restTemplate;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public OpenWeatherMapApiService(String appId, String baseUrl, RestTemplate restTemplate) {
        this.appId = appId;
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    public CityWeather getCurrentCityWeather(String city, String country) {
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
            throw new WeatherException("Error in processing", e);
        }
    }

    public CityWeather getHistoricalWeather(Double lat, Double lon, LocalDate date) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                            this.baseUrl + HISTORICAL_URL)
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("dt", date.toEpochSecond(LocalTime.NOON, ZoneOffset.UTC))
                    .queryParam("units", "metric")
                    .queryParam("appid", this.appId);
            String jsonResponse = restTemplate.getForObject(builder.build().toUri(), String.class);
            return processHistoricalWeatherResponse(OBJECT_MAPPER.readTree(jsonResponse));
        } catch (Exception e) {
            log.error("Error in processing", e);
            throw new WeatherException("Error in processing", e);
        }
    }

    private CityWeather processHistoricalWeatherResponse(JsonNode historicalJsonNode) {
        JsonNode jsonCurrentNode = historicalJsonNode.get("current");
        CityWeatherBuilder cityWeatherBuilder = CityWeather.builder();
        if (jsonCurrentNode != null && jsonCurrentNode.isObject()) {
            cityWeatherBuilder
                    .temp(jsonCurrentNode.get("temp").floatValue())
                    .windSpeed(jsonCurrentNode.get("wind_speed").floatValue());

            JsonNode jsonWeatherNode = jsonCurrentNode.get("weather");
            if (jsonWeatherNode != null && jsonWeatherNode.isArray()) {
                cityWeatherBuilder.description(jsonWeatherNode.get(0).get("description").asText());
            }
        }
        return cityWeatherBuilder.build();
    }

    public Geocode getGeocode(String city, String country) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                            this.baseUrl + GEOCODE_URL)
                    .queryParam("q", String.format("%s,%s", city, country))
                    .queryParam("limit", 1)
                    .queryParam("appid", this.appId);
            String jsonResponse = restTemplate.getForObject(builder.build().toUri(), String.class);
            return processGeocodeResponse(OBJECT_MAPPER.readTree(jsonResponse));
        } catch (Exception e) {
            log.error("Error in processing", e);
            throw new WeatherException("Error in processing", e);
        }
    }

    private Geocode processGeocodeResponse(JsonNode geocodeJsonNode) {
        GeocodeBuilder builder = Geocode.builder();
        if (geocodeJsonNode != null && geocodeJsonNode.isArray()) {
            return builder
                    .city(geocodeJsonNode.get(0).get("name").asText())
                    .country(geocodeJsonNode.get(0).get("country").asText())
                    .lat(geocodeJsonNode.get(0).get("lat").asDouble())
                    .lon(geocodeJsonNode.get(0).get("lon").asDouble())
                    .build();
        }
        return builder.build();
    }

    private CityWeather processCurrentCityWeatherResponse(JsonNode weatherMapJsonNode) {
        CityWeatherBuilder cityWeatherBuilder = CityWeather.builder();
        if (weatherMapJsonNode != null && weatherMapJsonNode.isObject()) {
            cityWeatherBuilder.name(weatherMapJsonNode.get("name").asText());
            JsonNode jsonWeatherNode = weatherMapJsonNode.get("weather");
            JsonNode jsonMainNode = weatherMapJsonNode.get("main");
            JsonNode jsonWindNode = weatherMapJsonNode.get("wind");
            if (jsonWeatherNode != null && jsonWeatherNode.isArray()) {
                cityWeatherBuilder.description(jsonWeatherNode.get(0).get("description").asText());
            }
            if (jsonMainNode != null) {
                cityWeatherBuilder.temp(jsonMainNode.get("temp").floatValue())
                        .minTemp(jsonMainNode.get("temp_min").floatValue())
                        .maxTemp(jsonMainNode.get("temp_max").floatValue());
            }
            if (jsonWindNode != null) {
                cityWeatherBuilder.windSpeed(jsonWindNode.get("speed").floatValue());
            }
        }
        return cityWeatherBuilder.build();
    }*/
}
