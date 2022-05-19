package com.utopian.weather.controller;

import com.utopian.weather.persistence.model.ExchangeRateInfo;
import com.utopian.weather.service.ExchangeRateService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchangerate")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping(value = "/rate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ExchangeRateInfo getExchangeRate(
            @RequestParam(name = "ratedate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate ratedate,
            @RequestParam(name = "base") final String baseCurrencyCode,
            @RequestParam(name = "target") final String targetCurrencyCode) {
        // ToDo: Handle as proper ResponseEntity
        final LocalDate date = (ratedate != null ? ratedate : LocalDate.now());

        return exchangeRateService.getExchangeRate(date, baseCurrencyCode.toUpperCase(),
                targetCurrencyCode.toUpperCase());
    }

    @GetMapping(value = "/timeseries", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ExchangeRateInfo> getExchangeRateTimeseries(
            @RequestParam(name = "start", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate start,
            @RequestParam(name = "end", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate end,
            @RequestParam(name = "base") final String baseCurrencyCode,
            @RequestParam(name = "target") final String targetCurrencyCode) {
        // ToDo: Handle as proper ResponseEntity
        final LocalDate startDate = (start != null ? start : LocalDate.now().minusDays(4));
        final LocalDate endDate = (end != null ? end : LocalDate.now());

        return exchangeRateService.getExchangeRateTimeseries(startDate, endDate,
                baseCurrencyCode.toUpperCase(), targetCurrencyCode.toUpperCase());
    }

}
