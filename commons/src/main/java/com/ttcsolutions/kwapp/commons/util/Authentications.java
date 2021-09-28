package com.ttcsolutions.kwapp.commons.util;

import com.ttcsolutions.kwapp.commons.model.KwAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class Authentications {
  private Authentications() {
  }

  public static Long requireAccountId() {
    return requireAccountId(SecurityContextHolder.getContext().getAuthentication());
  }

  public static Long requireAccountId(Authentication authentication) {
    return ((KwAccount) authentication.getPrincipal()).getId();
  }

  public static KwAccount requireKwAccount() {
    return (KwAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public static Optional<KwAccount> getKwAccount() {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof KwAccount) {
      return Optional.of((KwAccount) principal);
    }
    return Optional.empty();
  }

  public static Optional<Long> getAccountId() {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof KwAccount) {
      return Optional.of(((KwAccount) principal).getId());
    }
    return Optional.empty();
  }
}
