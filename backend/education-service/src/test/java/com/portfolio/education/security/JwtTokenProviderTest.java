package com.portfolio.education.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtTokenProvider Tests")
class JwtTokenProviderTest {

  private JwtTokenProvider jwtTokenProvider;
  private String testEmail;
  private String testRole;

  @BeforeEach
  void setUp() {
    jwtTokenProvider = new JwtTokenProvider();
    // Set the required properties using ReflectionTestUtils
    ReflectionTestUtils.setField(
        jwtTokenProvider, "jwtSecret", "mySecretKeyForJWTTokenGenerationAndValidationPurpose");
    ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", 86400000L); // 24 hours

    testEmail = "test@example.com";
    testRole = "USER";
  }

  @Test
  @DisplayName("Should generate JWT token successfully")
  void testGenerateToken() {
    // Act
    String token = jwtTokenProvider.generateToken(testEmail, testRole);

    // Assert
    assertNotNull(token);
    assertFalse(token.isEmpty());
    assertTrue(token.split("\\.").length == 3); // JWT has 3 parts
  }

  @Test
  @DisplayName("Should extract email from valid token")
  void testGetEmailFromToken() {
    // Arrange
    String token = jwtTokenProvider.generateToken(testEmail, testRole);

    // Act
    String email = jwtTokenProvider.getEmailFromToken(token);

    // Assert
    assertEquals(testEmail, email);
  }

  @Test
  @DisplayName("Should extract role from valid token")
  void testGetRoleFromToken() {
    // Arrange
    String token = jwtTokenProvider.generateToken(testEmail, testRole);

    // Act
    String role = jwtTokenProvider.getRoleFromToken(token);

    // Assert
    assertEquals(testRole, role);
  }

  @Test
  @DisplayName("Should validate valid token")
  void testValidateToken_ValidToken() {
    // Arrange
    String token = jwtTokenProvider.generateToken(testEmail, testRole);

    // Act
    boolean isValid = jwtTokenProvider.validateToken(token);

    // Assert
    assertTrue(isValid);
  }

  @Test
  @DisplayName("Should return false for invalid token")
  void testValidateToken_InvalidToken() {
    // Arrange
    String invalidToken = "invalid.token.here";

    // Act
    boolean isValid = jwtTokenProvider.validateToken(invalidToken);

    // Assert
    assertFalse(isValid);
  }

  @Test
  @DisplayName("Should return false for empty token")
  void testValidateToken_EmptyToken() {
    // Act
    boolean isValid = jwtTokenProvider.validateToken("");

    // Assert
    assertFalse(isValid);
  }

  @Test
  @DisplayName("Should return false for null token")
  void testValidateToken_NullToken() {
    // Act
    boolean isValid = jwtTokenProvider.validateToken(null);

    // Assert
    assertFalse(isValid);
  }

  @Test
  @DisplayName("Should generate different tokens for different emails")
  void testGenerateToken_DifferentEmails() {
    // Arrange
    String email1 = "user1@example.com";
    String email2 = "user2@example.com";

    // Act
    String token1 = jwtTokenProvider.generateToken(email1, testRole);
    String token2 = jwtTokenProvider.generateToken(email2, testRole);

    // Assert
    assertNotEquals(token1, token2);
  }

  @Test
  @DisplayName("Should generate different tokens for different roles")
  void testGenerateToken_DifferentRoles() {
    // Arrange
    String role1 = "USER";
    String role2 = "ADMIN";

    // Act
    String token1 = jwtTokenProvider.generateToken(testEmail, role1);
    String token2 = jwtTokenProvider.generateToken(testEmail, role2);

    // Assert
    assertNotEquals(token1, token2);
  }

  @Test
  @DisplayName("Should handle token generation with ADMIN role")
  void testGenerateToken_AdminRole() {
    // Arrange
    String adminRole = "ADMIN";

    // Act
    String token = jwtTokenProvider.generateToken(testEmail, adminRole);
    String role = jwtTokenProvider.getRoleFromToken(token);

    // Assert
    assertNotNull(token);
    assertEquals(adminRole, role);
  }

  @Test
  @DisplayName("Should handle token with special characters in email")
  void testGenerateToken_SpecialCharactersInEmail() {
    // Arrange
    String emailWithSpecialChars = "test.user+tag@example.com";

    // Act
    String token = jwtTokenProvider.generateToken(emailWithSpecialChars, testRole);
    String extractedEmail = jwtTokenProvider.getEmailFromToken(token);

    // Assert
    assertEquals(emailWithSpecialChars, extractedEmail);
  }
}
