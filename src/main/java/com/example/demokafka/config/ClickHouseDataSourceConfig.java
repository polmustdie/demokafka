package com.example.demokafka.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class ClickHouseDataSourceConfig {
    //using hikari config to create datasource to then use jdbctemplates
    @Bean(name="clickhouse")
    @ConfigurationProperties(prefix="spring.datasource.clickhouse")
    public HikariDataSource hikariDataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate jdbcTemplateClick(@Qualifier("clickhouse")HikariDataSource hikariDataSource){
        return new JdbcTemplate(hikariDataSource);
    }
}
