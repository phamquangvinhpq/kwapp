package com.ttcsolutions.kwapp.commons.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestAdvice implements ResponseBodyAdvice<Object> {

  private final Logger logger = LogManager.getLogger(getClass());
  private final ObjectMapper objectMapper;


  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {

    if (body != null && !request.getURI().toString().contains("/actuator/health")) {
      try {
        logger.info("Response {} {} : {}", request.getMethod(), request.getURI(), objectMapper.writeValueAsString(body));
      }
      catch (JsonProcessingException ignored) {
      }
    }


    return body;
  }

}