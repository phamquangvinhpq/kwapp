package com.ttcsolutions.kwapp.core.test;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockLxUserSecurityContextFactory.class)
public @interface WithMockKwAccount {
  long id() default 1L;
  String email() default "abc@gmail.com";
}
