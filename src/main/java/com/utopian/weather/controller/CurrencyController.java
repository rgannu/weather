package com.utopian.weather.controller;

import com.utopian.weather.persistence.model.CurrencyInfo;
import com.utopian.weather.service.CurrencyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CurrencyInfo> getAllCurrenciesInfo() {
        // ToDo: Handle as proper ResponseEntity
        return currencyService.getAllCurrencies();
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CurrencyInfo getCurrencyInfo(@PathVariable(name = "code") final String code) {
        // ToDo: Handle as proper ResponseEntity
        return currencyService.getCurrencyInfo(code.toUpperCase());
    }

/*
    private final FixerApiService fixerApiService;

    public CurrencyController(FixerApiService fixerApiService) {
        this.fixerApiService = fixerApiService;
    }

    @GetMapping(value = "/exchangerate/{base}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ExchangeRate> getExchangeRate(@PathVariable(name = "base") final String baseCurrency,
            @RequestParam(name = "start", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate start,
            @RequestParam(name = "end", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate end) {

        // ToDo: Handle as proper ResponseEntity
        final LocalDate startDate = (start != null ? start : LocalDate.now().minusDays(4));
        final LocalDate endDate = (end != null ? end : LocalDate.now());

        return fixerApiService.getExchangeRate(baseCurrency.toUpperCase(), startDate, endDate);
    }
*/

}
