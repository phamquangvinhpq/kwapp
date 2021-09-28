package com.ttcsolutions.kwapp.core.util.validator;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileValidator  implements ConstraintValidator<File, MultipartFile> {

  @Override
  public void initialize(File constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
    if (file.isEmpty()) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("file is empty")
        .addPropertyNode("file").addConstraintViolation();
    }
    return !file.isEmpty();
  }

}
