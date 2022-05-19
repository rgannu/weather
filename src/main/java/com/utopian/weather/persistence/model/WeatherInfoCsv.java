package com.utopian.weather.persistence.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class WeatherInfoCsv implements Serializable {

    @CsvBindByName
    @CsvBindByPosition(position = 0)
    private String countryCode;

    @CsvBindByName
    @CsvBindByPosition(position = 1)
    private LocalDate date;

    @CsvBindByName
    @CsvBindByPosition(position = 2)
    private String name;

    @CsvBindByName
    @CsvBindByPosition(position = 3)
    private List<String> capital;

    @CsvBindByName
    @CsvBindByPosition(position = 4)
    private String cca2;

    @CsvBindByName
    @CsvBindByPosition(position = 5)
    private String cca3;

    @CsvBindByName
    @CsvBindByPosition(position = 6)
    private String cioc;

    @CsvBindByName
    @CsvBindByPosition(position = 7)
    private Long population;

    @CsvBindByName
    @CsvBindByPosition(position = 8)
    private String currencyCode;

    @CsvBindByName
    @CsvBindByPosition(position = 9)
    private String targetCurrency;

    @CsvBindByName
    @CsvBindByPosition(position = 10)
    private Double exchangeRate;

    @CsvBindByName
    @CsvBindByPosition(position = 11)
    private Double temp;

    @CsvBindByName
    @CsvBindByPosition(position = 12)
    private String description;

    @CsvBindByName
    @CsvBindByPosition(position = 13)
    private Double windSpeed;

}
