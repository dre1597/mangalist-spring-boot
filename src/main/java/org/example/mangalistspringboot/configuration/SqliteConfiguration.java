package org.example.mangalistspringboot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class SqliteConfiguration {
  private final String driverClassName;
  private final String url;
  private final String username;
  private final String password;

  public SqliteConfiguration(
      @Value("${driverClassName}") String driverClassName,
      @Value("${url}") String url,
      @Value("${username}") String username,
      @Value("${password}") String password
  ) {
    this.driverClassName = driverClassName;
    this.url = url;
    this.username = username;
    this.password = password;
  }

  @Bean
  public DataSource dataSource() {
    final var dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }
}
