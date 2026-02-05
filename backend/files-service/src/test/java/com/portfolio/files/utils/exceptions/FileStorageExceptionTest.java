package com.portfolio.files.utils.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FileStorageException Tests")
class FileStorageExceptionTest {

  @Test
  @DisplayName("Should create exception with message")
  void testMessageConstructor() {
    FileStorageException ex = new FileStorageException("Storage error");

    assertEquals("Storage error", ex.getMessage());
  }

  @Test
  @DisplayName("Should create exception with message and cause")
  void testMessageAndCauseConstructor() {
    RuntimeException cause = new RuntimeException("Root cause");
    FileStorageException ex = new FileStorageException("Storage error", cause);

    assertEquals("Storage error", ex.getMessage());
    assertEquals(cause, ex.getCause());
  }
}
