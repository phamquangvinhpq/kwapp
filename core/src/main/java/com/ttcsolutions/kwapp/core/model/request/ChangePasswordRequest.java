package com.ttcsolutions.kwapp.core.model.request;


import com.ttcsolutions.kwapp.commons.util.Constant;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Accessors(chain = true)
public class ChangePasswordRequest {
  @NotBlank
  @Pattern(regexp = Constant.PASSWORD_REGEX)
  private String currentPassword;

  @NotBlank
  @Pattern(regexp = Constant.PASSWORD_REGEX)
  private String newPassword;
}
