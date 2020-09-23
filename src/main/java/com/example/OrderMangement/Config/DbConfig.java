package com.example.OrderMangement.Config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DbConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "com.example.ordermanagement.datasource")
	public DataSource dbDataSource() {
		return DataSourceBuilder.create().build();
	}

}
