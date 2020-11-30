package com.voronkov.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.voronkov.blog.config")
public class WebAppConfig implements WebMvcConfigurer {

  @Value("${upload.path}")
  private String uploadPath;

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }

  // @Bean
  // public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
  // final DataSourceInitializer initializer = new DataSourceInitializer();
  // initializer.setDataSource(dataSource);
  // initializer.setDatabasePopulator(databasePopulator());
  // return initializer;
  // }
  //
  // private DatabasePopulator databasePopulator() {
  // Resource resource = new ClassPathResource("initDB.sql");
  // final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
  // populator.addScript(resource);
  // return populator;
  // }

  @Bean
  public StandardServletMultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/img/**").addResourceLocations("file:///" + uploadPath + "/");
    registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/");
  }
}
