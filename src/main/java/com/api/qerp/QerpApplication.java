package com.api.qerp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.api.qerp.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class QerpApplication {

	public static void main(String[] args) {
		SpringApplication.run(QerpApplication.class, args);
	}

}

