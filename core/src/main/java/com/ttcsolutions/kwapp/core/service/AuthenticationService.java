package com.ttcsolutions.kwapp.core.service;

import com.ttcsolutions.kwapp.commons.model.KwAccount;
import com.ttcsolutions.kwapp.core.model.request.LoginRequest;
import com.ttcsolutions.kwapp.core.security.LoginResponse;

public interface AuthenticationService {
  LoginResponse login(LoginRequest loginRequest);

  KwAccount refresh(Long userId);
}
