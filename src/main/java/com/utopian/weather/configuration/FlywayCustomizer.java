package com.utopian.weather.configuration;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;

public class FlywayCustomizer implements FlywayConfigurationCustomizer {

    private final String dataSourceUser;
    private final String dataSourcePassword;
    private final String dataSourceSchema;
    private final DataSource dataSource;

    public FlywayCustomizer(String dataSourceUser, String dataSourcePassword,
            String dataSourceSchema, DataSource dataSource) {
        this.dataSourceUser = dataSourceUser;
        this.dataSourcePassword = dataSourcePassword;
        this.dataSourceSchema = dataSourceSchema;
        this.dataSource = dataSource;
    }

    @Override
    public void customize(FluentConfiguration configuration) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("db.owner", dataSourceUser);
        placeholders.put("db.user", dataSourceUser);
        placeholders.put("db.password", dataSourcePassword);
        configuration.placeholders(placeholders);
        configuration.schemas(dataSourceSchema);
        configuration.outOfOrder(true);
        configuration.baselineOnMigrate(true);
        configuration.dataSource(dataSource);
        configuration.validateOnMigrate(
                false);//this prevents flyway from failing when it can't find a missing dependency.
    }
}
