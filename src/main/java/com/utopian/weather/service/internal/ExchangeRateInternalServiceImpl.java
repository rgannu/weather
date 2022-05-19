package com.utopian.weather.service.internal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utopian.weather.exception.CurrencyNotFoundException;
import com.utopian.weather.exception.ExchangeRateException;
import com.utopian.weather.persistence.model.Currency;
import com.utopian.weather.persistence.model.CurrencyInfo;
import com.utopian.weather.persistence.model.ExchangeRate;
import com.utopian.weather.persistence.model.ExchangeRateInfo;
import com.utopian.weather.persistence.model.ExchangeRateInfo.ExchangeRateInfoBuilder;
import com.utopian.weather.persistence.repository.CurrencyRepository;
import com.utopian.weather.persistence.repository.ExchangeRateRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class ExchangeRateInternalServiceImpl implements ExchangeRateInternalService {

    public static final String EXCHANGERATE_TIMESERIES_URL = "/exchangerates_data/timeseries";

    private final String apiKey;
    private final String baseUrl;
    private final RestTemplate restTemplate;
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyInternalService currencyInternalService;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final CurrencyRepository currencyRepository;

    public ExchangeRateInternalServiceImpl(String apiKey, String baseUrl,
            RestTemplate restTemplate, CurrencyRepository currencyRepository,
            CurrencyInternalService currencyInternalService,
            ExchangeRateRepository exchangeRateRepository) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
        this.currencyRepository = currencyRepository;
        this.currencyInternalService = currencyInternalService;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public ExchangeRate getExchangeRate(LocalDate rateDate, String baseCurrencyCode,
            String targetCurrencyCode) {
        return getExchangeRateTimeseries(rateDate, rateDate, baseCurrencyCode, targetCurrencyCode)
                .stream()
                .map(Optional::ofNullable)
                .findFirst()
                .orElseGet(Optional::empty)
                .orElse(null);
    }

    @Override
    public List<ExchangeRate> getExchangeRateTimeseries(LocalDate startDate, LocalDate endDate,
            String baseCurrencyCode, String targetCurrencyCode) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                            this.baseUrl + EXCHANGERATE_TIMESERIES_URL)
                    .queryParam("amount", 1)
                    .queryParam("base", baseCurrencyCode)
                    .queryParam("symbols", targetCurrencyCode)
                    .queryParam("start_date", startDate)
                    .queryParam("end_date", endDate);
            final HttpHeaders headers = new HttpHeaders();
            headers.set("apikey", this.apiKey);

            final HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

            ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(),
                    HttpMethod.GET, requestEntity, String.class);
            String jsonResponse = response.getBody();

            List<ExchangeRateInfo> exchangeRateInfos = processExchangeRatesResponse(
                    OBJECT_MAPPER.readTree(jsonResponse));
            return saveExchangeRates(exchangeRateInfos);
        } catch (Exception e) {
            log.error("Error in processing", e);
            throw new ExchangeRateException(
                    String.format("Error in Exchange Rate retrieval %s", e.getMessage()));
        }
    }

    private List<ExchangeRate> saveExchangeRates(List<ExchangeRateInfo> exchangeRateInfos) {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        exchangeRateInfos.forEach(exchangeRateInfo -> {
            String baseCurrencyCode = exchangeRateInfo.getBaseCurrency().getCode();
            String targetCurrencyCode = exchangeRateInfo.getTargetCurrency().getCode();
            Currency baseCurrency = currencyRepository.findByCode(baseCurrencyCode)
                    .orElseThrow(() -> new CurrencyNotFoundException(baseCurrencyCode));
            Currency targetCurrency = currencyRepository.findByCode(targetCurrencyCode)
                    .orElseThrow(() -> new CurrencyNotFoundException(targetCurrencyCode));

            Optional<ExchangeRate> exchangeRateInDb = exchangeRateRepository.queryAndFind(
                    exchangeRateInfo.getRateDate(), baseCurrency, targetCurrency);

            if (exchangeRateInDb.isPresent()) {
                exchangeRates.add(exchangeRateInDb.get());
            } else {
                exchangeRates.add(exchangeRateRepository.save(ExchangeRate.builder()
                        .date(exchangeRateInfo.getRateDate())
                        .base(baseCurrency)
                        .target(targetCurrency)
                        .rate(exchangeRateInfo.getRate())
                        .build()));
            }
        });
        return exchangeRates;
    }

    private List<ExchangeRateInfo> processExchangeRatesResponse(JsonNode exchangeRatesJsonNode) {
        List<ExchangeRateInfo> exchangeRates = new ArrayList<>();
        String base = exchangeRatesJsonNode.get("base").asText();
        Iterator<Entry<String, JsonNode>> exchangeRateFields = exchangeRatesJsonNode.get("rates")
                .fields();
        exchangeRateFields.forEachRemaining(field -> {
            Currency baseCurrency = currencyInternalService.getCurrencyInfo(base);
            ExchangeRateInfoBuilder exchangeRateInfoBuilder = ExchangeRateInfo.builder()
                    .baseCurrency(CurrencyInfo.builder()
                            .code(baseCurrency.getCode())
                            .name(baseCurrency.getName())
                            .symbol(baseCurrency.getSymbol())
                            .build());
            exchangeRateInfoBuilder.rateDate(LocalDate.parse(field.getKey()));
            field.getValue().fields().forEachRemaining(targetCurrencyField -> {
                Currency targetCurrency = currencyInternalService.getCurrencyInfo(
                        targetCurrencyField.getKey());
                exchangeRateInfoBuilder.targetCurrency(CurrencyInfo.builder()
                        .code(targetCurrency.getCode())
                        .name(targetCurrency.getName())
                        .symbol(targetCurrency.getSymbol())
                        .build());
                exchangeRateInfoBuilder.rate(targetCurrencyField.getValue().doubleValue());
            });
            exchangeRates.add(exchangeRateInfoBuilder.build());
        });

        return exchangeRates;
    }

}
