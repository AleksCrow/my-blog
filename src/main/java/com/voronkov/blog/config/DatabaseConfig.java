package com.voronkov.blog.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/postgres.properties")
public class DatabaseConfig {

  @Autowired
  private Environment environment;

  private static final String URL = "database.url";
  private static final String USER = "database.user";
  private static final String PASSWORD = "database.password";

  @Bean
  DataSource dataSource() {
    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
    driverManagerDataSource.setUrl(environment.getProperty(URL));
    driverManagerDataSource.setUsername(environment.getProperty(USER));
    driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
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
