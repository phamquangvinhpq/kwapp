package com.ttcsolutions.kwapp.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfiguration {
  private ObjectMapper objectMapper;

  public AppConfiguration(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(0, new MappingJackson2HttpMessageConverter(objectMapper));
    return restTemplate;
  }

  @Bean
  public HttpTraceRepository httpTraceRepository() {
    return new InMemoryHttpTraceRepository();
  }

}
