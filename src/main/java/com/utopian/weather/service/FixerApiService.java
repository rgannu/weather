package com.utopian.weather.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class FixerApiService {

/*    public static final String EXCHANGERATE_TIMESERIES_URL = "/exchangerates_data/timeseries";
    private static final String EUR = "EUR";
    private final String apiKey;
    private final String baseUrl;
    private final RestTemplate restTemplate;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public FixerApiService(String apiKey, String baseUrl, RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    public List<ExchangeRate> getExchangeRate(String baseCurrency, LocalDate startDate,
            LocalDate endDate) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                            this.baseUrl + EXCHANGERATE_TIMESERIES_URL)
                    .queryParam("amount", 1)
                    .queryParam("base", baseCurrency)
                    .queryParam("symbols", EUR)
                    .queryParam("start_date", startDate)
                    .queryParam("end_date", endDate);
            final HttpHeaders headers = new HttpHeaders();
            headers.set("apikey", this.apiKey);

            final HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

            ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(),
                    HttpMethod.GET, requestEntity, String.class);
            String jsonResponse = response.getBody();

            return processExchangeRatesResponse(OBJECT_MAPPER.readTree(jsonResponse));
        } catch (Exception e) {
            log.error("Error in processing", e);
            throw new WeatherException("Error in processing", e);
        }
    }

    private List<ExchangeRate> processExchangeRatesResponse(JsonNode exchangeRatesJsonNode) {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        String base = exchangeRatesJsonNode.get("base").asText();
        Iterator<Entry<String, JsonNode>> exchangeRateFields = exchangeRatesJsonNode.get("rates").fields();
        exchangeRateFields.forEachRemaining(field -> {
            ExchangeRateBuilder exchangeRateBuilder = ExchangeRate.builder()
                    .base(base);
            exchangeRateBuilder.date(LocalDate.parse(field.getKey()));
            field.getValue().fields().forEachRemaining(targetCurrencyField -> {
                exchangeRateBuilder.target(targetCurrencyField.getKey());
                exchangeRateBuilder.rate(targetCurrencyField.getValue().floatValue());
            });
            exchangeRates.add(exchangeRateBuilder.build());
        });

        return exchangeRates;
    }*/
}