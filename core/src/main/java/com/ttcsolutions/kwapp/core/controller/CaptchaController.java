package com.ttcsolutions.kwapp.core.controller;

import com.ttcsolutions.kwapp.commons.model.response.Response;
import com.ttcsolutions.kwapp.core.model.response.CaptchaResponse;
import com.ttcsolutions.kwapp.core.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor
public class CaptchaController {
  private final CaptchaService service;

  @GetMapping
  public Response<CaptchaResponse> get() {
    return Response.ofSucceeded(service.get());
  }
}
