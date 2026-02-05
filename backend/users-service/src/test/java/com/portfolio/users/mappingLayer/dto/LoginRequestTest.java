package com.portfolio.users.mappingLayer.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LoginRequest Unit Tests")
class LoginRequestTest {

  private LoginRequest testLoginRequest;

  @BeforeEach
  void setUp() {
    testLoginRequest =
        LoginRequest.builder().email("test@example.com").password("password123").build();
  }

  @Test
  @DisplayName("Should create LoginRequest with all fields")
  void testLoginRequestCreation_WithAllFields() {
    assertNotNull(testLoginRequest);
    assertEquals("test@example.com", testLoginRequest.getEmail());
    assertEquals("password123", testLoginRequest.getPassword());
  }

  @Test
  @DisplayName("Should create LoginRequest with no-args constructor")
  void testLoginRequestNoArgsConstructor() {
    LoginRequest request = new LoginRequest();
    assertNotNull(request);
  }

  @Test
  @DisplayName("Should create LoginRequest with all-args constructor")
  void testLoginRequestAllArgsConstructor() {
    LoginRequest request = new LoginRequest("test@example.com", "password123");
    assertNotNull(request);
    assertEquals("test@example.com", request.getEmail());
    assertEquals("password123", request.getPassword());
  }

  @Test
  @DisplayName("Should create LoginRequest with builder pattern")
  void testLoginRequestBuilder() {
    LoginRequest request =
        LoginRequest.builder().email("admin@example.com").password("securePassword123").build();

    assertNotNull(request);
    assertEquals("admin@example.com", request.getEmail());
    assertEquals("securePassword123", request.getPassword());
  }

  @Test
  @DisplayName("Should set and get email correctly")
  void testEmailSetterGetter() {
    testLoginRequest.setEmail("newemail@example.com");
    assertEquals("newemail@example.com", testLoginRequest.getEmail());
  }

  @Test
  @DisplayName("Should set and get password correctly")
  void testPasswordSetterGetter() {
    testLoginRequest.setPassword("newPassword456");
    assertEquals("newPassword456", testLoginRequest.getPassword());
  }

  @Test
  @DisplayName("Should test equals with same values")
  void testEqualsWithSameValues() {
    LoginRequest request1 =
        LoginRequest.builder().email("test@example.com").password("password123").build();

    LoginRequest request2 =
        LoginRequest.builder().email("test@example.com").password("password123").build();

    assertEquals(request1, request2);
  }

  @Test
  @DisplayName("Should test equals with different emails")
  void testEqualsWithDifferentEmails() {
    LoginRequest request1 =
        LoginRequest.builder().email("test1@example.com").password("password123").build();

    LoginRequest request2 =
        LoginRequest.builder().email("test2@example.com").password("password123").build();

    assertNotEquals(request1, request2);
  }

  @Test
  @DisplayName("Should test equals with different passwords")
  void testEqualsWithDifferentPasswords() {
    LoginRequest request1 =
        LoginRequest.builder().email("test@example.com").password("password1").build();

    LoginRequest request2 =
        LoginRequest.builder().email("test@example.com").password("password2").build();

    assertNotEquals(request1, request2);
  }

  @Test
  @DisplayName("Should test hashCode consistency")
  void testHashCodeConsistency() {
    LoginRequest request1 =
        LoginRequest.builder().email("test@example.com").password("password123").build();

    LoginRequest request2 =
        LoginRequest.builder().email("test@example.com").password("password123").build();

    assertEquals(request1.hashCode(), request2.hashCode());
  }

  @Test
  @DisplayName("Should test toString method")
  void testToString() {
    String requestString = testLoginRequest.toString();
    assertNotNull(requestString);
    assertTrue(
        requestString.contains("LoginRequest") || requestString.contains("test@example.com"));
  }

  @Test
  @DisplayName("Should test equals with null")
  void testEqualsWithNull() {
    assertNotEquals(testLoginRequest, null);
  }

  @Test
  @DisplayName("Should test equals with different type")
  void testEqualsWithDifferentType() {
    assertNotEquals(testLoginRequest, "not a login request");
  }

  @Test
  @DisplayName("Should test multiple LoginRequest objects")
  void testMultipleLoginRequests() {
    LoginRequest request1 =
        LoginRequest.builder().email("user1@example.com").password("pass1").build();

    LoginRequest request2 =
        LoginRequest.builder().email("user2@example.com").password("pass2").build();

    assertNotEquals(request1, request2);
  }

  @Test
  @DisplayName("Should test LoginRequest with special characters in password")
  void testLoginRequestWithSpecialCharactersInPassword() {
    LoginRequest request =
        LoginRequest.builder().email("test@example.com").password("p@ssw0rd!@#$%").build();

    assertEquals("p@ssw0rd!@#$%", request.getPassword());
  }

  @Test
  @DisplayName("Should test LoginRequest with valid email formats")
  void testLoginRequestWithValidEmailFormats() {
    LoginRequest request1 =
        LoginRequest.builder().email("user.name@example.com").password("password").build();
    assertEquals("user.name@example.com", request1.getEmail());

    LoginRequest request2 =
        LoginRequest.builder().email("user+tag@example.co.uk").password("password").build();
    assertEquals("user+tag@example.co.uk", request2.getEmail());
  }

  @Test
  @DisplayName("Should test field assignments individually")
  void testFieldAssignmentIndividually() {
    LoginRequest request = new LoginRequest();
    request.setEmail("test@example.com");
    request.setPassword("password123");

    assertEquals("test@example.com", request.getEmail());
    assertEquals("password123", request.getPassword());
  }

  @Test
  @DisplayName("Should test LoginRequest with null email")
  void testLoginRequestWithNullEmail() {
    LoginRequest request = new LoginRequest();
    request.setEmail(null);
    request.setPassword("password123");

    assertNull(request.getEmail());
    assertEquals("password123", request.getPassword());
  }

  @Test
  @DisplayName("Should test LoginRequest with null password")
  void testLoginRequestWithNullPassword() {
    LoginRequest request = new LoginRequest();
    request.setEmail("test@example.com");
    request.setPassword(null);

    assertEquals("test@example.com", request.getEmail());
    assertNull(request.getPassword());
  }

  @Test
  @DisplayName("Should test LoginRequest with empty strings")
  void testLoginRequestWithEmptyStrings() {
    LoginRequest request = LoginRequest.builder().email("").password("").build();

    assertEquals("", request.getEmail());
    assertEquals("", request.getPassword());
  }

  @Test
  @DisplayName("Should build LoginRequest with email only")
  void testLoginRequestBuilderEmailOnly() {
    LoginRequest req = LoginRequest.builder().email("test@example.com").build();
    assertEquals("test@example.com", req.getEmail());
    assertNull(req.getPassword());
  }

  @Test
  @DisplayName("Should build LoginRequest with password only")
  void testLoginRequestBuilderPasswordOnly() {
    LoginRequest req = LoginRequest.builder().password("password123").build();
    assertEquals("password123", req.getPassword());
    assertNull(req.getEmail());
  }

  @Test
  @DisplayName("Should handle LoginRequest with overwritten fields")
  void testLoginRequestBuilderFieldOverwrite() {
    LoginRequest req =
        LoginRequest.builder()
            .email("original@example.com")
            .email("new@example.com")
            .password("original")
            .password("newpassword")
            .build();

    assertEquals("new@example.com", req.getEmail());
    assertEquals("newpassword", req.getPassword());
  }

  @Test
  @DisplayName("Should verify multiple LoginRequest builders are independent")
  void testMultipleLoginRequestBuilders() {
    LoginRequest req1 = LoginRequest.builder().email("user1@example.com").password("pass1").build();
    LoginRequest req2 = LoginRequest.builder().email("user2@example.com").password("pass2").build();

    assertNotEquals(req1, req2);
    assertNotEquals(req1.getEmail(), req2.getEmail());
  }

  @Test
  @DisplayName("Should handle LoginRequest with very long password")
  void testLoginRequestLongPassword() {
    String longPassword = "a".repeat(1000);
    LoginRequest req =
        LoginRequest.builder().email("test@example.com").password(longPassword).build();

    assertEquals(longPassword, req.getPassword());
    assertEquals(1000, req.getPassword().length());
  }

  @Test
  @DisplayName("Should test LoginRequest no-args constructor")
  void testLoginRequestNoArgsDefault() {
    LoginRequest req = new LoginRequest();
    assertNull(req.getEmail());
    assertNull(req.getPassword());
  }

  @Test
  @DisplayName("Should handle LoginRequest with unicode characters")
  void testLoginRequestUnicodeEmail() {
    LoginRequest req =
        LoginRequest.builder().email("user+é@example.com").password("pässwörd").build();

    assertEquals("user+é@example.com", req.getEmail());
    assertEquals("pässwörd", req.getPassword());
  }

  @Test
  @DisplayName("Should verify LoginRequest toString includes email")
  void testLoginRequestToStringContainsFields() {
    LoginRequest req = LoginRequest.builder().email("test@example.com").password("secret").build();

    String str = req.toString();
    assertNotNull(str);
    assertTrue(str.contains("email") || str.contains("test@example.com"));
  }

  @Test
  @DisplayName("Should test LoginRequest equals with matching data")
  void testLoginRequestEqualsWithMatchingData() {
    LoginRequest req1 =
        LoginRequest.builder().email("test@example.com").password("password123").build();

    LoginRequest req2 =
        LoginRequest.builder().email("test@example.com").password("password123").build();

    assertEquals(req1, req2);
    assertEquals(req1.hashCode(), req2.hashCode());
  }

  @Test
  @DisplayName("Should test LoginRequest not equals with different email")
  void testLoginRequestNotEqualsWithDifferentEmail() {
    LoginRequest req1 =
        LoginRequest.builder().email("user1@example.com").password("password123").build();

    LoginRequest req2 =
        LoginRequest.builder().email("user2@example.com").password("password123").build();

    assertNotEquals(req1, req2);
  }
}
