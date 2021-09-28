package com.ttcsolutions.kwapp.commons.util;

public final class Constant {
  public static final String PREFIX_RESPONSE_CODE = "KWA-";
  public static final String USER_ROLE = "ROLE_USER";
  public static final String CAPTCHA_ID_HEADER = "X-Captcha-Id";
  public static final String CAPTCHA_ANSWER_HEADER = "X-Captcha-Answer";
  public static final String UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";
  public static final String PASSWORD_REGEX = "^[\\x20-\\x7E\\p{L}]{8,32}$";
  
  private Constant() {
    //hidden constructor
  }
  
}