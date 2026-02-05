package com.portfolio.education.utils.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ErrorResponse Tests")
class ErrorResponseTest {

  @Test
  @DisplayName("Should create ErrorResponse with no-args constructor")
  void testNoArgsConstructor() {
    ErrorResponse errorResponse = new ErrorResponse();
    assertNotNull(errorResponse);
  }

  @Test
  @DisplayName("Should create ErrorResponse with all-args constructor")
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    Map<String, String> errors = new HashMap<>();
    errors.put("field1", "error1");

    ErrorResponse errorResponse = new ErrorResponse(404, "Not Found", now, errors);

    assertNotNull(errorResponse);
    assertEquals(404, errorResponse.getStatus());
    assertEquals("Not Found", errorResponse.getMessage());
    assertEquals(now, errorResponse.getTimestamp());
    assertEquals(errors, errorResponse.getErrors());
  }

  @Test
  @DisplayName("Should create ErrorResponse using builder")
  void testBuilder() {
    LocalDateTime now = LocalDateTime.now();
    Map<String, String> errors = new HashMap<>();
    errors.put("email", "Email is required");

    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(400)
            .message("Validation failed")
            .timestamp(now)
            .errors(errors)
            .build();

    assertNotNull(errorResponse);
    assertEquals(400, errorResponse.getStatus());
    assertEquals("Validation failed", errorResponse.getMessage());
    assertEquals(now, errorResponse.getTimestamp());
    assertEquals(errors, errorResponse.getErrors());
  }

  @Test
  @DisplayName("Should set and get all fields correctly")
  void testSettersAndGetters() {
    LocalDateTime now = LocalDateTime.now();
    Map<String, String> errors = new HashMap<>();
    errors.put("password", "Password is too short");

    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(401);
    errorResponse.setMessage("Unauthorized");
    errorResponse.setTimestamp(now);
    errorResponse.setErrors(errors);

    assertEquals(401, errorResponse.getStatus());
    assertEquals("Unauthorized", errorResponse.getMessage());
    assertEquals(now, errorResponse.getTimestamp());
    assertEquals(errors, errorResponse.getErrors());
  }

  @Test
  @DisplayName("Should handle null errors map")
  void testNullErrorsMap() {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(500)
            .message("Internal Server Error")
            .timestamp(LocalDateTime.now())
            .errors(null)
            .build();

    assertNull(errorResponse.getErrors());
  }

  @Test
  @DisplayName("Should test equals and hashCode")
  void testEqualsAndHashCode() {
    LocalDateTime now = LocalDateTime.now();
    Map<String, String> errors = new HashMap<>();
    errors.put("field", "error");

    ErrorResponse error1 =
        ErrorResponse.builder()
            .status(404)
            .message("Not Found")
            .timestamp(now)
            .errors(errors)
            .build();

    ErrorResponse error2 =
        ErrorResponse.builder()
            .status(404)
            .message("Not Found")
            .timestamp(now)
            .errors(errors)
            .build();

    assertEquals(error1, error2);
    assertEquals(error1.hashCode(), error2.hashCode());
  }

  @Test
  @DisplayName("Should test toString method")
  void testToString() {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(404)
            .message("Resource not found")
            .timestamp(LocalDateTime.now())
            .build();

    String toString = errorResponse.toString();
    assertNotNull(toString);
    assertTrue(toString.contains("404"));
    assertTrue(toString.contains("Resource not found"));
  }
}
