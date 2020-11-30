package com.voronkov.blog.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/application.properties")
public class ConnectionConfig {

  private Environment environment;

  public ConnectionConfig(Environment environment) {
    this.environment = environment;
  }

  private static final String URL = "url";
  private static final String USER = "user";
  private static final String DRIVER = "driver";
  private static final String PASSWORD = "password";

  @Bean
  DataSource dataSource() {
    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
    driverManagerDataSource.setUrl(environment.getProperty(URL));
    driverManagerDataSource.setUsername(environment.getProperty(USER));
    driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
    driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
    return driverManagerDataSource;
  }

  @Bean
  public JdbcTemplate applicationDataConnection() {
    return new JdbcTemplate(dataSource());
  }

  @Bean
  public PlatformTransactionManager txManager() {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
    return new NamedParameterJdbcTemplate(dataSource());
  }
}
