package com.utopian.weather.persistence.model;

import static lombok.AccessLevel.PROTECTED;

import com.sun.istack.NotNull;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "country")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Country {
    @Id
    @GeneratedValue(generator = "pooled")
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    @Type(type = "com.utopian.weather.persistence.converter.ListToTextArrayUserType")
    @Column(columnDefinition = "text[]")
    private List<String> capital;

    @NotNull
    @Column(nullable = false)
    private String cca2;

    @NotNull
    @Column(nullable = false)
    private String cca3;

    private String cioc;

    @NotNull
    @Column(nullable = false)
    private Long population;

    private String flag;

    @OneToMany(mappedBy = "country")
    @Setter(AccessLevel.NONE)
    private Set<CountryCurrency> countryCurrencies;
}
