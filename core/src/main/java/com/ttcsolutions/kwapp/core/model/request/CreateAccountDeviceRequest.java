package com.ttcsolutions.kwapp.core.model.request;


import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * The object which is transfer from request body to.
 * @author mahai
 *
 */
@Data
@Accessors(chain = true)
public class CreateAccountDeviceRequest {
  @NotBlank
  private String deviceCode;

  @NotBlank
  private String deviceName;
  
  @NotBlank
  private String relationship;
  
  @NotBlank
  private String icon;
}
