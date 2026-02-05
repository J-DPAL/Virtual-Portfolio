package com.portfolio.education.utils.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ResourceNotFoundException Tests")
class ResourceNotFoundExceptionTest {

  @Test
  @DisplayName("Should create exception with message")
  void testConstructorWithMessage() {
    String message = "Education not found with id: 1";
    ResourceNotFoundException exception = new ResourceNotFoundException(message);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
  }

  @Test
  @DisplayName("Should be instance of RuntimeException")
  void testIsRuntimeException() {
    ResourceNotFoundException exception = new ResourceNotFoundException("Test message");
    assertTrue(exception instanceof RuntimeException);
  }

  @Test
  @DisplayName("Should be throwable")
  void testThrowable() {
    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          throw new ResourceNotFoundException("Resource not found");
        });
  }
}
