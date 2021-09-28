package com.ttcsolutions.kwapp.core.config;

import com.ttcsolutions.kwapp.core.security.JwtAuthenticationFilter;
import com.ttcsolutions.kwapp.core.security.JwtProvider;
import com.ttcsolutions.kwapp.commons.controller.ExceptionController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtProvider jwtProvider;
    private final ExceptionController exceptionController;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
          .ignoring()
          .antMatchers(HttpMethod.GET,
            "/swagger-ui/**", "/swagger-resources", "/swagger-resources/**", "/v3/api-docs", "/actuator/**", "/captcha",
                  "/account-devices")
          .antMatchers(HttpMethod.POST, "/accounts", "/auth/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
          .anyRequest().authenticated();
        http.exceptionHandling()
          .accessDeniedHandler((request, response, accessDeniedException) ->
            exceptionController.handleAccessDeniedException(accessDeniedException, request, response))
          .authenticationEntryPoint((request, response, authException) ->
            exceptionController.handleAuthenticationException(authException, request, response));
        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider, exceptionController), UsernamePasswordAuthenticationFilter.class);
    }
}
