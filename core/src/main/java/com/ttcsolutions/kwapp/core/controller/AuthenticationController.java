package com.ttcsolutions.kwapp.core.controller;

import com.ttcsolutions.kwapp.commons.model.KwAccount;
import com.ttcsolutions.kwapp.commons.model.response.Response;
import com.ttcsolutions.kwapp.commons.util.Authentications;
import com.ttcsolutions.kwapp.core.model.request.LoginRequest;
import com.ttcsolutions.kwapp.core.security.LoginResponse;
import com.ttcsolutions.kwapp.core.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @GetMapping("/verify")
  public Response<Void> verifyToken() {
    return Response.ofSucceeded();
  }

  @PostMapping("/login")
  public Response<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    return Response.ofSucceeded(authenticationService.login(loginRequest));
  }

  @GetMapping("/refresh")
  public Response<KwAccount> refresh() {
    return Response.ofSucceeded(authenticationService.refresh(Authentications.requireAccountId()));
  }

}
