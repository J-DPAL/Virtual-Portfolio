package com.portfolio.testimonials.utils.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

  @Test
  void testConstructor() {
    String message = "Testimonial not found";
    ResourceNotFoundException exception = new ResourceNotFoundException(message);

    assertEquals(message, exception.getMessage());
  }

  @Test
  void testInheritance() {
    ResourceNotFoundException exception = new ResourceNotFoundException("Test message");

    assertTrue(exception instanceof RuntimeException);
  }

  @Test
  void testThrowable() {
    String message = "Resource not found";
    ResourceNotFoundException exception = new ResourceNotFoundException(message);

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          throw exception;
        });
  }

  @Test
  void testExceptionWithDifferentMessages() {
    ResourceNotFoundException exception1 = new ResourceNotFoundException("Testimonial not found");
    ResourceNotFoundException exception2 = new ResourceNotFoundException("User not found");

    assertEquals("Testimonial not found", exception1.getMessage());
    assertEquals("User not found", exception2.getMessage());
    assertNotEquals(exception1.getMessage(), exception2.getMessage());
  }

  @Test
  void testExceptionThrowingAndCatching() {
    String expectedMessage = "Resource not found with ID: 123";

    try {
      throw new ResourceNotFoundException(expectedMessage);
    } catch (ResourceNotFoundException e) {
      assertEquals(expectedMessage, e.getMessage());
    }
  }
}
