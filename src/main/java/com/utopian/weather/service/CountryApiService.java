package com.utopian.weather.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class CountryApiService {

/*    private final String countryApiBaseUrl;
    private final RestTemplate restTemplate;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public CountryApiService(String countryApiBaseUrl, RestTemplate restTemplate) {
        this.countryApiBaseUrl = countryApiBaseUrl;
        this.restTemplate = restTemplate;
    }

    public List<Country> getAllCountriesInfo() {
        try {
            UriComponentsBuilder builder = getUriComponentsBuilder(countryApiBaseUrl + "/all");
            String jsonResponse = restTemplate.getForObject(builder.build().toUri(), String.class);

            return processCountriesResponse(OBJECT_MAPPER.readTree(jsonResponse));
        } catch (Exception e) {
            log.error("Error in processing", e);
            throw new WeatherException("Error in processing", e);
        }
    }

    private List<Country> processCountriesResponse(JsonNode countriesJsonNode) {
        List<Country> countries = new ArrayList<>();
        for (JsonNode jsonNode : countriesJsonNode) {
            String name = jsonNode.get("name").get("common").asText();
            CountryBuilder countryBuilder = Country.builder()
                    .name(name)
                    .cca2(jsonNode.get("cca2").asText())
                    .cca3(jsonNode.get("cca3").asText());
            if (jsonNode.get("cioc") != null) {
                countryBuilder.cioc(jsonNode.get("cioc").asText());
            }
            countryBuilder.population(jsonNode.get("population").asLong());

            JsonNode jsonCapitalNodes = jsonNode.get("capital");
            List<String> capitalList = new ArrayList<>();
            if (jsonCapitalNodes != null && jsonCapitalNodes.isArray()) {
                jsonCapitalNodes.forEach(jsonCapitalNode -> capitalList.add(jsonCapitalNode.asText()));
            }
            countryBuilder.capital(capitalList);
            JsonNode flag = jsonNode.get("flag");
            if (flag != null) {
                countryBuilder.flag(flag.asText());
            }
            else {
                log.info("Flag isn't available for the country {}", name);
            }
            Set<Currency> currencies = new HashSet<>();
            JsonNode currencyJsonNode = jsonNode.get("currencies");
            if (currencyJsonNode != null && currencyJsonNode.isObject()) {
                Iterator<Entry<String, JsonNode>> currencyFields = currencyJsonNode.fields();
                currencyFields.forEachRemaining(field -> {
                    CurrencyBuilder currencyBuilder = Currency.builder()
                            .code(field.getKey())
                            .name(field.getValue().get("name").asText());
                    if (field.getValue().get("symbol") != null) {
                        currencyBuilder.symbol(field.getValue().get("symbol").asText());
                    }
                    currencies.add(currencyBuilder.build());
                });
            } else {
                log.info("Currencies are empty for the country {}", name);
            }

            countryBuilder.countryCurrencies(currencies);
            countries.add(countryBuilder.build());
        }
        return countries;
    }

    private UriComponentsBuilder getUriComponentsBuilder(String url) {
        return UriComponentsBuilder.fromHttpUrl(url);
    }

    public Country getCountryInfo(String countryCode) {
        try {
            UriComponentsBuilder builder = getUriComponentsBuilder(countryApiBaseUrl + "/alpha/" + countryCode);
            String jsonResponse = restTemplate.getForObject(builder.build().toUri(), String.class);

            return processCountriesResponse(
                    OBJECT_MAPPER.readTree(jsonResponse)).get(0);
        } catch (Exception e) {
            log.error("Error in processing", e);
            throw new WeatherException("Error in processing", e);
        }
    }*/
}
