package org.voo.api.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    @ConfigurationProperties(prefix="app.datasource")
    public DataSource dataSource() {
        //System.out.print("Data Sourceeeeeeeee");
        return DataSourceBuilder.create().build();
    }
}
