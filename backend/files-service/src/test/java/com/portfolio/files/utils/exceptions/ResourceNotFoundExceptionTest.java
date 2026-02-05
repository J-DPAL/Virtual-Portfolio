package com.portfolio.files.utils.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ResourceNotFoundException Tests")
class ResourceNotFoundExceptionTest {

  @Test
  @DisplayName("Should create exception with message")
  void testMessageConstructor() {
    ResourceNotFoundException ex = new ResourceNotFoundException("Not found");

    assertEquals("Not found", ex.getMessage());
  }
}
