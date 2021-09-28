package com.ttcsolutions.kwapp.core.test;

import com.ttcsolutions.kwapp.commons.model.KwAccount;
import com.ttcsolutions.kwapp.commons.util.Constant;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithMockLxUserSecurityContextFactory implements WithSecurityContextFactory<WithMockKwAccount> {
  @Override
  public SecurityContext createSecurityContext(WithMockKwAccount annotation) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    var principal = new KwAccount().setId(annotation.id()).setEmail(annotation.email());
    var auth = new UsernamePasswordAuthenticationToken(principal, "password", List.of(new SimpleGrantedAuthority(Constant.USER_ROLE)));
    context.setAuthentication(auth);
    return context;
  }
}
