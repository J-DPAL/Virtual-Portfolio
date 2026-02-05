package com.portfolio.education.utils.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler exceptionHandler;

  @BeforeEach
  void setUp() {
    exceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  @DisplayName("Should handle ResourceNotFoundException")
  void testHandleResourceNotFoundException() {
    // Arrange
    String errorMessage = "Education not found with id: 1";
    ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

    // Act
    ResponseEntity<ErrorResponse> response =
        exceptionHandler.handleResourceNotFoundException(exception);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
    assertEquals(errorMessage, response.getBody().getMessage());
    assertNotNull(response.getBody().getTimestamp());
    assertTrue(response.getBody().getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
  }

  @Test
  @DisplayName("Should handle MethodArgumentNotValidException")
  void testHandleValidationExceptions() throws NoSuchMethodException {
    // Arrange
    BeanPropertyBindingResult bindingResult =
        new BeanPropertyBindingResult(new Object(), "educationDTO");
    bindingResult.addError(
        new FieldError(
            "educationDTO", "institutionNameEn", "Institution name in English is required"));
    bindingResult.addError(
        new FieldError("educationDTO", "degreeEn", "Degree in English is required"));

    MethodParameter methodParameter =
        new MethodParameter(this.getClass().getDeclaredMethod("dummyMethod", String.class), 0);
    MethodArgumentNotValidException exception =
        new MethodArgumentNotValidException(methodParameter, bindingResult);

    // Act
    ResponseEntity<ErrorResponse> response = exceptionHandler.handleValidationExceptions(exception);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    assertEquals("Validation failed", response.getBody().getMessage());
    assertNotNull(response.getBody().getTimestamp());
    assertNotNull(response.getBody().getErrors());
    assertEquals(2, response.getBody().getErrors().size());
    assertTrue(response.getBody().getErrors().containsKey("institutionNameEn"));
    assertTrue(response.getBody().getErrors().containsKey("degreeEn"));
  }

  @Test
  @DisplayName("Should handle generic Exception")
  void testHandleGlobalException() {
    // Arrange
    Exception exception = new Exception("Unexpected error occurred");

    // Act
    ResponseEntity<ErrorResponse> response = exceptionHandler.handleGlobalException(exception);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    assertEquals("An unexpected error occurred", response.getBody().getMessage());
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  @DisplayName("Should handle RuntimeException as generic exception")
  void testHandleRuntimeException() {
    // Arrange
    RuntimeException exception = new RuntimeException("Runtime error");

    // Act
    ResponseEntity<ErrorResponse> response = exceptionHandler.handleGlobalException(exception);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An unexpected error occurred", response.getBody().getMessage());
  }

  @Test
  @DisplayName("Should handle NullPointerException as generic exception")
  void testHandleNullPointerException() {
    // Arrange
    NullPointerException exception = new NullPointerException("Null pointer error");

    // Act
    ResponseEntity<ErrorResponse> response = exceptionHandler.handleGlobalException(exception);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An unexpected error occurred", response.getBody().getMessage());
  }

  @Test
  @DisplayName("Should handle validation exception with multiple field errors")
  void testHandleValidationExceptions_MultipleErrors() throws NoSuchMethodException {
    // Arrange
    BeanPropertyBindingResult bindingResult =
        new BeanPropertyBindingResult(new Object(), "educationDTO");
    bindingResult.addError(new FieldError("educationDTO", "institutionNameEn", "Required"));
    bindingResult.addError(new FieldError("educationDTO", "institutionNameFr", "Required"));
    bindingResult.addError(new FieldError("educationDTO", "institutionNameEs", "Required"));
    bindingResult.addError(new FieldError("educationDTO", "startDate", "Required"));

    MethodParameter methodParameter =
        new MethodParameter(this.getClass().getDeclaredMethod("dummyMethod", String.class), 0);
    MethodArgumentNotValidException exception =
        new MethodArgumentNotValidException(methodParameter, bindingResult);

    // Act
    ResponseEntity<ErrorResponse> response = exceptionHandler.handleValidationExceptions(exception);

    // Assert
    assertNotNull(response);
    Map<String, String> errors = response.getBody().getErrors();
    assertEquals(4, errors.size());
    assertTrue(errors.containsKey("institutionNameEn"));
    assertTrue(errors.containsKey("institutionNameFr"));
    assertTrue(errors.containsKey("institutionNameEs"));
    assertTrue(errors.containsKey("startDate"));
  }

  // Dummy method for MethodParameter creation in tests
  @SuppressWarnings("unused")
  private void dummyMethod(String param) {}
}
