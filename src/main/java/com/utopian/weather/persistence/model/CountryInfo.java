package com.utopian.weather.persistence.model;

import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.NONE)
public class CountryInfo implements Serializable {

    private String name;
    private List<String> capital;
    private String cca2;
    private String cca3;
    private String cioc;
    private Long population;
    private String flag;

}
