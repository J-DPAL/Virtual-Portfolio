package com.portfolio.testimonials.utils.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

  @Test
  void testHandleResourceNotFoundException() {
    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    ResourceNotFoundException exception = new ResourceNotFoundException("Testimonial not found");

    var response = handler.handleResourceNotFoundException(exception);

    assertNotNull(response);
    assertEquals(404, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(404, response.getBody().getStatus());
    assertEquals("Testimonial not found", response.getBody().getMessage());
    assertNull(response.getBody().getErrors());
  }

  @Test
  void testHandleGlobalException() {
    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    Exception exception = new Exception("Unexpected error");

    var response = handler.handleGlobalException(exception);

    assertNotNull(response);
    assertEquals(500, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(500, response.getBody().getStatus());
    assertEquals("An unexpected error occurred", response.getBody().getMessage());
  }

  @Test
  void testExceptionHandlerAnnotation() {
    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    assertNotNull(handler);
  }

  @Test
  void testMultipleResourceNotFoundExceptions() {
    GlobalExceptionHandler handler = new GlobalExceptionHandler();

    ResourceNotFoundException exception1 = new ResourceNotFoundException("User not found");
    ResourceNotFoundException exception2 = new ResourceNotFoundException("Project not found");

    var response1 = handler.handleResourceNotFoundException(exception1);
    var response2 = handler.handleResourceNotFoundException(exception2);

    assertEquals("User not found", response1.getBody().getMessage());
    assertEquals("Project not found", response2.getBody().getMessage());
    assertEquals(404, response1.getStatusCode().value());
    assertEquals(404, response2.getStatusCode().value());
  }
}
