package com.utopian.weather.persistence.model;

import static lombok.AccessLevel.PROTECTED;

import com.sun.istack.NotNull;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Currency {
    @Id
    @GeneratedValue(generator = "pooled")
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String code;

    @NotNull
    @Column(nullable = false)
    private String name;

    private String symbol;

    @OneToMany(mappedBy = "country")
    @Setter(AccessLevel.NONE)
    private Set<CountryCurrency> countryCurrencies;
}
