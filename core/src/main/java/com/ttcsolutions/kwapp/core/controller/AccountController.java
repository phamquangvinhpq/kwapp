package com.ttcsolutions.kwapp.core.controller;

import com.ttcsolutions.kwapp.commons.model.response.Response;
import com.ttcsolutions.kwapp.commons.util.Authentications;
import com.ttcsolutions.kwapp.commons.util.Constant;
import com.ttcsolutions.kwapp.core.model.request.ChangePasswordRequest;
import com.ttcsolutions.kwapp.core.model.request.CreateAccountRequest;
import com.ttcsolutions.kwapp.core.security.LoginResponse;
import com.ttcsolutions.kwapp.core.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/accounts")
@Validated
@RequiredArgsConstructor
public class AccountController {
  private final AccountService accountService;

  @PostMapping
  public Response<LoginResponse> createAndLogin(@Valid @NotBlank @Pattern(regexp = Constant.UUID_REGEX)
                                              @RequestHeader(Constant.CAPTCHA_ID_HEADER) String captchaId,
                                                @Valid @NotNull @RequestHeader(Constant.CAPTCHA_ANSWER_HEADER) Integer captchaAnswer, @Valid @RequestBody CreateAccountRequest createAccountRequest) {
    return Response.ofSucceeded(accountService.create(captchaId, captchaAnswer,createAccountRequest));
  }

  @PutMapping("/password")
  public Response<LoginResponse> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
    return Response.ofSucceeded(accountService.changePassword(Authentications.requireAccountId(), changePasswordRequest));
  }

}
