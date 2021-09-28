package com.ttcsolutions.kwapp.core.service.impl;

import com.ttcsolutions.kwapp.commons.exception.BusinessException;
import com.ttcsolutions.kwapp.commons.model.KwAccount;
import com.ttcsolutions.kwapp.commons.util.ErrorCode;
import com.ttcsolutions.kwapp.core.mapper.CreateAccount2EntityMapper;
import com.ttcsolutions.kwapp.core.mapper.Entity2LxUser;
import com.ttcsolutions.kwapp.core.model.entity.Account;
import com.ttcsolutions.kwapp.core.model.request.ChangePasswordRequest;
import com.ttcsolutions.kwapp.core.model.request.CreateAccountRequest;
import com.ttcsolutions.kwapp.core.repository.AccountRepository;
import com.ttcsolutions.kwapp.core.security.JwtProvider;
import com.ttcsolutions.kwapp.core.security.LoginResponse;
import com.ttcsolutions.kwapp.core.service.AccountInternalService;
import com.ttcsolutions.kwapp.core.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccountInternalServiceImpl implements AccountInternalService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository repository;
    private final CaptchaService captchaService;
    private final JwtProvider jwtProvider;


    /**
     * Register
     *
     * @param captchaId
     * @param captchaAnswer
     * @param createAccountRequest
     * @return
     */
    public LoginResponse create(String captchaId, Integer captchaAnswer, CreateAccountRequest createAccountRequest) {
        captchaService.verify(captchaId, captchaAnswer);
        createAccountRequest.setPassword(passwordEncoder.encode(createAccountRequest.getPassword()));
        var now = OffsetDateTime.now();
        var account = CreateAccount2EntityMapper.INSTANCE.map(createAccountRequest)
                .setCreatedAt(now)
                .setUpdatedAt(now);
        var savedAccount = repository.insert(account);
        return getLoginResponse(savedAccount);
    }

    public LoginResponse changePassword(Long accountId, ChangePasswordRequest changePasswordRequest) {
        Account account = getAccount0(accountId);
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), account.getPassword())) {
            throw new BusinessException(ErrorCode.WRONG_PASSWORD, "can't change password for account: " + accountId + " due mismatch password");
        }
        account.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        var saved = update(account);
        return getLoginResponse(saved);
    }

    @Override
    public Account getAccount0(long accountId) {
        return repository.findById(accountId)
                .orElseThrow(
                        () -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND, "account id: " + accountId + " is not found"));
    }

    @Override
    public Account getAccount0(String email) {
        return repository.findByEmail(email)
                .orElseThrow(
                        () -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND, "Account with email: " + email + " was not found"));
    }

    @Override
    public KwAccount getKwAccount(long id) {
        var account = getAccount0(id);
        return Entity2LxUser.INSTANCE.map(account);
    }

    private Account update(Account account) {
        return repository.update(account);
    }

    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    private LoginResponse getLoginResponse(Account account) {
        String token = jwtProvider.generateToken(Entity2LxUser.INSTANCE.map(account));
        return new LoginResponse(token);
    }

}

