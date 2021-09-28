package com.ttcsolutions.kwapp.core.service.impl;

import com.ttcsolutions.kwapp.commons.exception.BusinessException;
import com.ttcsolutions.kwapp.commons.model.KwAccount;
import com.ttcsolutions.kwapp.commons.util.ErrorCode;
import com.ttcsolutions.kwapp.core.mapper.Entity2LxUser;
import com.ttcsolutions.kwapp.core.model.entity.Account;
import com.ttcsolutions.kwapp.core.model.request.LoginRequest;
import com.ttcsolutions.kwapp.core.security.JwtProvider;
import com.ttcsolutions.kwapp.core.security.LoginResponse;
import com.ttcsolutions.kwapp.core.service.AccountInternalService;
import com.ttcsolutions.kwapp.core.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final PasswordEncoder passwordEncoder;
  private final AccountInternalService accountService;
  private final JwtProvider jwtProvider;


  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    Account account = accountService.getAccount0(loginRequest.getEmail());
    if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
      throw new BusinessException(ErrorCode.WRONG_PASSWORD,  ErrorCode.WRONG_PASSWORD.getMessage());
    }
    return getLoginResponse(account);
  }


  @Override
  public KwAccount refresh(Long userId) {
    return accountService.getKwAccount(userId);
  }


  private LoginResponse getLoginResponse(Account account) {
    String token = jwtProvider.generateToken(Entity2LxUser.INSTANCE.map(account));
    return new LoginResponse(token);
  }

}
