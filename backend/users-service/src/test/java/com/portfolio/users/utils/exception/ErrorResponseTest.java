package com.portfolio.users.utils.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ErrorResponse Unit Tests")
class ErrorResponseTest {

  private ErrorResponse testErrorResponse;

  @BeforeEach
  void setUp() {
    testErrorResponse =
        ErrorResponse.builder().message("An error occurred").status("ERROR").build();
  }

  @Test
  @DisplayName("Should create ErrorResponse with all fields")
  void testErrorResponseCreation_WithAllFields() {
    assertNotNull(testErrorResponse);
    assertEquals("An error occurred", testErrorResponse.getMessage());
    assertEquals("ERROR", testErrorResponse.getStatus());
  }

  @Test
  @DisplayName("Should create ErrorResponse with no-args constructor")
  void testErrorResponseNoArgsConstructor() {
    ErrorResponse response = new ErrorResponse();
    assertNotNull(response);
  }

  @Test
  @DisplayName("Should create ErrorResponse with all-args constructor")
  void testErrorResponseAllArgsConstructor() {
    ErrorResponse response = new ErrorResponse("Test message", "ERROR");
    assertNotNull(response);
    assertEquals("Test message", response.getMessage());
    assertEquals("ERROR", response.getStatus());
  }

  @Test
  @DisplayName("Should create ErrorResponse with builder pattern")
  void testErrorResponseBuilder() {
    ErrorResponse response =
        ErrorResponse.builder().message("Not Found").status("NOT_FOUND").build();

    assertNotNull(response);
    assertEquals("Not Found", response.getMessage());
    assertEquals("NOT_FOUND", response.getStatus());
  }

  @Test
  @DisplayName("Should set and get message correctly")
  void testMessageSetterGetter() {
    testErrorResponse.setMessage("New error message");
    assertEquals("New error message", testErrorResponse.getMessage());
  }

  @Test
  @DisplayName("Should set and get status correctly")
  void testStatusSetterGetter() {
    testErrorResponse.setStatus("FORBIDDEN");
    assertEquals("FORBIDDEN", testErrorResponse.getStatus());
  }

  @Test
  @DisplayName("Should test equals with same values")
  void testEqualsWithSameValues() {
    ErrorResponse response1 = ErrorResponse.builder().message("Error").status("ERROR").build();

    ErrorResponse response2 = ErrorResponse.builder().message("Error").status("ERROR").build();

    assertEquals(response1, response2);
  }

  @Test
  @DisplayName("Should test equals with different messages")
  void testEqualsWithDifferentMessages() {
    ErrorResponse response1 = ErrorResponse.builder().message("Error 1").status("ERROR").build();

    ErrorResponse response2 = ErrorResponse.builder().message("Error 2").status("ERROR").build();

    assertNotEquals(response1, response2);
  }

  @Test
  @DisplayName("Should test equals with different statuses")
  void testEqualsWithDifferentStatuses() {
    ErrorResponse response1 = ErrorResponse.builder().message("Error").status("ERROR").build();

    ErrorResponse response2 = ErrorResponse.builder().message("Error").status("FORBIDDEN").build();

    assertNotEquals(response1, response2);
  }

  @Test
  @DisplayName("Should test hashCode consistency")
  void testHashCodeConsistency() {
    ErrorResponse response1 = ErrorResponse.builder().message("Error").status("ERROR").build();

    ErrorResponse response2 = ErrorResponse.builder().message("Error").status("ERROR").build();

    assertEquals(response1.hashCode(), response2.hashCode());
  }

  @Test
  @DisplayName("Should test toString method")
  void testToString() {
    String responseString = testErrorResponse.toString();
    assertNotNull(responseString);
    assertTrue(responseString.contains("ErrorResponse") || responseString.contains("error"));
  }

  @Test
  @DisplayName("Should test equals with null")
  void testEqualsWithNull() {
    assertNotEquals(testErrorResponse, null);
  }

  @Test
  @DisplayName("Should test equals with different type")
  void testEqualsWithDifferentType() {
    assertNotEquals(testErrorResponse, "not an error response");
  }

  @Test
  @DisplayName("Should test multiple ErrorResponse objects")
  void testMultipleErrorResponses() {
    ErrorResponse response1 = ErrorResponse.builder().message("Error 1").status("ERROR").build();

    ErrorResponse response2 =
        ErrorResponse.builder().message("Error 2").status("NOT_FOUND").build();

    assertNotEquals(response1, response2);
  }

  @Test
  @DisplayName("Should test ErrorResponse with null message")
  void testErrorResponseWithNullMessage() {
    ErrorResponse response = ErrorResponse.builder().message(null).status("ERROR").build();

    assertNull(response.getMessage());
    assertEquals("ERROR", response.getStatus());
  }

  @Test
  @DisplayName("Should test ErrorResponse with null status")
  void testErrorResponseWithNullStatus() {
    ErrorResponse response = ErrorResponse.builder().message("Error").status(null).build();

    assertEquals("Error", response.getMessage());
    assertNull(response.getStatus());
  }

  @Test
  @DisplayName("Should test field assignments individually")
  void testFieldAssignmentIndividually() {
    ErrorResponse response = new ErrorResponse();
    response.setMessage("Test error");
    response.setStatus("TEST_ERROR");

    assertEquals("Test error", response.getMessage());
    assertEquals("TEST_ERROR", response.getStatus());
  }

  @Test
  @DisplayName("Should test ErrorResponse with empty strings")
  void testErrorResponseWithEmptyStrings() {
    ErrorResponse response = ErrorResponse.builder().message("").status("").build();

    assertEquals("", response.getMessage());
    assertEquals("", response.getStatus());
  }

  @Test
  @DisplayName("Should test ErrorResponse with various status codes")
  void testErrorResponseWithVariousStatusCodes() {
    String[] statuses = {
      "ERROR", "FORBIDDEN", "UNAUTHORIZED", "NOT_FOUND", "BAD_REQUEST", "INTERNAL_SERVER_ERROR"
    };

    for (String status : statuses) {
      ErrorResponse response =
          ErrorResponse.builder().message("Error message").status(status).build();
      assertEquals(status, response.getStatus());
    }
  }

  @Test
  @DisplayName("Should test ErrorResponse with long message")
  void testErrorResponseWithLongMessage() {
    String longMessage =
        "This is a very long error message that provides detailed information about what went wrong in the system.";
    ErrorResponse response = ErrorResponse.builder().message(longMessage).status("ERROR").build();

    assertEquals(longMessage, response.getMessage());
  }

  @Test
  @DisplayName("Should test all fields updated together")
  void testAllFieldsUpdatedTogether() {
    testErrorResponse.setMessage("Updated message");
    testErrorResponse.setStatus("UPDATED_STATUS");

    assertEquals("Updated message", testErrorResponse.getMessage());
    assertEquals("UPDATED_STATUS", testErrorResponse.getStatus());
  }

  @Test
  @DisplayName("Should test ErrorResponse equals with matching status and message")
  void testErrorResponseEqualsWithMatchingData() {
    ErrorResponse resp1 = ErrorResponse.builder().message("Error occurred").status("ERROR").build();

    ErrorResponse resp2 = ErrorResponse.builder().message("Error occurred").status("ERROR").build();

    assertEquals(resp1, resp2);
    assertEquals(resp1.hashCode(), resp2.hashCode());
  }

  @Test
  @DisplayName("Should test ErrorResponse with different statuses not equal")
  void testErrorResponseDifferentStatuses() {
    ErrorResponse resp1 = ErrorResponse.builder().message("Error").status("ERROR").build();

    ErrorResponse resp2 = ErrorResponse.builder().message("Error").status("FORBIDDEN").build();

    assertNotEquals(resp1, resp2);
  }

  @Test
  @DisplayName("Should test ErrorResponse toString is not empty")
  void testErrorResponseToStringNotEmpty() {
    ErrorResponse resp =
        ErrorResponse.builder().message("Test error message").status("NOT_FOUND").build();

    String str = resp.toString();
    assertNotNull(str);
    assertTrue(str.length() > 0);
  }
}
