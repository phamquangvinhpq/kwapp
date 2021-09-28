package com.ttcsolutions.kwapp.core.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.OAS_30)
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.ttcsolutions.kwapp.core.controller"))
      .paths(PathSelectors.any())
      .build().apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("LIVE STREAM API")
      .description("API documentation for Live Stream project")
      .version("1.0.0")
      .build();
  }
}
