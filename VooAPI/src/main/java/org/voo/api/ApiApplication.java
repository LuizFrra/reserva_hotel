package org.voo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.voo.api.repositories.AirplaneRepository;
import org.voo.api.repositories.CityRepository;
import org.voo.api.repositories.FlyCompanyRepository;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
public class ApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	@ConfigurationProperties("app.datasource")
	public DataSource dataSource(){
		return DataSourceBuilder.create().build();
	}

	@Bean
	public CommandLineRunner demo(FlyCompanyRepository repository, CityRepository cityRepository, AirplaneRepository airplaneRepository) {
		return (args) -> {

		};
	}
}
