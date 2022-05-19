package com.utopian.weather.service.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utopian.weather.exception.GeocodeException;
import com.utopian.weather.persistence.model.Geocode;
import com.utopian.weather.persistence.model.GeocodeInfo;
import com.utopian.weather.persistence.model.GeocodeInfo.GeocodeInfoBuilder;
import com.utopian.weather.persistence.repository.GeocodeRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class GeocodeInternalServiceImpl implements GeocodeInternalService {

    private static final String GEOCODE_URL = "/geo/1.0/direct";

    private final RestTemplate restTemplate;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final String appId;
    private final String baseUrl;
    private final GeocodeRepository geocodeRepository;

    public GeocodeInternalServiceImpl(String appId, String baseUrl,
            RestTemplate restTemplate,
            GeocodeRepository geocodeRepository) {
        this.appId = appId;
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
        this.geocodeRepository = geocodeRepository;
    }

    @Override
    public Geocode getGeocode(String city, String country) {
        try {
            Optional<Geocode> geocodeInDb = geocodeRepository.findByCityAndCountry(city, country);
            // .stream().findFirst();

            if (geocodeInDb.isPresent()) {
                return geocodeInDb.get();
            }

            GeocodeInfo geocodeInfo = getGeocodeInfo(city, country);

            // Save the geocodeInfo
            return geocodeRepository.save(Geocode.builder()
                    .country(geocodeInfo.getCountry())
                    .city(geocodeInfo.getCity())
                    .lat(geocodeInfo.getLat())
                    .lon(geocodeInfo.getLon())
                    .build());
        } catch (Exception e) {
            log.error("Error in processing", e);
            throw new GeocodeException(
                    String.format("Error in Exchange Rate retrieval %s", e.getMessage()));
        }
    }

    private GeocodeInfo getGeocodeInfo(String city, String country) throws JsonProcessingException {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(
                        this.baseUrl + GEOCODE_URL)
                .queryParam("q", String.format("%s,%s", city, country))
                .queryParam("limit", 1)
                .queryParam("appid", this.appId);
        String jsonResponse = restTemplate.getForObject(uriComponentsBuilder.build().toUri(),
                String.class);
        JsonNode geocodeJsonNode = OBJECT_MAPPER.readTree(jsonResponse);

        GeocodeInfoBuilder builder = GeocodeInfo.builder();
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
}
