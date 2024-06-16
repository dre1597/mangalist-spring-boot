package org.example.mangalistspringboot.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {

    var info = new Info()
        .title("Mangalist API")
        .description("Mangalist API")
        .version("v0.0.1");

    return new OpenAPI().info(info);
  }
}
