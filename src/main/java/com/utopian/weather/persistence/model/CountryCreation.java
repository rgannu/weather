package com.utopian.weather.persistence.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
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
public class CountryCreation implements Serializable {

    private String name;
    private List<String> capital;
    private String cca2;
    private String cca3;
    private String cioc;
    private Long population;
    private String flag;

    private Set<Currency> currencies;
}
