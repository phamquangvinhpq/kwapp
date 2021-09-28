package com.ttcsolutions.kwapp.core.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AccountExistsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountExists {
  String message() default "Account doesn't exist";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
