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
public class ExchangeRateInfo implements Serializable {

    private LocalDate rateDate;
    private CurrencyInfo baseCurrency;
    private CurrencyInfo targetCurrency;
    private Double rate;

}
