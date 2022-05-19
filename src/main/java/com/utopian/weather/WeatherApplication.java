package com.utopian.weather;

import com.utopian.weather.configuration.CacheConfiguration;
import com.utopian.weather.configuration.FlywayConfiguration;
import com.utopian.weather.configuration.WeatherConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@EntityScan
@Import({WeatherConfiguration.class, CacheConfiguration.class, FlywayConfiguration.class})
public class WeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

}
