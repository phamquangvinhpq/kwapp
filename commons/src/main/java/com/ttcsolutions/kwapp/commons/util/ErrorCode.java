package com.ttcsolutions.kwapp.commons.util;

import com.ttcsolutions.kwapp.commons.exception.BusinessErrorCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ErrorCode {
  private static Logger log = LogManager.getLogger(ErrorCode.class);
  
  public static final BusinessErrorCode INTERNAL_SERVER_ERROR =
    new BusinessErrorCode(5000, "Internal server error", 503);

  public static final BusinessErrorCode INTERNAL_STORAGE_ERROR =
    new BusinessErrorCode(5001, "Internal storage error", 503);
  public static final BusinessErrorCode ACCOUNT_NOT_FOUND =
    new BusinessErrorCode(4001, "Account is not found", 400);

  public static final BusinessErrorCode WRONG_PASSWORD =
    new BusinessErrorCode(4002, "Phone number and password don't match", 400);
  public static final BusinessErrorCode INVALID_FIELD_FORMAT =
    new BusinessErrorCode(4003, "Invalid field format", 400);
  public static final BusinessErrorCode UNAUTHORIZED =
    new BusinessErrorCode(4004, "You need to login to to access this resource", 401);
  public static final BusinessErrorCode FORBIDDEN =
    new BusinessErrorCode(4005, "You don't have permission to to access this resource", 403);
  public static final BusinessErrorCode MISSING_PARAMETER =
    new BusinessErrorCode(4006, "Missing parameter", 400);
  public static final BusinessErrorCode FORMAT_DATE_INVALID =
    new BusinessErrorCode(4007, "Date format error", 400);
  public static final BusinessErrorCode INVALID_FIELD_NAME =
    new BusinessErrorCode(4008, "Field name is invalid", 400);
  public static final BusinessErrorCode BOOLEAN_FIELD_NAME =
    new BusinessErrorCode(4009, "Boolean name is invalid", 400);
  public static final BusinessErrorCode NUMBER_FORMAT_ERROR =
    new BusinessErrorCode(4010, "Number format error", 400);
  public static final BusinessErrorCode ENUM_FIELD_VALUE_INVALID =
    new BusinessErrorCode(4011, "Enum value is invalid", 400);
  public static final BusinessErrorCode EMAIL_NOT_FOUND =
    new BusinessErrorCode(4012, "Email is not found", 400);
  public static final BusinessErrorCode BOOLEAN_FORMAT_ERROR =
    new BusinessErrorCode(4013, "Invalid value of boolean type", 400);
  public static final BusinessErrorCode INVALID_FILTER_OPERATOR =
    new BusinessErrorCode(4014, "Invalid filter operator", 400);
  public static final BusinessErrorCode INVALID_REQUEST =
    new BusinessErrorCode(4015, "Invalid request", 400);
  public static final BusinessErrorCode UPLOAD_FILE_ERROR =
    new BusinessErrorCode(4016, "File upload error", 400);
  public static final BusinessErrorCode WRONG_CAPTCHA =
    new BusinessErrorCode(4017, "Wrong captcha", 400);
  public static final BusinessErrorCode DEVICE_ALREADY_CONNECTED =
		  new BusinessErrorCode(4018, "Device already connected", 400);
  public static final BusinessErrorCode DEVICE_NOT_FOUND =
		  new BusinessErrorCode(4019, "Device is not found", 400);
  public static final BusinessErrorCode DEVICE_INACTIVE =
		  new BusinessErrorCode(4020, "Device is inactive", 400);
  static {
    Set<Integer> codes = new HashSet<>();
    var duplications = Arrays.stream(ErrorCode.class.getDeclaredFields())
      .filter(f -> Modifier.isStatic(f.getModifiers()) && f.getType().equals(BusinessErrorCode.class))
      .map(f -> {
        try {
          return ((BusinessErrorCode) f.get(null)).getCode();
        }
        catch (IllegalAccessException e) {
          log.error("can't load error code into map", e);
          throw new RuntimeException(e);
        }
      })
      .filter(code -> !codes.add(code))
      .collect(Collectors.toList());
    if (!duplications.isEmpty()) {
      throw new RuntimeException("Duplicate error code: " + duplications);
    }
  }
}
