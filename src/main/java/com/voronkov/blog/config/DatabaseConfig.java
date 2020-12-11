package com.voronkov.blog.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@PropertySource("classpath:/postgres.properties")
public class DatabaseConfig {

//  @Autowired
//  private Environment environment;
//
//  private static final String URL = "database.url";
//  private static final String USER = "database.user";
//  private static final String PASSWORD = "database.password";
//
//  @Bean
//  public DataSource dataSource() {
//    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//    driverManagerDataSource.setUrl(environment.getProperty(URL));
//    driverManagerDataSource.setUsername(environment.getProperty(USER));
//    driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
//    return driverManagerDataSource;
//  }
//  
	@Bean
    public BasicDataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }

  @Bean
  public JdbcTemplate applicationDataConnection() throws URISyntaxException {
    return new JdbcTemplate(dataSource());
  }

  @Bean
  public PlatformTransactionManager txManager() throws URISyntaxException {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate() throws URISyntaxException {
    return new NamedParameterJdbcTemplate(dataSource());
  }
}
