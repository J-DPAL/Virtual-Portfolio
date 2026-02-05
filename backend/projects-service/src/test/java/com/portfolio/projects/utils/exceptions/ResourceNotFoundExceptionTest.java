package com.portfolio.projects.utils.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ResourceNotFoundException Tests")
class ResourceNotFoundExceptionTest {

  @Test
  @DisplayName("Should create exception with message")
  void testConstructor() {
    ResourceNotFoundException exception = new ResourceNotFoundException("Not found");
    assertEquals("Not found", exception.getMessage());
  }

  @Test
  @DisplayName("Should be instance of RuntimeException")
  void testInheritance() {
    ResourceNotFoundException exception = new ResourceNotFoundException("Not found");
    assertTrue(exception instanceof RuntimeException);
  }

  @Test
  @DisplayName("Should be throwable")
  void testThrowable() {
    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          throw new ResourceNotFoundException("Not found");
        });
  }
}
