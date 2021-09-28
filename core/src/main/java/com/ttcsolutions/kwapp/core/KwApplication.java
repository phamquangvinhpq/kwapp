package com.ttcsolutions.kwapp.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
@ComponentScan({"com.ttcsolutions.kwapp.core", "com.ttcsolutions.kwapp.commons"})
public class KwApplication {

  public static void main(String[] args) {
    SpringApplication.run(KwApplication.class, args);
  }

}
