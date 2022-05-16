package com.utopian.weather.persistence.model;

import static lombok.AccessLevel.PROTECTED;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "city_weather")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class CityWeather {
    @Id
    @GeneratedValue(generator = "pooled")
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    private Double temp;

    private Double minTemp;
    private Double maxTemp;

    @NotNull
    @Column(nullable = false)
    private Double windSpeed;
}
