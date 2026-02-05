package com.portfolio.hobbies.utils.exceptions;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

  @InjectMocks private GlobalExceptionHandler globalExceptionHandler;

  @BeforeEach
  void setUp() {}

  @Test
  void testHandleResourceNotFoundException() {
    String errorMessage = "Resource not found";
    ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

    ResponseEntity<ErrorResponse> response =
        globalExceptionHandler.handleResourceNotFoundException(exception);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(404, response.getBody().getStatus());
    assertEquals(errorMessage, response.getBody().getMessage());
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  void testHandleResourceNotFoundExceptionWithDifferentMessage() {
    String errorMessage = "Hobby with id 999 not found";
    ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

    ResponseEntity<ErrorResponse> response =
        globalExceptionHandler.handleResourceNotFoundException(exception);

    assertNotNull(response.getBody());
    assertEquals(404, response.getBody().getStatus());
    assertEquals(errorMessage, response.getBody().getMessage());
  }

  @Test
  void testHandleMethodArgumentNotValidException() {
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    when(exception.getBindingResult())
        .thenReturn(mock(org.springframework.validation.BindingResult.class));
    when(exception.getBindingResult().getAllErrors()).thenReturn(java.util.Collections.emptyList());

    ResponseEntity<ErrorResponse> response =
        globalExceptionHandler.handleValidationExceptions(exception);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(400, response.getBody().getStatus());
    assertNotNull(response.getBody().getMessage());
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  void testHandleGlobalException() {
    String errorMessage = "An unexpected error occurred";
    Exception exception = new Exception(errorMessage);

    ResponseEntity<ErrorResponse> response =
        globalExceptionHandler.handleGlobalException(exception);

    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(500, response.getBody().getStatus());
    assertNotNull(response.getBody().getMessage());
    assertNotNull(response.getBody().getTimestamp());
  }

  @Test
  void testHandleGlobalExceptionWithDifferentMessage() {
    String errorMessage = "Database connection failed";
    Exception exception = new Exception(errorMessage);

    ResponseEntity<ErrorResponse> response =
        globalExceptionHandler.handleGlobalException(exception);

    assertNotNull(response.getBody());
    assertEquals(500, response.getBody().getStatus());
    assertNotNull(response.getBody().getMessage());
  }

  @Test
  void testAllExceptionHandlersReturnErrorResponse() {
    String message1 = "Not found";
    ResourceNotFoundException exception1 = new ResourceNotFoundException(message1);
    ResponseEntity<ErrorResponse> response1 =
        globalExceptionHandler.handleResourceNotFoundException(exception1);

    String message2 = "Server error";
    Exception exception2 = new Exception(message2);
    ResponseEntity<ErrorResponse> response2 =
        globalExceptionHandler.handleGlobalException(exception2);

    assertNotNull(response1.getBody());
    assertNotNull(response2.getBody());
    assertEquals(404, response1.getBody().getStatus());
    assertEquals(500, response2.getBody().getStatus());
    assertTrue(response1.getBody().getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
    assertTrue(response2.getBody().getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
  }
}
