package com.utopian.weather.persistence.model;


import com.sun.istack.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "country_currency")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, exclude = {"country", "currency"})
@ToString()
public class CountryCurrency {

    @Id
    @GeneratedValue(generator = "pooled")
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private Country country;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CURRENCY_ID", nullable = false)
    private Currency currency;
}
