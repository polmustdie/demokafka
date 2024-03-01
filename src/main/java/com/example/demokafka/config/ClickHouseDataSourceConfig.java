package com.example.demokafka.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(entityManagerFactoryRef = "clickEntityManagerFactory", basePackages = {"com.example.anomaly.clickhouse.repository"},
//transactionManagerRef = "clickTransactionManager")

public class ClickHouseDataSourceConfig {
    //using hikari config to create datasource to then use jdbctemplates
    @Bean(name="clickhouse")
    @ConfigurationProperties(prefix="spring.datasource.clickhouse")
    public HikariDataSource hikariDataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    //
    @Bean
    public JdbcTemplate jdbcTemplateClick(@Qualifier("clickhouse")HikariDataSource hikariDataSource){
        return new JdbcTemplate(hikariDataSource);
    }

//    @Bean(name="clickhouse")
//    @ConfigurationProperties(prefix="spring.datasource.clickhouse")
//
//    public DataSource dataSource(){
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name="clickEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("clickhouse")DataSource datasource){
//        Map<String, Object> properties = new HashMap<>();
//        properties.put( "hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        return builder.dataSource(datasource).properties(properties).packages("com.example.anomaly.clickhouse.model").persistenceUnit("GeoDataClick").build();
//    }
//    @Bean(name="clickTransactionManager")
//    public PlatformTransactionManager transactionManager(@Qualifier("clickEntityManagerFactory") EntityManagerFactory entityManagerFactory){
//        return new JpaTransactionManager(entityManagerFactory);
//    }
}
