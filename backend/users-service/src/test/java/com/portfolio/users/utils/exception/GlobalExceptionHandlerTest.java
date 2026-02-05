package com.portfolio.users.utils.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GlobalExceptionHandler Unit Tests")
class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler exceptionHandler;

  @BeforeEach
  void setUp() {
    exceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  @DisplayName("Should handle RuntimeException with error message")
  void testHandleRuntimeException_WithMessage() {
    RuntimeException exception = new RuntimeException("Test error message");

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleRuntimeException(exception);

    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Test error message", response.getBody().getMessage());
    assertEquals("ERROR", response.getBody().getStatus());
  }

  @Test
  @DisplayName("Should handle RuntimeException with null message")
  void testHandleRuntimeException_WithNullMessage() {
    RuntimeException exception = new RuntimeException();

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleRuntimeException(exception);

    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNull(response.getBody().getMessage());
    assertEquals("ERROR", response.getBody().getStatus());
  }

  @Test
  @DisplayName("Should return 500 status code")
  void testHandleRuntimeException_Returns500Status() {
    RuntimeException exception = new RuntimeException("Test error");

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleRuntimeException(exception);

    assertEquals(500, response.getStatusCodeValue());
  }

  @Test
  @DisplayName("Should set status field to ERROR")
  void testHandleRuntimeException_SetsErrorStatus() {
    RuntimeException exception = new RuntimeException("Test error");

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleRuntimeException(exception);

    assertEquals("ERROR", response.getBody().getStatus());
  }

  @Test
  @DisplayName("Should handle multiple RuntimeExceptions")
  void testHandleRuntimeException_Multiple() {
    RuntimeException exception1 = new RuntimeException("Error 1");
    RuntimeException exception2 = new RuntimeException("Error 2");

    ResponseEntity<ErrorResponse> response1 = exceptionHandler.handleRuntimeException(exception1);
    ResponseEntity<ErrorResponse> response2 = exceptionHandler.handleRuntimeException(exception2);

    assertEquals("Error 1", response1.getBody().getMessage());
    assertEquals("Error 2", response2.getBody().getMessage());
    assertNotEquals(response1.getBody().getMessage(), response2.getBody().getMessage());
  }

  @Test
  @DisplayName("Should handle RuntimeException with long message")
  void testHandleRuntimeException_WithLongMessage() {
    String longMessage =
        "This is a very long error message that provides detailed information about what went wrong in the system with specific details about the error.";
    RuntimeException exception = new RuntimeException(longMessage);

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleRuntimeException(exception);

    assertEquals(longMessage, response.getBody().getMessage());
  }

  @Test
  @DisplayName("Should handle RuntimeException with special characters")
  void testHandleRuntimeException_WithSpecialCharacters() {
    RuntimeException exception = new RuntimeException("Error: @#$%^&*() - something went wrong!");

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleRuntimeException(exception);

    assertEquals("Error: @#$%^&*() - something went wrong!", response.getBody().getMessage());
  }

  @Test
  @DisplayName("Should return ErrorResponse in response body")
  void testHandleRuntimeException_ReturnsErrorResponse() {
    RuntimeException exception = new RuntimeException("Test error");

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleRuntimeException(exception);

    assertNotNull(response.getBody());
    assertTrue(response.getBody() instanceof ErrorResponse);
  }

  @Test
  @DisplayName("Should preserve exception message in response")
  void testHandleRuntimeException_PreservesMessage() {
    String originalMessage = "Original exception message";
    RuntimeException exception = new RuntimeException(originalMessage);

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleRuntimeException(exception);

    assertEquals(originalMessage, response.getBody().getMessage());
  }

  @Test
  @DisplayName("Should handle RuntimeException with cause")
  void testHandleRuntimeException_WithCause() {
    Throwable cause = new Exception("Root cause");
    RuntimeException exception = new RuntimeException("Main error", cause);

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleRuntimeException(exception);

    assertNotNull(response);
    assertEquals("Main error", response.getBody().getMessage());
  }

  @Test
  @DisplayName("Should not return null response")
  void testHandleRuntimeException_NotNull() {
    RuntimeException exception = new RuntimeException("Test error");

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleRuntimeException(exception);

    assertNotNull(response);
    assertNotNull(response.getBody());
  }

  @Test
  @DisplayName("Should handle exception with numeric message")
  void testHandleRuntimeException_WithNumericMessage() {
    RuntimeException exception = new RuntimeException("Error code: 12345");

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleRuntimeException(exception);

    assertEquals("Error code: 12345", response.getBody().getMessage());
  }

  @Test
  @DisplayName("Should handle exception with whitespace in message")
  void testHandleRuntimeException_WithWhitespaceMessage() {
    RuntimeException exception = new RuntimeException("   Error with spaces   ");

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleRuntimeException(exception);

    assertEquals("   Error with spaces   ", response.getBody().getMessage());
  }

  @Test
  @DisplayName("Should return consistent status for all exceptions")
  void testHandleRuntimeException_ConsistentStatus() {
    RuntimeException exception1 = new RuntimeException("Error 1");
    RuntimeException exception2 = new RuntimeException("Error 2");
    RuntimeException exception3 = new RuntimeException("Error 3");

    ResponseEntity<ErrorResponse> response1 = exceptionHandler.handleRuntimeException(exception1);
    ResponseEntity<ErrorResponse> response2 = exceptionHandler.handleRuntimeException(exception2);
    ResponseEntity<ErrorResponse> response3 = exceptionHandler.handleRuntimeException(exception3);

    assertEquals("ERROR", response1.getBody().getStatus());
    assertEquals("ERROR", response2.getBody().getStatus());
    assertEquals("ERROR", response3.getBody().getStatus());
  }

  @Test
  @DisplayName("Should create new ErrorResponse for each exception")
  void testHandleRuntimeException_NewResponseEachTime() {
    RuntimeException exception1 = new RuntimeException("Error 1");
    RuntimeException exception2 = new RuntimeException("Error 2");

    ResponseEntity<ErrorResponse> response1 = exceptionHandler.handleRuntimeException(exception1);
    ResponseEntity<ErrorResponse> response2 = exceptionHandler.handleRuntimeException(exception2);

    assertNotSame(response1.getBody(), response2.getBody());
  }
}
