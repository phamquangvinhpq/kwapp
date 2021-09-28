package com.ttcsolutions.kwapp.commons.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumValueValidator.class)
public @interface EnumValue {
  Class<? extends Enum<?>> enumClass();

  String message() default "Enum value is not valid ";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
