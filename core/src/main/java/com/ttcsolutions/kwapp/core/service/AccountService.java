package com.ttcsolutions.kwapp.core.service;

import com.ttcsolutions.kwapp.core.model.request.ChangePasswordRequest;
import com.ttcsolutions.kwapp.core.model.request.CreateAccountRequest;
import com.ttcsolutions.kwapp.core.security.LoginResponse;

public interface AccountService {

  LoginResponse create(String captchaId, Integer captchaAnswer, CreateAccountRequest createAccountRequest);

  LoginResponse changePassword(Long id, ChangePasswordRequest changePasswordRequest);

}
