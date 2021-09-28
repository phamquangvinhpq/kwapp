package com.ttcsolutions.kwapp.core.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface File {
  String message() default "Invalid file";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
