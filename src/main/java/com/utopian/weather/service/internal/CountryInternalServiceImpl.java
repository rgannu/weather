package com.utopian.weather.service.internal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utopian.weather.exception.CountryNotFoundException;
import com.utopian.weather.persistence.model.Country;
import com.utopian.weather.persistence.model.CountryCreation;
import com.utopian.weather.persistence.model.CountryCreation.CountryCreationBuilder;
import com.utopian.weather.persistence.model.CountryCurrency;
import com.utopian.weather.persistence.model.Currency;
import com.utopian.weather.persistence.model.Currency.CurrencyBuilder;
import com.utopian.weather.persistence.repository.CountryCurrencyRepository;
import com.utopian.weather.persistence.repository.CountryRepository;
import com.utopian.weather.persistence.repository.CurrencyRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CountryInternalServiceImpl implements CountryInternalService {

    private final String countryApiBaseUrl;
    private final RestTemplate restTemplate;

    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;
    private final CountryCurrencyRepository countryCurrencyRepository;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public CountryInternalServiceImpl(String countryApiBaseUrl, RestTemplate restTemplate,
            CountryRepository countryRepository, CurrencyRepository currencyRepository,
            CountryCurrencyRepository countryCurrencyRepository) {
        this.countryApiBaseUrl = countryApiBaseUrl;
        this.restTemplate = restTemplate;
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.countryCurrencyRepository = countryCurrencyRepository;
        this.loadAllCountries();
    }

    @Override
    public List<Country> getAllCountries() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Country getCountry(String cca3) {
        return countryRepository.findByCca3(cca3)
                .orElseThrow(() -> new CountryNotFoundException(cca3));
    }

    private void loadAllCountries() {
        try {
            UriComponentsBuilder builder = getUriComponentsBuilder(countryApiBaseUrl + "/all");
            String jsonResponse = restTemplate.getForObject(builder.build().toUri(), String.class);
            List<CountryCreation> countries = processCountriesResponse(
                    OBJECT_MAPPER.readTree(jsonResponse));
            for (CountryCreation country : countries) {
                getExisting(country.getName())
                        .ifPresentOrElse(existing -> updateCountry(existing, country)
                                , () -> createNew(country));
            }

        } catch (Exception e) {
            log.error("Unable to load country data during startup", e);
        }
    }

    private void updateCountry(Country existing, CountryCreation newData) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setDeepCopyEnabled(true);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setPropertyCondition(context -> context.getSource() != null);
        Country newCountryData = Country.builder()
                .name(newData.getName())
                .cca2(newData.getCca2())
                .cca3(newData.getCca3())
                .population(newData.getPopulation())
                .capital(newData.getCapital())
                .cioc(newData.getCioc())
                .flag(newData.getFlag())
                .build();
        modelMapper.map(newCountryData, existing);

        countryRepository.save(existing);
    }

    private void createNew(CountryCreation countryCreation) {
        Country savedCountry = countryRepository.save(Country.builder()
                .name(countryCreation.getName())
                .flag(countryCreation.getFlag())
                .capital(countryCreation.getCapital())
                .cca2(countryCreation.getCca2())
                .cca3(countryCreation.getCca3())
                .cioc(countryCreation.getCioc())
                .population(countryCreation.getPopulation())
                .build());
        countryCreation.getCurrencies()
                .forEach(currency -> {
                    Currency savedCurrency = addCurrency(currency);
                    countryCurrencyRepository.save(CountryCurrency.builder()
                            .country(savedCountry)
                            .currency(savedCurrency)
                            .build());
                });
    }

    private Currency addCurrency(Currency currency) {
        Optional<Currency> currencyInDb = currencyRepository.findByCode(currency.getCode());
        return currencyInDb.orElseGet(() -> currencyRepository.save(Currency.builder()
                .code(currency.getCode())
                .name(currency.getName())
                .symbol(currency.getSymbol())
                .build()));
    }

    private Optional<Country> getExisting(String countryName) {
        return countryRepository.findByName(countryName);
    }

    private List<CountryCreation> processCountriesResponse(JsonNode countriesJsonNode) {
        List<CountryCreation> countries = new ArrayList<>();
        for (JsonNode jsonNode : countriesJsonNode) {
            String name = jsonNode.get("name").get("common").asText();
            CountryCreationBuilder
                    countryBuilder = CountryCreation.builder()
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
                jsonCapitalNodes.forEach(
                        jsonCapitalNode -> capitalList.add(jsonCapitalNode.asText()));
            }
            countryBuilder.capital(capitalList);
            JsonNode flag = jsonNode.get("flag");
            if (flag != null) {
                countryBuilder.flag(flag.asText());
            } else {
                log.info("Flag isn't available for the country {}", name);
            }

            Set<Currency> countryCurrencies = new HashSet<>();
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
                    countryCurrencies.add(currencyBuilder.build());
                });
            } else {
                log.info("Currencies are empty for the country {}", name);
            }

            countryBuilder.currencies(countryCurrencies);
            countries.add(countryBuilder.build());
        }
        return countries;
    }

    private UriComponentsBuilder getUriComponentsBuilder(String url) {
        return UriComponentsBuilder.fromHttpUrl(url);
    }
}
