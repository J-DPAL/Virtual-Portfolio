package com.portfolio.skills.utils.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

  @Test
  void testConstructorWithMessage() {
    String message = "Resource not found";
    ResourceNotFoundException exception = new ResourceNotFoundException(message);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testConstructorWithDifferentMessages() {
    String message1 = "Skill with id 1 not found";
    String message2 = "User with email test@example.com not found";

    ResourceNotFoundException exception1 = new ResourceNotFoundException(message1);
    ResourceNotFoundException exception2 = new ResourceNotFoundException(message2);

    assertEquals(message1, exception1.getMessage());
    assertEquals(message2, exception2.getMessage());
    assertNotEquals(exception1.getMessage(), exception2.getMessage());
  }

  @Test
  void testExceptionInheritance() {
    String message = "Resource not found";
    ResourceNotFoundException exception = new ResourceNotFoundException(message);

    assertTrue(exception instanceof RuntimeException);
    assertTrue(exception instanceof Exception);
    assertTrue(exception instanceof Throwable);
  }

  @Test
  void testThrowableConstructor() {
    String message = "Resource not found";
    ResourceNotFoundException exception = new ResourceNotFoundException(message);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
    assertNull(exception.getCause());
  }

  @Test
  void testExceptionThrowingAndCatching() {
    String message = "Skill with id 999 not found";

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          throw new ResourceNotFoundException(message);
        });

    try {
      throw new ResourceNotFoundException(message);
    } catch (ResourceNotFoundException e) {
      assertEquals(message, e.getMessage());
      assertTrue(e instanceof RuntimeException);
    }
  }

  @Test
  void testEmptyMessageConstructor() {
    ResourceNotFoundException exception = new ResourceNotFoundException("");

    assertNotNull(exception);
    assertEquals("", exception.getMessage());
  }

  @Test
  void testNullMessageConstructor() {
    ResourceNotFoundException exception = new ResourceNotFoundException(null);

    assertNotNull(exception);
    assertNull(exception.getMessage());
  }

  @Test
  void testMultipleExceptionInstances() {
    ResourceNotFoundException exception1 = new ResourceNotFoundException("Error 1");
    ResourceNotFoundException exception2 = new ResourceNotFoundException("Error 2");
    ResourceNotFoundException exception3 = new ResourceNotFoundException("Error 1");

    assertNotEquals(exception1, exception2);
    assertNotEquals(exception1, exception3);
    assertEquals(exception1.getMessage(), exception3.getMessage());
  }
}
