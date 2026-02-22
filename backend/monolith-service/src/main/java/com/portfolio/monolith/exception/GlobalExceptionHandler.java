package com.portfolio.monolith.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
    return build(ex.getStatus(), ex.getMessage(), null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }

    String message = "Validation failed";
    if (!errors.isEmpty()) {
      message = errors.values().iterator().next();
    }
    return build(HttpStatus.BAD_REQUEST, message, errors);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
    return build(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", null);
  }

  private ResponseEntity<ErrorResponse> build(HttpStatus status, String message, Map<String, String> errors) {
    ErrorResponse response =
        new ErrorResponse(status.value(), message, LocalDateTime.now(), errors);
    return ResponseEntity.status(status).body(response);
  }
}
