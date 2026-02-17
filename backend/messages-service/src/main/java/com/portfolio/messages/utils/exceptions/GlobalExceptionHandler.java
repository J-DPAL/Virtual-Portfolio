package com.portfolio.messages.utils.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException ex) {
    log.error("Resource not found: {}", ex.getMessage());
    ErrorResponse error =
        ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    log.error("Validation error: {}", ex.getMessage());
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    ErrorResponse error =
        ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message("Validation failed")
            .timestamp(LocalDateTime.now())
            .errors(errors)
            .build();

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RateLimitExceededException.class)
  public ResponseEntity<ErrorResponse> handleRateLimitExceededException(
      RateLimitExceededException ex) {
    log.warn("Rate limit exceeded: {}", ex.getMessage());
    ErrorResponse error =
        ErrorResponse.builder()
            .status(HttpStatus.TOO_MANY_REQUESTS.value())
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
    return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
  }

  @ExceptionHandler({InvalidCaptchaException.class, ContactValidationException.class})
  public ResponseEntity<ErrorResponse> handleContactProtectionException(RuntimeException ex) {
    log.warn("Contact protection validation failed: {}", ex.getMessage());
    ErrorResponse error =
        ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MailNotificationException.class)
  public ResponseEntity<ErrorResponse> handleMailNotificationException(
      MailNotificationException ex) {
    log.error("Mail notification exception: {} - {}", ex.getErrorCode(), ex.getMessage());

    // Determine HTTP status based on error code
    HttpStatus status = determineMailErrorStatus(ex.getErrorCode());

    ErrorResponse error =
        ErrorResponse.builder()
            .status(status.value())
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();

    return new ResponseEntity<>(error, status);
  }

  private HttpStatus determineMailErrorStatus(String errorCode) {
    return switch (errorCode) {
      case "CIRCUIT_BREAKER_OPEN" -> HttpStatus.SERVICE_UNAVAILABLE; // 503
      case "RETRY_EXHAUSTED" -> HttpStatus.GATEWAY_TIMEOUT; // 504
      case "MAIL_PROVIDER_ERROR" -> HttpStatus.BAD_GATEWAY; // 502
      default -> HttpStatus.INTERNAL_SERVER_ERROR; // 500
    };
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
    log.error("Unexpected error occurred", ex);

    // Include detailed error message for debugging
    String errorMessage =
        ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred";

    ErrorResponse error =
        ErrorResponse.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message(errorMessage)
            .timestamp(LocalDateTime.now())
            .build();

    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
