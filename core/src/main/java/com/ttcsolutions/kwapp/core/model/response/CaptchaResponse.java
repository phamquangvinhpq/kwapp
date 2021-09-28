package com.ttcsolutions.kwapp.core.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
public class CaptchaResponse {
  private byte[] captcha;
  private OffsetDateTime expiredAt;
  private String captchaId;
}
