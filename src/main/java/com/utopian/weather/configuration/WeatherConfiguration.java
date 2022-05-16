package com.utopian.weather.configuration;

import com.utopian.weather.mapper.ServiceMapper;
import com.utopian.weather.persistence.repository.CountryCurrencyRepository;
import com.utopian.weather.persistence.repository.CountryRepository;
import com.utopian.weather.persistence.repository.CurrencyRepository;
import com.utopian.weather.service.internal.CountryInternalService;
import com.utopian.weather.service.internal.CountryInternalServiceImpl;
import com.utopian.weather.service.internal.CurrencyInternalService;
import com.utopian.weather.service.internal.CurrencyInternalServiceImpl;
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
    private ServiceMapper serviceMapper;

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CountryCurrencyRepository countryCurrencyRepository;

    @Bean
    public CountryInternalService getCountryInternalService(RestTemplateBuilder restTemplateBuilder) {
        Duration timeout = Duration.ofSeconds(60);
        restTemplateBuilder.setReadTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();
        return new CountryInternalServiceImpl(countryApiBaseUrl, restTemplateBuilder.build(),
                countryRepository, currencyRepository, countryCurrencyRepository, serviceMapper);
    }

    @Bean
    public CurrencyInternalService getCurrencyInternalService(RestTemplateBuilder restTemplateBuilder) {
        Duration timeout = Duration.ofSeconds(60);
        restTemplateBuilder.setReadTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();
        return new CurrencyInternalServiceImpl(currencyRepository, serviceMapper);
    }

/*
    @Bean
    public FixerApiService getFixerApiService(RestTemplateBuilder restTemplateBuilder) {
        Duration timeout = Duration.ofSeconds(60);
        restTemplateBuilder.setReadTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();
        return new FixerApiService(fixerApiApiKey, fixerApiBaseUrl, restTemplateBuilder.build());
    }

    @Bean
    public OpenWeatherMapApiService getOpenWeatherMapService(RestTemplateBuilder restTemplateBuilder) {
        Duration timeout = Duration.ofSeconds(60);
        restTemplateBuilder.setReadTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();
        return new OpenWeatherMapApiService(openWeatherMapAppId, openWeatherMapBaseUrl, restTemplateBuilder.build());
    }

    @Bean
    public WeatherApiService getWeatherApiService(RestTemplateBuilder restTemplateBuilder) {
        Duration timeout = Duration.ofSeconds(60);
        restTemplateBuilder.setReadTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();
        return new WeatherApiService(listOfCountries, getCountryService(restTemplateBuilder),
                getFixerApiService(restTemplateBuilder),
                getOpenWeatherMapService(restTemplateBuilder));
    }
*/


}
