package com.ttcsolutions.kwapp.core.security;

import com.ttcsolutions.kwapp.commons.controller.ExceptionController;
import com.ttcsolutions.kwapp.commons.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private static final String BEARER_TOKEN_PREFIX = "Bearer ";
  private static final int TOKEN_START_INDEX = BEARER_TOKEN_PREFIX.length();
  private final JwtProvider jwtProvider;
  private final ExceptionController exceptionController;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final var header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || !header.startsWith(BEARER_TOKEN_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }
    final var token = header.substring(TOKEN_START_INDEX);
    var authentication = jwtProvider.decode(token);
    if (authentication != null) {
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }
}
