package com.portfolio.files.utils.exceptions;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  @DisplayName("Should handle FileStorageException")
  void testHandleFileStorageException() {
    FileStorageException ex = new FileStorageException("Storage error");

    ResponseEntity<Map<String, Object>> response = handler.handleFileStorageException(ex);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().get("status"));
    assertEquals("File Storage Error", response.getBody().get("error"));
    assertEquals("Storage error", response.getBody().get("message"));
    assertTrue(response.getBody().containsKey("timestamp"));
  }

  @Test
  @DisplayName("Should handle ResourceNotFoundException")
  void testHandleResourceNotFoundException() {
    ResourceNotFoundException ex = new ResourceNotFoundException("Not found");

    ResponseEntity<Map<String, Object>> response = handler.handleResourceNotFoundException(ex);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().get("status"));
    assertEquals("Not Found", response.getBody().get("error"));
    assertEquals("Not found", response.getBody().get("message"));
    assertTrue(response.getBody().containsKey("timestamp"));
  }

  @Test
  @DisplayName("Should handle MaxUploadSizeExceededException")
  void testHandleMaxSizeException() {
    MaxUploadSizeExceededException ex = new MaxUploadSizeExceededException(10L);

    ResponseEntity<Map<String, Object>> response = handler.handleMaxSizeException(ex);

    assertEquals(HttpStatus.PAYLOAD_TOO_LARGE, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.PAYLOAD_TOO_LARGE.value(), response.getBody().get("status"));
    assertEquals("File Too Large", response.getBody().get("error"));
    assertEquals(
        "Maximum upload size exceeded. Please upload a file smaller than 10MB.",
        response.getBody().get("message"));
    assertTrue(response.getBody().containsKey("timestamp"));
  }

  @Test
  @DisplayName("Should handle generic Exception")
  void testHandleGlobalException() {
    Exception ex = new Exception("Unexpected error");

    ResponseEntity<Map<String, Object>> response = handler.handleGlobalException(ex);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().get("status"));
    assertEquals("Internal Server Error", response.getBody().get("error"));
    assertEquals("Unexpected error", response.getBody().get("message"));
    assertTrue(response.getBody().containsKey("timestamp"));
  }
}
