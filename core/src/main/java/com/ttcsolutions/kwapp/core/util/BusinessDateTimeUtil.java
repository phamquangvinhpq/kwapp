package com.ttcsolutions.kwapp.core.util;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class BusinessDateTimeUtil {

  public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
  public static final String TIMEZONE = "+0700";
  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE;


  public static boolean checkFormatOfDate(String date) {
    try {
      OffsetDateTime.parse(date, FORMATTER);
      return true;
    }
    catch (DateTimeParseException e) {
      return false;
    }
  }

  public static OffsetDateTime todayStartTime() {
    return OffsetDateTime.now(ZoneId.of(BusinessDateTimeUtil.TIMEZONE)).truncatedTo(ChronoUnit.DAYS);
  }
}
