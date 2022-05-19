package com.utopian.weather.configuration;

import com.utopian.weather.persistence.repository.CityWeatherRepository;
import com.utopian.weather.persistence.repository.CountryCurrencyRepository;
import com.utopian.weather.persistence.repository.CountryRepository;
import com.utopian.weather.persistence.repository.CurrencyRepository;
import com.utopian.weather.persistence.repository.ExchangeRateRepository;
import com.utopian.weather.persistence.repository.GeocodeRepository;
import com.utopian.weather.persistence.repository.WeatherRepository;
import com.utopian.weather.service.internal.CityWeatherInternalService;
import com.utopian.weather.service.internal.CityWeatherInternalServiceImpl;
import com.utopian.weather.service.internal.CountryInternalService;
import com.utopian.weather.service.internal.CountryInternalServiceImpl;
import com.utopian.weather.service.internal.CurrencyInternalService;
import com.utopian.weather.service.internal.CurrencyInternalServiceImpl;
import com.utopian.weather.service.internal.ExchangeRateInternalService;
import com.utopian.weather.service.internal.ExchangeRateInternalServiceImpl;
import com.utopian.weather.service.internal.GeocodeInternalService;
import com.utopian.weather.service.internal.GeocodeInternalServiceImpl;
import com.utopian.weather.service.internal.WeatherInternalService;
import com.utopian.weather.service.internal.WeatherInternalServiceImpl;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan
public class WeatherConfiguration {

    @Value("${weather.listOfCountries}")
    private String[] listOfCountries;

    @Value("${weather.targetCurrency}")
    private String targetCurrency;

    @Value("${weather.country.api.base-url}")
    private String countryApiBaseUrl;

    @Value("${weather.openweathermap.api.app-id}")
    private String openWeatherMapAppId;

    @Value("${weather.openweathermap.api.base-url}")
    private String openWeatherMapBaseUrl;

    @Value("${weather.fixer.api.app-key}")
    private String fixerApiApiKey;

    @Value("${weather.fixer.api.base-url}")
    private String fixerApiBaseUrl;

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CountryCurrencyRepository countryCurrencyRepository;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private GeocodeRepository geocodeRepository;
    @Autowired
    private CityWeatherRepository cityWeatherRepository;
    @Autowired
    private WeatherRepository weatherRepository;

    @Bean
    public CountryInternalService getCountryInternalService(
            RestTemplateBuilder restTemplateBuilder) {
        Duration timeout = Duration.ofSeconds(60);
        restTemplateBuilder.setReadTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();
        return new CountryInternalServiceImpl(countryApiBaseUrl, restTemplateBuilder.build(),
                countryRepository, currencyRepository, countryCurrencyRepository);
    }

    @Bean
    public CurrencyInternalService getCurrencyInternalService() {
        return new CurrencyInternalServiceImpl(currencyRepository);
    }

    @Bean
    public ExchangeRateInternalService getExchangeRateInternalService(
            RestTemplateBuilder restTemplateBuilder) {
        Duration timeout = Duration.ofSeconds(60);
        restTemplateBuilder.setReadTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();
        return new ExchangeRateInternalServiceImpl(fixerApiApiKey, fixerApiBaseUrl,
                restTemplateBuilder.build(),
                currencyRepository, getCurrencyInternalService(),
                exchangeRateRepository);
    }

    @Bean
    public GeocodeInternalService getGeocodeInternalService(
            RestTemplateBuilder restTemplateBuilder) {
        Duration timeout = Duration.ofSeconds(60);
        restTemplateBuilder.setReadTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();
        return new GeocodeInternalServiceImpl(openWeatherMapAppId, openWeatherMapBaseUrl,
                restTemplateBuilder.build(),
                geocodeRepository);
    }

    @Bean
    public CityWeatherInternalService getCityWeatherInternalService(
            RestTemplateBuilder restTemplateBuilder) {
        Duration timeout = Duration.ofSeconds(60);
        restTemplateBuilder.setReadTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();
        return new CityWeatherInternalServiceImpl(openWeatherMapAppId, openWeatherMapBaseUrl,
                restTemplateBuilder.build(),
                getGeocodeInternalService(restTemplateBuilder),
                cityWeatherRepository);
    }

    @Bean
    public WeatherInternalService getWeatherInternalService(
            RestTemplateBuilder restTemplateBuilder) {
        Duration timeout = Duration.ofSeconds(60);
        restTemplateBuilder.setReadTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();

        return new WeatherInternalServiceImpl(listOfCountries,
                targetCurrency, weatherRepository,
                countryRepository, currencyRepository,
                getExchangeRateInternalService(restTemplateBuilder),
                getCityWeatherInternalService(restTemplateBuilder));
    }

}
