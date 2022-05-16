package com.utopian.weather.persistence.model;

import static lombok.AccessLevel.PROTECTED;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Weather {
    @Id
    @GeneratedValue(generator = "pooled")
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull
    @Column(name="weather_date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    @OneToOne
    @JoinColumn(name = "EXCHANGE_RATE_ID")
    private ExchangeRate exchangeRate;

    // OpenWeatherMap API gives only the current weather (not historical)
    // So, same entry will be duplicated for every date
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CITY_WEATHER_ID")
    private CityWeather cityWeather;
}
