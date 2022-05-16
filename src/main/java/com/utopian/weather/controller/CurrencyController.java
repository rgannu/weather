package com.utopian.weather.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

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
