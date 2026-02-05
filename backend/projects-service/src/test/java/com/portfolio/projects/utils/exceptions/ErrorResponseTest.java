package com.portfolio.projects.utils.exceptions;

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
    ErrorResponse response = new ErrorResponse();
    assertNotNull(response);
  }

  @Test
  @DisplayName("Should create ErrorResponse with all-args constructor")
  void testAllArgsConstructor() {
    Map<String, String> errors = new HashMap<>();
    errors.put("field", "error");
    LocalDateTime now = LocalDateTime.now();

    ErrorResponse response = new ErrorResponse(400, "Bad Request", now, errors);

    assertEquals(400, response.getStatus());
    assertEquals("Bad Request", response.getMessage());
    assertEquals(now, response.getTimestamp());
    assertEquals(errors, response.getErrors());
  }

  @Test
  @DisplayName("Should create ErrorResponse using builder")
  void testBuilder() {
    LocalDateTime now = LocalDateTime.now();
    ErrorResponse response =
        ErrorResponse.builder().status(404).message("Not Found").timestamp(now).build();

    assertEquals(404, response.getStatus());
    assertEquals("Not Found", response.getMessage());
    assertEquals(now, response.getTimestamp());
  }

  @Test
  @DisplayName("Should set and get all fields")
  void testGettersAndSetters() {
    ErrorResponse response = new ErrorResponse();
    Map<String, String> errors = new HashMap<>();
    errors.put("field", "error");
    LocalDateTime now = LocalDateTime.now();

    response.setStatus(500);
    response.setMessage("Server Error");
    response.setTimestamp(now);
    response.setErrors(errors);

    assertEquals(500, response.getStatus());
    assertEquals("Server Error", response.getMessage());
    assertEquals(now, response.getTimestamp());
    assertEquals(errors, response.getErrors());
  }

  @Test
  @DisplayName("Should allow null errors map")
  void testNullErrorsMap() {
    ErrorResponse response = new ErrorResponse();
    response.setErrors(null);
    assertNull(response.getErrors());
  }

  @Test
  @DisplayName("Should implement equals, hashCode, and toString")
  void testEqualsHashCodeToString() {
    LocalDateTime now = LocalDateTime.now();
    ErrorResponse response1 = new ErrorResponse(400, "Bad Request", now, null);
    ErrorResponse response2 = new ErrorResponse(400, "Bad Request", now, null);

    assertEquals(response1, response1);
    assertEquals(response1, response2);
    assertEquals(response1.hashCode(), response2.hashCode());
    assertNotNull(response1.toString());
  }

  @Test
  @DisplayName("Should not equal when errors map differs")
  void testEqualsWithDifferentErrors() {
    LocalDateTime now = LocalDateTime.now();
    ErrorResponse response1 = new ErrorResponse(400, "Bad Request", now, null);
    Map<String, String> errors = new HashMap<>();
    errors.put("field", "error");
    ErrorResponse response2 = new ErrorResponse(400, "Bad Request", now, errors);

    assertNotEquals(response1, response2);
  }
}
