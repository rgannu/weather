package com.utopian.weather.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

/*    private final WeatherApiService weatherService;

    public WeatherController(WeatherApiService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value = "/getweatherinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<Weather>> getWeatherInfo(
            @RequestParam(name = "start", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate start,
            @RequestParam(name = "end", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate end) {
        final LocalDate startDate = (start != null ? start : LocalDate.now().minusDays(4));
        final LocalDate endDate = (end != null ? end : LocalDate.now());

        // ToDo: Handle as proper ResponseEntity
        return weatherService.getWeatherInfo(startDate, endDate);
    }

    @GetMapping(value = "/getweatherinfo/csv")
    @ResponseStatus(HttpStatus.OK)
    public void exportCSV(HttpServletResponse response) throws Exception {
        // Intentionally start and end date aren't passed as arguments.
        // Reason being the openweatherapi doesn't give for free license historical timeseries older than the last 5 days
        final LocalDate startDate = LocalDate.now().minusDays(4);
        final LocalDate endDate = LocalDate.now();

        // set file name and content type
        String filename = "weatherinfo.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        // create a csv writer
        StatefulBeanToCsv<WeatherInfoCsv> writer = new StatefulBeanToCsvBuilder<WeatherInfoCsv>(
                response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false).build();

        writer.write(weatherService.getWeatherInfoCsvList(startDate, endDate));
    }*/

}
