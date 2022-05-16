package com.utopian.weather.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class WeatherApiService {
/*
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final String[] listOfCountries;
    private final CountryApiService countryApiService;
    private final FixerApiService fixerApiService;
    private final OpenWeatherMapApiService openWeatherMapApiService;

    public WeatherApiService(String[] listOfCountries, CountryApiService countryApiService,
            FixerApiService fixerApiService, OpenWeatherMapApiService openWeatherMapApiService) {
        this.listOfCountries = listOfCountries;
        this.countryApiService = countryApiService;
        this.fixerApiService = fixerApiService;
        this.openWeatherMapApiService = openWeatherMapApiService;
    }

    public Map<String, List<Weather>> getWeatherInfo(LocalDate startDate, LocalDate endDate) {
        return getWeatherInfo(Arrays.asList(this.listOfCountries), startDate, endDate);
    }

    public Map<String, List<Weather>> getWeatherInfo(final List<String> countryCodes,
            final LocalDate startDate, final LocalDate endDate) {
        // Map of country code, list of weather info for the period
        Map<String, List<Weather>> weatherInfoMap = new HashMap<>();
        countryCodes.forEach(countryCode -> weatherInfoMap.put(countryCode,
                processWeatherInfo(countryCode, startDate, endDate)));
        return weatherInfoMap;
    }

    public List<WeatherInfoCsv> getWeatherInfoCsvList(LocalDate startDate, LocalDate endDate) {
        return processWeatherInfoCsvList(startDate, endDate);
    }

    private List<Weather> processWeatherInfo(final String countryCode,
            final LocalDate startDate, final LocalDate endDate) {
        List<Weather> weathers = new ArrayList<>();

        Country country = this.countryApiService.getCountryInfo(countryCode);
        List<ExchangeRate> exchangeRateTimeSeries = this.fixerApiService.getExchangeRate(
                country.getCountryCurrencies().stream().findFirst().get().getCode(), startDate, endDate);
        CityWeather currentCityWeather = this.openWeatherMapApiService.getCurrentCityWeather(
                country.getCapital().get(0), country.getCca2());

        exchangeRateTimeSeries.forEach(exchangeRate ->
                weathers.add(Weather.builder()
                        .date(exchangeRate.getDate())
                        .country(country)
                        .cityWeather(currentCityWeather)
                        .exchangeRate(exchangeRate)
                        .build()));
        return weathers;
    }

    public List<WeatherInfoCsv> processWeatherInfoCsvList(final LocalDate startDate,
            final LocalDate endDate) {
        List<WeatherInfoCsv> weatherInfoCsvList = new ArrayList<>();

        Arrays.stream(this.listOfCountries)
                .forEach(countryCode -> {
                    Country country = this.countryApiService.getCountryInfo(countryCode);
                    List<ExchangeRate> exchangeRateTimeSeries = this.fixerApiService.getExchangeRate(
                            country.getCurrencies().stream().findFirst().get().getCode(), startDate, endDate);
                    String capital = country.getCapital().get(0);
                    String countryCodeCca2 = country.getCca2();
                    Geocode geocode = this.openWeatherMapApiService.getGeocode(capital, countryCodeCca2);

                    exchangeRateTimeSeries.forEach(exchangeRate ->
                    {
                        WeatherInfoCsvBuilder weatherInfoCsvBuilder = WeatherInfoCsv.builder()
                                .date(exchangeRate.getDate())
                                .name(country.getName())
                                .capital(country.getCapital())
                                .cca2(countryCodeCca2)
                                .cca3(country.getCca3())
                                .cioc(country.getCioc())
                                .population(country.getPopulation())
                                .countryCode(countryCode)
                                .currencyCode(country.getCurrencies().stream().findFirst().get().getCode())
                                .targetCurrency(exchangeRate.getTarget())
                                .exchangeRate(exchangeRate.getRate());

                        CityWeather cityWeather = this.openWeatherMapApiService.getHistoricalWeather(
                                geocode.getLat(), geocode.getLon(), exchangeRate.getDate());
                        weatherInfoCsvBuilder
                                .temp(cityWeather.getTemp())
                                .description(cityWeather.getDescription())
                                .windSpeed(cityWeather.getWindSpeed());

                        weatherInfoCsvList.add(weatherInfoCsvBuilder.build());
                    });
                });

        return weatherInfoCsvList;
    }*/
}
