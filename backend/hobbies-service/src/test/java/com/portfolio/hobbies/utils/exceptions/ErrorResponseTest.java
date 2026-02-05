package com.portfolio.hobbies.utils.exceptions;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

  private ErrorResponse errorResponse;

  @BeforeEach
  void setUp() {
    errorResponse = new ErrorResponse();
  }

  @Test
  void testNoArgsConstructor() {
    ErrorResponse response = new ErrorResponse();

    assertNotNull(response);
    assertEquals(0, response.getStatus());
    assertNull(response.getMessage());
    assertNull(response.getTimestamp());
    assertNull(response.getErrors());
  }

  @Test
  void testAllArgsConstructor() {
    int status = 400;
    String message = "Bad Request";
    LocalDateTime timestamp = LocalDateTime.now();
    java.util.Map<String, String> errors = new java.util.HashMap<>();
    errors.put("field1", "error message");

    ErrorResponse response = new ErrorResponse(status, message, timestamp, errors);

    assertEquals(status, response.getStatus());
    assertEquals(message, response.getMessage());
    assertEquals(timestamp, response.getTimestamp());
    assertEquals(errors, response.getErrors());
  }

  @Test
  void testBuilder() {
    int status = 404;
    String message = "Not Found";
    LocalDateTime timestamp = LocalDateTime.now();
    java.util.Map<String, String> errors = new java.util.HashMap<>();

    ErrorResponse response =
        ErrorResponse.builder()
            .status(status)
            .message(message)
            .timestamp(timestamp)
            .errors(errors)
            .build();

    assertEquals(status, response.getStatus());
    assertEquals(message, response.getMessage());
    assertEquals(timestamp, response.getTimestamp());
    assertEquals(errors, response.getErrors());
  }

  @Test
  void testGettersAndSetters() {
    int status = 500;
    String message = "Internal Server Error";
    LocalDateTime timestamp = LocalDateTime.now();
    java.util.Map<String, String> errors = new java.util.HashMap<>();
    errors.put("key", "value");

    ErrorResponse response = new ErrorResponse();
    response.setStatus(status);
    response.setMessage(message);
    response.setTimestamp(timestamp);
    response.setErrors(errors);

    assertEquals(status, response.getStatus());
    assertEquals(message, response.getMessage());
    assertEquals(timestamp, response.getTimestamp());
    assertEquals(errors, response.getErrors());
  }

  @Test
  void testAllowNullErrorsMap() {
    int status = 400;
    String message = "Bad Request";
    LocalDateTime timestamp = LocalDateTime.now();

    ErrorResponse response =
        ErrorResponse.builder()
            .status(status)
            .message(message)
            .timestamp(timestamp)
            .errors(null)
            .build();

    assertEquals(status, response.getStatus());
    assertEquals(message, response.getMessage());
    assertEquals(timestamp, response.getTimestamp());
    assertNull(response.getErrors());
  }

  @Test
  void testEqualsHashCodeToString() {
    LocalDateTime timestamp = LocalDateTime.now();
    java.util.Map<String, String> errors = new java.util.HashMap<>();
    errors.put("field", "error");

    ErrorResponse response1 =
        ErrorResponse.builder()
            .status(400)
            .message("Bad Request")
            .timestamp(timestamp)
            .errors(errors)
            .build();

    ErrorResponse response2 =
        ErrorResponse.builder()
            .status(400)
            .message("Bad Request")
            .timestamp(timestamp)
            .errors(errors)
            .build();

    assertEquals(response1, response2);
    assertEquals(response1.hashCode(), response2.hashCode());
    assertNotNull(response1.toString());
    assertTrue(response1.toString().contains("ErrorResponse"));
  }

  @Test
  void testEqualsWithDifferentStatus() {
    LocalDateTime timestamp = LocalDateTime.now();
    java.util.Map<String, String> errors = new java.util.HashMap<>();

    ErrorResponse response1 =
        ErrorResponse.builder()
            .status(400)
            .message("Bad Request")
            .timestamp(timestamp)
            .errors(errors)
            .build();

    ErrorResponse response2 =
        ErrorResponse.builder()
            .status(500)
            .message("Bad Request")
            .timestamp(timestamp)
            .errors(errors)
            .build();

    assertNotEquals(response1, response2);
  }

  @Test
  void testEqualsWithDifferentMessage() {
    LocalDateTime timestamp = LocalDateTime.now();
    java.util.Map<String, String> errors = new java.util.HashMap<>();

    ErrorResponse response1 =
        ErrorResponse.builder()
            .status(400)
            .message("Bad Request")
            .timestamp(timestamp)
            .errors(errors)
            .build();

    ErrorResponse response2 =
        ErrorResponse.builder()
            .status(400)
            .message("Different Message")
            .timestamp(timestamp)
            .errors(errors)
            .build();

    assertNotEquals(response1, response2);
  }

  @Test
  void testEqualsWithDifferentTimestamp() {
    LocalDateTime timestamp1 = LocalDateTime.now();
    LocalDateTime timestamp2 = timestamp1.plusHours(1);
    java.util.Map<String, String> errors = new java.util.HashMap<>();

    ErrorResponse response1 =
        ErrorResponse.builder()
            .status(400)
            .message("Bad Request")
            .timestamp(timestamp1)
            .errors(errors)
            .build();

    ErrorResponse response2 =
        ErrorResponse.builder()
            .status(400)
            .message("Bad Request")
            .timestamp(timestamp2)
            .errors(errors)
            .build();

    assertNotEquals(response1, response2);
  }

  @Test
  void testEqualsWithDifferentErrors() {
    LocalDateTime timestamp = LocalDateTime.now();
    java.util.Map<String, String> errors1 = new java.util.HashMap<>();
    errors1.put("field1", "error1");

    java.util.Map<String, String> errors2 = new java.util.HashMap<>();
    errors2.put("field2", "error2");

    ErrorResponse response1 =
        ErrorResponse.builder()
            .status(400)
            .message("Bad Request")
            .timestamp(timestamp)
            .errors(errors1)
            .build();

    ErrorResponse response2 =
        ErrorResponse.builder()
            .status(400)
            .message("Bad Request")
            .timestamp(timestamp)
            .errors(errors2)
            .build();

    assertNotEquals(response1, response2);
  }

  @Test
  void testEqualsWithNullErrorsMap() {
    LocalDateTime timestamp = LocalDateTime.now();

    ErrorResponse response1 =
        ErrorResponse.builder()
            .status(400)
            .message("Bad Request")
            .timestamp(timestamp)
            .errors(null)
            .build();

    ErrorResponse response2 =
        ErrorResponse.builder()
            .status(400)
            .message("Bad Request")
            .timestamp(timestamp)
            .errors(null)
            .build();

    assertEquals(response1, response2);
  }
}
