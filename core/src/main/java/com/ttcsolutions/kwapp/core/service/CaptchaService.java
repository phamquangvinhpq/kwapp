package com.ttcsolutions.kwapp.core.service;

import com.ttcsolutions.kwapp.core.model.response.CaptchaResponse;

public interface CaptchaService {
  CaptchaResponse get();

  void verify(String captchaId, Integer answer);
}
