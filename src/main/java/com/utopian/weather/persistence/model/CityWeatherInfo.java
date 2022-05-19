package com.utopian.weather.persistence.model;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.NONE)
@ToString
public class CityWeatherInfo implements Serializable {

    private LocalDate measurementDate;
    private String description;
    private Double temp;
    private Double minTemp;
    private Double maxTemp;
    private Double windSpeed;
    private GeocodeInfo geocode;

}
