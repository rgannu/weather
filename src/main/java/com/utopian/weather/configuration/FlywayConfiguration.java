package com.utopian.weather.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceSchemaCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfiguration {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Value("${spring.datasource.username}")
    private String dataSourceUser;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Value("${spring.jpa.properties.hibernate.default_schema}")
    private String dataSourceSchema;

    @Autowired
    private DataSource dataSource;

    @Bean
    public FlywayConfigurationCustomizer flywayCustomizer() {
        return new FlywayCustomizer(dataSourceUser, dataSourcePassword,
                dataSourceSchema, dataSource);
    }

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            flyway.repair();
            flyway.migrate();
            publisher.publishEvent(new DataSourceSchemaCreatedEvent(dataSource));
        };
    }

}
