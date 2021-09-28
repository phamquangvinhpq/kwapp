package com.ttcsolutions.kwapp.core.util.validator;

import com.ttcsolutions.kwapp.core.service.AccountInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
@Component
@RequiredArgsConstructor
public class AccountExistsValidator implements ConstraintValidator<AccountExists, String> {
  private final AccountInternalService accountInternalService;
  public void initialize(AccountExists constraint) {
  }

  public boolean isValid(String id, ConstraintValidatorContext context) {
    try {
      var accountId = Long.valueOf(id);
      return accountInternalService.existsById(accountId);
    } catch (NumberFormatException e) {
      return true;
    }
  }
}
