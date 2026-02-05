package com.portfolio.testimonials.utils.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

  private ErrorResponse errorResponse;

  @BeforeEach
  void setUp() {
    LocalDateTime now = LocalDateTime.now();
    Map<String, String> errors = new HashMap<>();
    errors.put("field1", "error message 1");
    errors.put("field2", "error message 2");

    errorResponse =
        ErrorResponse.builder()
            .status(400)
            .message("Validation failed")
            .timestamp(now)
            .errors(errors)
            .build();
  }

  @Test
  void testNoArgsConstructor() {
    ErrorResponse newErrorResponse = new ErrorResponse();
    assertEquals(0, newErrorResponse.getStatus());
    assertNull(newErrorResponse.getMessage());
    assertNull(newErrorResponse.getTimestamp());
    assertNull(newErrorResponse.getErrors());
  }

  @Test
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    Map<String, String> errors = new HashMap<>();
    errors.put("email", "Email is required");

    ErrorResponse testErrorResponse = new ErrorResponse(400, "Validation failed", now, errors);

    assertEquals(400, testErrorResponse.getStatus());
    assertEquals("Validation failed", testErrorResponse.getMessage());
    assertEquals(now, testErrorResponse.getTimestamp());
    assertEquals(errors, testErrorResponse.getErrors());
  }

  @Test
  void testBuilder() {
    LocalDateTime now = LocalDateTime.now();
    Map<String, String> errors = new HashMap<>();
    errors.put("username", "Username already exists");

    ErrorResponse builtErrorResponse =
        ErrorResponse.builder()
            .status(409)
            .message("Conflict")
            .timestamp(now)
            .errors(errors)
            .build();

    assertEquals(409, builtErrorResponse.getStatus());
    assertEquals("Conflict", builtErrorResponse.getMessage());
    assertEquals(now, builtErrorResponse.getTimestamp());
    assertEquals(errors, builtErrorResponse.getErrors());
  }

  @Test
  void testGettersAndSetters() {
    ErrorResponse testErrorResponse = new ErrorResponse();
    LocalDateTime now = LocalDateTime.now();
    Map<String, String> errors = new HashMap<>();

    testErrorResponse.setStatus(500);
    testErrorResponse.setMessage("Internal Server Error");
    testErrorResponse.setTimestamp(now);
    testErrorResponse.setErrors(errors);

    assertEquals(500, testErrorResponse.getStatus());
    assertEquals("Internal Server Error", testErrorResponse.getMessage());
    assertEquals(now, testErrorResponse.getTimestamp());
    assertEquals(errors, testErrorResponse.getErrors());
  }

  @Test
  void testAllowNullErrorsMap() {
    ErrorResponse testErrorResponse =
        ErrorResponse.builder()
            .status(404)
            .message("Not Found")
            .timestamp(LocalDateTime.now())
            .errors(null)
            .build();

    assertNull(testErrorResponse.getErrors());
  }

  @Test
  void testEqualsHashCodeToString() {
    LocalDateTime now = LocalDateTime.now();
    Map<String, String> errors = new HashMap<>();
    errors.put("field1", "error message 1");
    errors.put("field2", "error message 2");

    ErrorResponse another =
        ErrorResponse.builder()
            .status(400)
            .message("Validation failed")
            .timestamp(now)
            .errors(errors)
            .build();

    ErrorResponse testError =
        ErrorResponse.builder()
            .status(400)
            .message("Validation failed")
            .timestamp(now)
            .errors(errors)
            .build();

    assertEquals(testError, another);
    assertEquals(testError.hashCode(), another.hashCode());
    assertNotNull(testError.toString());
    assertTrue(testError.toString().contains("Validation failed"));
  }

  @Test
  void testEqualsWithDifferentStatus() {
    LocalDateTime now = LocalDateTime.now();
    Map<String, String> errors = new HashMap<>();
    errors.put("field1", "error message 1");

    ErrorResponse errorResponse1 =
        ErrorResponse.builder()
            .status(400)
            .message("Validation failed")
            .timestamp(now)
            .errors(errors)
            .build();

    ErrorResponse errorResponse2 =
        ErrorResponse.builder()
            .status(500)
            .message("Validation failed")
            .timestamp(now)
            .errors(errors)
            .build();

    assertNotEquals(errorResponse1, errorResponse2);
  }

  @Test
  void testEqualsWithNullErrorsMap() {
    LocalDateTime now = LocalDateTime.now();

    ErrorResponse errorResponse1 =
        ErrorResponse.builder()
            .status(400)
            .message("Validation failed")
            .timestamp(now)
            .errors(null)
            .build();

    ErrorResponse errorResponse2 =
        ErrorResponse.builder()
            .status(400)
            .message("Validation failed")
            .timestamp(now)
            .errors(null)
            .build();

    assertEquals(errorResponse1, errorResponse2);
  }

  @Test
  void testEqualsWithDifferentErrors() {
    LocalDateTime now = LocalDateTime.now();
    Map<String, String> errors1 = new HashMap<>();
    errors1.put("field1", "error message 1");

    Map<String, String> errors2 = new HashMap<>();
    errors2.put("field2", "error message 2");

    ErrorResponse errorResponse1 =
        ErrorResponse.builder()
            .status(400)
            .message("Validation failed")
            .timestamp(now)
            .errors(errors1)
            .build();

    ErrorResponse errorResponse2 =
        ErrorResponse.builder()
            .status(400)
            .message("Validation failed")
            .timestamp(now)
            .errors(errors2)
            .build();

    assertNotEquals(errorResponse1, errorResponse2);
  }
}
