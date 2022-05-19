package com.utopian.weather.controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.utopian.weather.persistence.model.WeatherInfo;
import com.utopian.weather.persistence.model.WeatherInfoCsv;
import com.utopian.weather.service.WeatherService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    public WeatherController(final WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value = "/getweatherinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<WeatherInfo>> getWeatherInfo(
            @RequestParam(name = "start", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate start,
            @RequestParam(name = "end", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate end) {
        final LocalDate startDate = (start != null ? start : LocalDate.now().minusDays(4));
        final LocalDate endDate = (end != null ? end : LocalDate.now().plusDays(1));

        // ToDo: Handle as proper ResponseEntity
        return weatherService.getWeatherInfo(startDate, endDate);
    }

    @GetMapping(value = "/getweatherinfo/csv")
    @ResponseStatus(HttpStatus.OK)
    public void exportCSV(HttpServletResponse response) throws Exception {
        final LocalDate startDate = LocalDate.now().minusDays(4);
        final LocalDate endDate = LocalDate.now().plusDays(1);

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
    }

}
