package com.ttcsolutions.kwapp.commons.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.http.HttpClient;


@Configuration
public class BeanConfiguration {

  @Bean
  public HttpClient httpClient() {
    return HttpClient.newHttpClient();
  }

  @Bean
  public JedisPool create(@Value("${kwapp.redis.host}") String host, @Value("${kwapp.redis.port}") int port,
                          @Value("${kwapp.redis.password}") String password,
                            @Value("${kwapp.redis.db-default}") int db, @Value("${kwapp.redis.timeout}") int timeout) {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(30);
    poolConfig.setMaxIdle(30);
    if (!StringUtils.hasText(password)) {
      password = null;
    }
    return new JedisPool(poolConfig, host, port, timeout, password, db);
  }
}
