package com.utopian.weather.service.internal;

import com.utopian.weather.exception.CountryCurrencyNotFoundException;
import com.utopian.weather.exception.CountryNotFoundException;
import com.utopian.weather.exception.CurrencyNotFoundException;
import com.utopian.weather.persistence.model.CityWeather;
import com.utopian.weather.persistence.model.Country;
import com.utopian.weather.persistence.model.CountryCurrency;
import com.utopian.weather.persistence.model.Currency;
import com.utopian.weather.persistence.model.ExchangeRate;
import com.utopian.weather.persistence.model.Weather;
import com.utopian.weather.persistence.model.WeatherInfoCsv;
import com.utopian.weather.persistence.repository.CountryRepository;
import com.utopian.weather.persistence.repository.CurrencyRepository;
import com.utopian.weather.persistence.repository.WeatherRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class WeatherInternalServiceImpl implements WeatherInternalService {

    private final String[] listOfCountries;
    private final String targetCurrencyCode;
    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;
    private final ExchangeRateInternalService exchangeRateInternalService;
    private final CityWeatherInternalService cityWeatherInternalService;
    private final WeatherRepository weatherRepository;

    public WeatherInternalServiceImpl(String[] listOfCountries,
            String targetCurrencyCode,
            WeatherRepository weatherRepository,
            CountryRepository countryRepository,
            CurrencyRepository currencyRepository,
            ExchangeRateInternalService exchangeRateInternalService,
            CityWeatherInternalService cityWeatherInternalService) {
        this.listOfCountries = listOfCountries;
        this.targetCurrencyCode = targetCurrencyCode;
        this.weatherRepository = weatherRepository;
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.exchangeRateInternalService = exchangeRateInternalService;
        this.cityWeatherInternalService = cityWeatherInternalService;
    }

    @Override
    public Map<String, List<Weather>> getWeather(LocalDate startDate, LocalDate endDate) {
        return getWeather(Arrays.asList(this.listOfCountries), startDate, endDate);
    }

    @Override
    public List<WeatherInfoCsv> getWeatherInfoCsvList(LocalDate startDate, LocalDate endDate) {
        List<WeatherInfoCsv> weatherInfoCsvList = new ArrayList<>();

        getWeather(Arrays.asList(this.listOfCountries), startDate, endDate)
                .forEach((countryCode, weatherList) ->
                        weatherList.forEach(weather -> {
                            CountryCurrency countryCurrency = weather.getCountry()
                                    .getCountryCurrencies().stream()
                                    .map(Optional::ofNullable)
                                    .findFirst()
                                    .orElseGet(Optional::empty)
                                    .orElse(null);

                            weatherInfoCsvList.add(WeatherInfoCsv.builder()
                                    .countryCode(countryCode)
                                    .date(weather.getDate())
                                    .name(weather.getCountry().getName())
                                    .capital(weather.getCountry().getCapital())
                                    .cca2(weather.getCountry().getCca2())
                                    .cca3(weather.getCountry().getCca3())
                                    .cioc(weather.getCountry().getCioc())
                                    .population(weather.getCountry().getPopulation())
                                    .currencyCode(
                                            countryCurrency != null ? countryCurrency.getCurrency()
                                                    .getCode() : null)
                                    .targetCurrency(weather.getExchangeRate().getTarget().getCode())
                                    .exchangeRate(weather.getExchangeRate().getRate())
                                    .temp(weather.getCityWeather().getTemp())
                                    .description(weather.getCityWeather().getDescription())
                                    .windSpeed(weather.getCityWeather().getWindSpeed())
                                    .build());
                        }));

        return weatherInfoCsvList;
    }

    public Map<String, List<Weather>> getWeather(final List<String> countryCodes,
            final LocalDate startDate, final LocalDate endDate) {
        // Map of country code, list of weather info for the period
        Map<String, List<Weather>> weatherMap = new HashMap<>();
        countryCodes.forEach(countryCode -> weatherMap.put(countryCode,
                processWeather(countryCode, startDate, endDate)));
        return weatherMap;
    }

    private List<Weather> processWeather(final String countryCodeCca3,
            final LocalDate startDate, final LocalDate endDate) {
        List<Weather> weathers = new ArrayList<>();

        // Country should be there pre-populated in the DB
        Country country = this.countryRepository.findByCca3(countryCodeCca3)
                .orElseThrow(() -> new CountryNotFoundException(countryCodeCca3));
        CountryCurrency countryCurrency = country.getCountryCurrencies().stream()
                .findFirst()
                .orElseThrow(() -> new CountryCurrencyNotFoundException(countryCodeCca3));
        Currency targetCurrency = this.currencyRepository.findByCode(targetCurrencyCode)
                .orElseThrow(() -> new CurrencyNotFoundException(targetCurrencyCode));

        startDate.datesUntil(endDate)
                .forEach(date -> {
                    ExchangeRate exchangeRate = this.exchangeRateInternalService.getExchangeRate(
                            date, countryCurrency.getCurrency().getCode(),
                            targetCurrency.getCode());
                    CityWeather cityWeather = cityWeatherInternalService.getCityWeather(date,
                            country.getCapital().get(0), country.getCca2());

                    Optional<Weather> weatherInDb = this.weatherRepository.findBy(date, country,
                            exchangeRate, cityWeather);
                    if (weatherInDb.isPresent()) {
                        weathers.add(weatherInDb.get());
                    } else {
                        // save the weather
                        weathers.add(this.weatherRepository.save(Weather.builder()
                                .date(date)
                                .country(country)
                                .exchangeRate(exchangeRate)
                                .cityWeather(cityWeather)
                                .build()));
                    }
                });
        return weathers;
    }
}
