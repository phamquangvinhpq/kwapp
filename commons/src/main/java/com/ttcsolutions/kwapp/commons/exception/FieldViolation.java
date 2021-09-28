package com.ttcsolutions.kwapp.commons.exception;

public class FieldViolation {
  private final String field;
  private final String description;

  public FieldViolation(String field, String description) {
    this.field = field;
    this.description = description;
  }

  public String getField() {
    return field;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "FieldViolation{" +
      "field='" + field + '\'' +
      ", description='" + description + '\'' +
      '}';
  }
}
