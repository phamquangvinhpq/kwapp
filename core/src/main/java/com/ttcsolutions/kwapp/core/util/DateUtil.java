package com.ttcsolutions.kwapp.core.util;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateUtil {
  private DateUtil() {
  }

  public static String millsToOffsetDateTimeString(long m) {
    return millsToOffsetDateTime(m).format(BusinessDateTimeUtil.FORMATTER);
  }

  public static Long getMillisecondFromStringDate(String requestTime) {
    OffsetDateTime zdt = OffsetDateTime.parse(requestTime, BusinessDateTimeUtil.FORMATTER);
    OffsetDateTime zonedIST = zdt.withOffsetSameInstant(ZoneOffset.of(BusinessDateTimeUtil.TIMEZONE));
    return zonedIST.toInstant().toEpochMilli();
  }

  public static OffsetDateTime parseOffsetDateTime(Date date) {
    return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.of(BusinessDateTimeUtil.TIMEZONE));
  }

  public static OffsetDateTime millsToOffsetDateTime(long m) {
    return millsToOffsetDateTime(m, BusinessDateTimeUtil.FORMATTER);
  }

  public static OffsetDateTime millsToOffsetDateTime(long m, DateTimeFormatter dateTimeFormatter) {

    if (m < 0) {
      throw new IllegalArgumentException("Format milliseconds to String ZoneDateTime");
    }

    ZoneId zoneId = ZoneId.of(BusinessDateTimeUtil.TIMEZONE);
    Instant instant = Instant.ofEpochMilli(m);
    return OffsetDateTime.ofInstant(instant, zoneId);
  }
}
