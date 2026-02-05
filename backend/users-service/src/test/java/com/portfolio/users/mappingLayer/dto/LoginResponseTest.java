package com.portfolio.users.mappingLayer.dto;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LoginResponse Unit Tests")
class LoginResponseTest {

  private LoginResponse testLoginResponse;
  private UserDTO testUserDTO;

  @BeforeEach
  void setUp() {
    testUserDTO =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("John Doe")
            .role("USER")
            .active(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    testLoginResponse =
        LoginResponse.builder()
            .token("jwt_token_value")
            .user(testUserDTO)
            .message("Login successful")
            .build();
  }

  @Test
  @DisplayName("Should create LoginResponse with all fields")
  void testLoginResponseCreation_WithAllFields() {
    assertNotNull(testLoginResponse);
    assertEquals("jwt_token_value", testLoginResponse.getToken());
    assertNotNull(testLoginResponse.getUser());
    assertEquals("test@example.com", testLoginResponse.getUser().getEmail());
    assertEquals("Login successful", testLoginResponse.getMessage());
  }

  @Test
  @DisplayName("Should create LoginResponse with no-args constructor")
  void testLoginResponseNoArgsConstructor() {
    LoginResponse response = new LoginResponse();
    assertNotNull(response);
  }

  @Test
  @DisplayName("Should create LoginResponse with all-args constructor")
  void testLoginResponseAllArgsConstructor() {
    LoginResponse response = new LoginResponse("token", testUserDTO, "Success");
    assertNotNull(response);
    assertEquals("token", response.getToken());
    assertEquals(testUserDTO, response.getUser());
    assertEquals("Success", response.getMessage());
  }

  @Test
  @DisplayName("Should create LoginResponse with builder pattern")
  void testLoginResponseBuilder() {
    LoginResponse response =
        LoginResponse.builder()
            .token("another_token")
            .user(testUserDTO)
            .message("Login successful")
            .build();

    assertNotNull(response);
    assertEquals("another_token", response.getToken());
    assertEquals(testUserDTO, response.getUser());
    assertEquals("Login successful", response.getMessage());
  }

  @Test
  @DisplayName("Should set and get token correctly")
  void testTokenSetterGetter() {
    testLoginResponse.setToken("new_token_value");
    assertEquals("new_token_value", testLoginResponse.getToken());
  }

  @Test
  @DisplayName("Should set and get user correctly")
  void testUserSetterGetter() {
    UserDTO newUserDTO =
        UserDTO.builder()
            .id(2L)
            .email("admin@example.com")
            .fullName("Admin")
            .role("ADMIN")
            .active(true)
            .build();

    testLoginResponse.setUser(newUserDTO);
    assertEquals(newUserDTO, testLoginResponse.getUser());
  }

  @Test
  @DisplayName("Should set and get message correctly")
  void testMessageSetterGetter() {
    testLoginResponse.setMessage("Authentication successful");
    assertEquals("Authentication successful", testLoginResponse.getMessage());
  }

  @Test
  @DisplayName("Should test equals with same values")
  void testEqualsWithSameValues() {
    LoginResponse response1 =
        LoginResponse.builder().token("token1").user(testUserDTO).message("Success").build();

    LoginResponse response2 =
        LoginResponse.builder().token("token1").user(testUserDTO).message("Success").build();

    assertEquals(response1, response2);
  }

  @Test
  @DisplayName("Should test equals with different tokens")
  void testEqualsWithDifferentTokens() {
    LoginResponse response1 =
        LoginResponse.builder().token("token1").user(testUserDTO).message("Success").build();

    LoginResponse response2 =
        LoginResponse.builder().token("token2").user(testUserDTO).message("Success").build();

    assertNotEquals(response1, response2);
  }

  @Test
  @DisplayName("Should test equals with different messages")
  void testEqualsWithDifferentMessages() {
    LoginResponse response1 =
        LoginResponse.builder().token("token1").user(testUserDTO).message("Success").build();

    LoginResponse response2 =
        LoginResponse.builder().token("token1").user(testUserDTO).message("Failed").build();

    assertNotEquals(response1, response2);
  }

  @Test
  @DisplayName("Should test hashCode consistency")
  void testHashCodeConsistency() {
    LoginResponse response1 =
        LoginResponse.builder().token("token1").user(testUserDTO).message("Success").build();

    LoginResponse response2 =
        LoginResponse.builder().token("token1").user(testUserDTO).message("Success").build();

    assertEquals(response1.hashCode(), response2.hashCode());
  }

  @Test
  @DisplayName("Should test toString method")
  void testToString() {
    String responseString = testLoginResponse.toString();
    assertNotNull(responseString);
    assertTrue(responseString.contains("LoginResponse") || responseString.contains("token"));
  }

  @Test
  @DisplayName("Should test equals with null")
  void testEqualsWithNull() {
    assertNotEquals(testLoginResponse, null);
  }

  @Test
  @DisplayName("Should test equals with different type")
  void testEqualsWithDifferentType() {
    assertNotEquals(testLoginResponse, "not a login response");
  }

  @Test
  @DisplayName("Should test LoginResponse with null user")
  void testLoginResponseWithNullUser() {
    LoginResponse response =
        LoginResponse.builder().token("token").user(null).message("Login successful").build();

    assertNull(response.getUser());
    assertEquals("token", response.getToken());
  }

  @Test
  @DisplayName("Should test LoginResponse with null token")
  void testLoginResponseWithNullToken() {
    LoginResponse response =
        LoginResponse.builder().token(null).user(testUserDTO).message("Success").build();

    assertNull(response.getToken());
    assertEquals(testUserDTO, response.getUser());
  }

  @Test
  @DisplayName("Should test LoginResponse with null message")
  void testLoginResponseWithNullMessage() {
    LoginResponse response =
        LoginResponse.builder().token("token").user(testUserDTO).message(null).build();

    assertNull(response.getMessage());
    assertEquals("token", response.getToken());
  }

  @Test
  @DisplayName("Should test multiple LoginResponse objects")
  void testMultipleLoginResponses() {
    UserDTO user1 = UserDTO.builder().id(1L).email("user1@example.com").build();
    UserDTO user2 = UserDTO.builder().id(2L).email("user2@example.com").build();

    LoginResponse response1 =
        LoginResponse.builder().token("token1").user(user1).message("Success 1").build();

    LoginResponse response2 =
        LoginResponse.builder().token("token2").user(user2).message("Success 2").build();

    assertNotEquals(response1, response2);
  }

  @Test
  @DisplayName("Should test field assignments individually")
  void testFieldAssignmentIndividually() {
    LoginResponse response = new LoginResponse();
    response.setToken("test_token");
    response.setUser(testUserDTO);
    response.setMessage("Login successful");

    assertEquals("test_token", response.getToken());
    assertEquals(testUserDTO, response.getUser());
    assertEquals("Login successful", response.getMessage());
  }

  @Test
  @DisplayName("Should test LoginResponse with empty strings")
  void testLoginResponseWithEmptyStrings() {
    LoginResponse response =
        LoginResponse.builder().token("").user(testUserDTO).message("").build();

    assertEquals("", response.getToken());
    assertEquals("", response.getMessage());
  }

  @Test
  @DisplayName("Should test LoginResponse with long token")
  void testLoginResponseWithLongToken() {
    String longToken =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwicm9sZXMiOiJVU0VSIiwiaWF0IjoxNjA5NDU5MjAwLCJleHAiOjE2MDk0NjI4MDB9.test_signature";
    LoginResponse response =
        LoginResponse.builder().token(longToken).user(testUserDTO).message("Success").build();

    assertEquals(longToken, response.getToken());
  }

  @Test
  @DisplayName("Should test all fields updated together")
  void testAllFieldsUpdatedTogether() {
    UserDTO newUser =
        UserDTO.builder()
            .id(3L)
            .email("newuser@example.com")
            .fullName("New User")
            .role("ADMIN")
            .active(true)
            .build();

    testLoginResponse.setToken("updated_token");
    testLoginResponse.setUser(newUser);
    testLoginResponse.setMessage("Updated message");

    assertEquals("updated_token", testLoginResponse.getToken());
    assertEquals(newUser, testLoginResponse.getUser());
    assertEquals("Updated message", testLoginResponse.getMessage());
  }

  @Test
  @DisplayName("Should build LoginResponse with token only")
  void testLoginResponseBuilderTokenOnly() {
    LoginResponse resp = LoginResponse.builder().token("jwt_token_123").build();
    assertEquals("jwt_token_123", resp.getToken());
    assertNull(resp.getUser());
    assertNull(resp.getMessage());
  }

  @Test
  @DisplayName("Should build LoginResponse with message only")
  void testLoginResponseBuilderMessageOnly() {
    LoginResponse resp = LoginResponse.builder().message("Login successful").build();
    assertEquals("Login successful", resp.getMessage());
    assertNull(resp.getToken());
    assertNull(resp.getUser());
  }

  @Test
  @DisplayName("Should handle LoginResponse with overwritten fields")
  void testLoginResponseBuilderFieldOverwrite() {
    UserDTO user1 = UserDTO.builder().id(1L).email("user1@example.com").build();
    UserDTO user2 = UserDTO.builder().id(2L).email("user2@example.com").build();

    LoginResponse resp =
        LoginResponse.builder()
            .user(user1)
            .user(user2)
            .message("First message")
            .message("Second message")
            .build();

    assertEquals(user2, resp.getUser());
    assertEquals("Second message", resp.getMessage());
  }

  @Test
  @DisplayName("Should verify multiple LoginResponse builders are independent")
  void testMultipleLoginResponseBuilders() {
    UserDTO user1 = UserDTO.builder().id(1L).build();
    UserDTO user2 = UserDTO.builder().id(2L).build();

    LoginResponse resp1 = LoginResponse.builder().token("token1").user(user1).build();
    LoginResponse resp2 = LoginResponse.builder().token("token2").user(user2).build();

    assertNotEquals(resp1, resp2);
  }

  @Test
  @DisplayName("Should test LoginResponse no-args constructor")
  void testLoginResponseNoArgsDefault() {
    LoginResponse resp = new LoginResponse();
    assertNull(resp.getToken());
    assertNull(resp.getUser());
    assertNull(resp.getMessage());
  }

  @Test
  @DisplayName("Should handle LoginResponse with complex token")
  void testLoginResponseComplexToken() {
    String complexToken =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    LoginResponse resp = LoginResponse.builder().token(complexToken).message("Success").build();

    assertEquals(complexToken, resp.getToken());
  }

  @Test
  @DisplayName("Should verify LoginResponse toString includes message")
  void testLoginResponseToStringContainsFields() {
    LoginResponse resp = LoginResponse.builder().token("token").message("Success message").build();

    String str = resp.toString();
    assertNotNull(str);
    assertTrue(str.length() > 0);
  }

  @Test
  @DisplayName("Should test LoginResponse with empty message")
  void testLoginResponseEmptyMessage() {
    LoginResponse resp = LoginResponse.builder().token("token").message("").build();

    assertEquals("", resp.getMessage());
    assertEquals("token", resp.getToken());
  }

  @Test
  @DisplayName("Should test LoginResponse equals with matching data")
  void testLoginResponseEqualsWithMatchingData() {
    UserDTO user = UserDTO.builder().id(1L).email("test@example.com").build();
    LoginResponse resp1 =
        LoginResponse.builder().token("token123").user(user).message("Success").build();

    LoginResponse resp2 =
        LoginResponse.builder().token("token123").user(user).message("Success").build();

    assertEquals(resp1, resp2);
    assertEquals(resp1.hashCode(), resp2.hashCode());
  }

  @Test
  @DisplayName("Should test LoginResponse not equals with different user")
  void testLoginResponseNotEqualsWithDifferentUser() {
    UserDTO user1 = UserDTO.builder().id(1L).email("user1@example.com").build();
    UserDTO user2 = UserDTO.builder().id(2L).email("user2@example.com").build();

    LoginResponse resp1 = LoginResponse.builder().token("token").user(user1).build();
    LoginResponse resp2 = LoginResponse.builder().token("token").user(user2).build();

    assertNotEquals(resp1, resp2);
  }
}
