package com.portfolio.files.utils.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(FileStorageException.class)
  public ResponseEntity<Map<String, Object>> handleFileStorageException(FileStorageException ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("timestamp", LocalDateTime.now());
    errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    errorResponse.put("error", "File Storage Error");
    errorResponse.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(
      ResourceNotFoundException ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("timestamp", LocalDateTime.now());
    errorResponse.put("status", HttpStatus.NOT_FOUND.value());
    errorResponse.put("error", "Not Found");
    errorResponse.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<Map<String, Object>> handleMaxSizeException(
      MaxUploadSizeExceededException ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("timestamp", LocalDateTime.now());
    errorResponse.put("status", HttpStatus.PAYLOAD_TOO_LARGE.value());
    errorResponse.put("error", "File Too Large");
    errorResponse.put(
        "message", "Maximum upload size exceeded. Please upload a file smaller than 10MB.");

    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("timestamp", LocalDateTime.now());
    errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    errorResponse.put("error", "Internal Server Error");
    errorResponse.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
