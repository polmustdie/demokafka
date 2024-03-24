package com.example.demokafka;

import com.example.demokafka.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class DemokafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemokafkaApplication.class, args);
	}

}
