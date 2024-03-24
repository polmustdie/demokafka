package com.example.demokafka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "properties")
@Getter
@Setter
public class ApplicationProperties {
    private int windowSize;
    private int readFromDbSeconds;
}
