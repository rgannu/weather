package com.utopian.weather.service.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableSet;
import com.utopian.weather.persistence.model.Country;
import com.utopian.weather.persistence.repository.CountryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CountryInternalServiceImplTest {

    @InjectMocks
    private CountryInternalServiceImpl countryInternalService;

    @Mock
    private CountryRepository countryRepository;

    @Test
    void testCountryInternalService() {
        Country belgium = Country.builder().name("Belgium")
                .cca2("BE").cca3("BEL").capital(List.of("Brussels")).population(111L)
                .build();
        Country india = Country.builder().name("India")
                .cca2("IN").cca3("IND").capital(List.of("New Delhi")).population(222L)
                .build();
        ImmutableSet<Country> countries = ImmutableSet.of(
                belgium,
                india);

        when(countryRepository.findAll()).thenReturn(List.of(countries).iterator().next());
        when(countryRepository.findByCca3(belgium.getCca3())).thenReturn(Optional.of(belgium));
        when(countryRepository.findByCca3(india.getCca3())).thenReturn(Optional.of(india));

        assertThat(countryInternalService.getAllCountries())
                .containsOnly(belgium, india);
        assertThat(countryInternalService.getCountry(belgium.getCca3()))
                .isEqualTo(belgium);
        assertThat(countryInternalService.getCountry(india.getCca3()))
                .isEqualTo(india);
    }

}