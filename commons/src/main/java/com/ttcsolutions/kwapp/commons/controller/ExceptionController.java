package com.ttcsolutions.kwapp.commons.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ttcsolutions.kwapp.commons.exception.BusinessErrorCode;
import com.ttcsolutions.kwapp.commons.exception.BusinessException;
import com.ttcsolutions.kwapp.commons.exception.FieldViolation;
import com.ttcsolutions.kwapp.commons.model.response.Response;
import com.ttcsolutions.kwapp.commons.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
  private Logger log = LogManager.getLogger(ExceptionController.class);
  private final ObjectMapper objectMapper;

  @ExceptionHandler(BusinessException.class)
  public void handleBusinessException(BusinessException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    handle(e, e.getErrorCode(), request, response);
  }

  @ExceptionHandler(Exception.class)
  public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    handle(e, ErrorCode.INTERNAL_SERVER_ERROR, request, response);
  }

  @ExceptionHandler(CompletionException.class)
  public void handleCompletionException(CompletionException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    var cause = e.getCause();
    if (cause instanceof BusinessException) {
      handleBusinessException((BusinessException) cause, request, response);
      return;
    }
    handleException(e, request, response);
  }

  @ExceptionHandler(BindException.class)
  public void handleException(BindException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    List<FieldViolation> violations = e.getBindingResult().getFieldErrors().stream()
      .map(fieldError -> new FieldViolation(fieldError.getField(), fieldError.getDefaultMessage())).collect(
        Collectors.toList());
    var errorResponse = Response.ofFailed(ErrorCode.INVALID_FIELD_FORMAT, "Invalid field format",
      violations);
    log.error("{}", errorResponse, e);
    writeResponse(response, ErrorCode.INVALID_FIELD_FORMAT.getHttpStatus(), errorResponse);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public void handleConstraintValidationException(
    ConstraintViolationException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    List<FieldViolation> violations = e.getConstraintViolations().stream()
      .map(violation -> new FieldViolation(( (PathImpl) violation.getPropertyPath()).getLeafNode().getParent().getName(),
        violation.getMessage()))
      .collect(Collectors.toList());
    var errorResponse = Response.ofFailed(ErrorCode.INVALID_FIELD_FORMAT, "Invalid field format",
      violations);
    log.error("{}", errorResponse, e);
    writeResponse(response, ErrorCode.INVALID_FIELD_FORMAT.getHttpStatus(), errorResponse);
  }

  @ExceptionHandler(InvalidFormatException.class)
  public void handleInvalidFormatException(InvalidFormatException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    List<FieldViolation> violations = e.getPath().stream().map(
      reference -> new FieldViolation(reference.getFieldName(), reference.getDescription())).collect(
      Collectors.toList());
    var errorResponse = Response.ofFailed(ErrorCode.INVALID_FIELD_FORMAT, "Invalid field format",
      violations);
    log.error("{}", errorResponse, e);
    writeResponse(response, ErrorCode.INVALID_FIELD_FORMAT.getHttpStatus(), errorResponse);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public void handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    String description;
    Class<?> requiredType = e.getRequiredType();
    if (requiredType != null) {
      description = "Invalid " + e.getName() +" require " + requiredType.getSimpleName()  + " type";
    } else {
       description = e.getMessage();
    }
    var fieldViolation = new FieldViolation(e.getName(), description);
    var errorResponse = Response.ofFailed(ErrorCode.INVALID_FIELD_FORMAT, Collections.singletonList(fieldViolation));
    log.error("{}", errorResponse, e);
    writeResponse(response, ErrorCode.INVALID_FIELD_FORMAT.getHttpStatus(), errorResponse);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public void handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    InvalidFormatException realCause;
    String fieldName;
    try {
      realCause = ((InvalidFormatException)e.getCause());
      fieldName = realCause.getPath().get(0).getFieldName();
    } catch (Exception exception) {
      handleException(e, request, response);
      return;
    }
    String  description;
    if (realCause.getTargetType().getEnumConstants() != null) {
      description = "Invalid " + fieldName +" require " + realCause.getTargetType().getSimpleName() + Arrays.toString(realCause.getTargetType().getEnumConstants())  + " type";
    } else {
      description = "Invalid " + fieldName +" require " + realCause.getTargetType().getSimpleName() + " type";
    }
    var fieldViolation = new FieldViolation(fieldName, description);
    var errorResponse = Response.ofFailed(ErrorCode.INVALID_FIELD_FORMAT, Collections.singletonList(fieldViolation));
    log.error("{}", errorResponse, e);
    writeResponse(response, ErrorCode.INVALID_FIELD_FORMAT.getHttpStatus(), errorResponse);
  }



  @ExceptionHandler(AccessDeniedException.class)
  public void handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    handle(e, ErrorCode.FORBIDDEN, request, response);
  }
  @ExceptionHandler(InsufficientAuthenticationException.class)
  public void handleInsufficientAuthenticationException(InsufficientAuthenticationException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    handle(e, ErrorCode.FORBIDDEN, request, response);
  }

  @ExceptionHandler(MissingServletRequestPartException.class)
  public void handleMissingServletRequestPartException(MissingServletRequestPartException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    handle(e, ErrorCode.INVALID_FIELD_FORMAT, request, response);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public void handleMissingServletRequestPartException(MissingServletRequestParameterException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    handle(e, ErrorCode.MISSING_PARAMETER, request, response);
  }


  @ExceptionHandler(AuthenticationException.class)
  public void handleAuthenticationException(AuthenticationException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (e instanceof InsufficientAuthenticationException) {
      handleInsufficientAuthenticationException((InsufficientAuthenticationException) e, request, response);
    } else  {
      handle(e, ErrorCode.UNAUTHORIZED, request, response);
    }
  }

  private <T extends Exception> void handle(T e, BusinessErrorCode errorCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
    var errorResponse = Response.ofFailed(errorCode, e.getMessage());
    log.error("Request: {} {}, Response: {}", request.getMethod(), request.getRequestURL(), objectMapper.writeValueAsString(errorResponse), e);
    writeResponse(response, errorCode.getHttpStatus(), errorResponse);
  }

  private void writeResponse(HttpServletResponse servletResponse, int httpStatus, Response<?> errorResponse) throws IOException {
    servletResponse.setStatus(httpStatus);
    servletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    byte[] body = objectMapper.writeValueAsBytes(errorResponse);
    servletResponse.setContentLength(body.length);
    servletResponse.getOutputStream().write(body);
  }
}
