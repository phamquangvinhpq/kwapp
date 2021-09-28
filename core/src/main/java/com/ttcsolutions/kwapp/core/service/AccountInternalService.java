package com.ttcsolutions.kwapp.core.service;

import com.ttcsolutions.kwapp.commons.model.KwAccount;
import com.ttcsolutions.kwapp.core.model.entity.Account;

public interface AccountInternalService extends AccountService {
  KwAccount getKwAccount(long id);

  Account getAccount0(long accountId);

  Account getAccount0(String email);

  boolean existsById(long id);

}
