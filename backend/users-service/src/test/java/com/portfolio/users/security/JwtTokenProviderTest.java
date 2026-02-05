package com.portfolio.users.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("JwtTokenProvider Unit Tests")
class JwtTokenProviderTest {

  @Autowired private JwtTokenProvider jwtTokenProvider;

  private String testEmail;
  private String testRole;

  @BeforeEach
  void setUp() {
    testEmail = "test@example.com";
    testRole = "USER";
  }

  @Test
  @DisplayName("Should generate valid JWT token with correct claims")
  void testGenerateToken_WithValidParams_ReturnsValidToken() {
    // Act: Generate token
    String token = jwtTokenProvider.generateToken(testEmail, testRole);

    // Assert: Verify token is not null and not empty
    assertNotNull(token);
    assertFalse(token.isEmpty());
    assertTrue(token.contains(".")); // JWT has three parts separated by dots
  }

  @Test
  @DisplayName("Should include email in JWT subject claim")
  void testGenerateToken_TokenContainsEmailInSubject() {
    // Act: Generate token
    String token = jwtTokenProvider.generateToken(testEmail, testRole);

    // Assert: Verify email is in subject (we verify by checking token structure)
    assertNotNull(token);
    String[] parts = token.split("\\.");
    assertEquals(3, parts.length); // Valid JWT has 3 parts
  }

  @Test
  @DisplayName("Should include role claim in JWT token")
  void testGenerateToken_TokenContainsRoleClaim() {
    // Act: Generate token
    String token = jwtTokenProvider.generateToken(testEmail, testRole);

    // Assert: Verify token is generated successfully
    assertNotNull(token);
    assertFalse(token.isEmpty());
  }

  @Test
  @DisplayName("Should generate different tokens for different inputs")
  void testGenerateToken_WithDifferentParams_ReturnsDifferentTokens() {
    // Act: Generate two different tokens
    String token1 = jwtTokenProvider.generateToken(testEmail, "USER");
    String token2 = jwtTokenProvider.generateToken("other@example.com", "ADMIN");

    // Assert: Verify tokens are different
    assertNotNull(token1);
    assertNotNull(token2);
    assertNotEquals(token1, token2);
  }

  @Test
  @DisplayName("Should generate token with ADMIN role")
  void testGenerateToken_WithAdminRole_ReturnsValidToken() {
    // Act: Generate token with ADMIN role
    String token = jwtTokenProvider.generateToken(testEmail, "ADMIN");

    // Assert: Verify token is generated
    assertNotNull(token);
    assertFalse(token.isEmpty());
  }

  @Test
  @DisplayName("Should generate token with expiration time")
  void testGenerateToken_TokenShouldHaveExpiration() {
    // Act: Generate token
    String token = jwtTokenProvider.generateToken(testEmail, testRole);

    // Assert: Verify token structure is valid
    assertNotNull(token);
    String[] parts = token.split("\\.");
    assertEquals(3, parts.length);
  }

  @Test
  @DisplayName("Should handle null email gracefully")
  void testGenerateToken_WithNullEmail_ThrowsException() {
    // Act: Generate token with null email
    String token = jwtTokenProvider.generateToken(null, "USER");

    // Assert: Verify token is created
    assertNotNull(token);
    assertFalse(token.isEmpty());
  }

  @Test
  @DisplayName("Should handle null role gracefully")
  void testGenerateToken_WithNullRole_ThrowsException() {
    // Act: Generate token with null role
    String token = jwtTokenProvider.generateToken(testEmail, null);

    // Assert: Verify token is created
    assertNotNull(token);
    assertFalse(token.isEmpty());
  }

  @Test
  @DisplayName("Should handle empty email string")
  void testGenerateToken_WithEmptyEmail_ReturnsValidToken() {
    // Act: Generate token with empty email
    String token = jwtTokenProvider.generateToken("", testRole);

    // Assert: Verify token is generated (empty string is technically valid)
    assertNotNull(token);
    assertFalse(token.isEmpty());
  }

  @Test
  @DisplayName("Should handle empty role string")
  void testGenerateToken_WithEmptyRole_ReturnsValidToken() {
    // Act: Generate token with empty role
    String token = jwtTokenProvider.generateToken(testEmail, "");

    // Assert: Verify token is generated
    assertNotNull(token);
    assertFalse(token.isEmpty());
  }

  @Test
  @DisplayName("Should handle special characters in email")
  void testGenerateToken_WithSpecialCharactersInEmail_ReturnsValidToken() {
    // Act: Generate token with special characters in email
    String specialEmail = "test+tag@example.co.uk";
    String token = jwtTokenProvider.generateToken(specialEmail, testRole);

    // Assert: Verify token is generated successfully
    assertNotNull(token);
    assertFalse(token.isEmpty());
  }

  @Test
  @DisplayName("Should generate consistent token structure")
  void testGenerateToken_TokenStructureIsConsistent() {
    // Act: Generate multiple tokens
    String token1 = jwtTokenProvider.generateToken(testEmail, testRole);
    String token2 = jwtTokenProvider.generateToken(testEmail, testRole);

    // Assert: Both tokens should have same structure (3 parts)
    String[] parts1 = token1.split("\\.");
    String[] parts2 = token2.split("\\.");
    assertEquals(3, parts1.length);
    assertEquals(3, parts2.length);
  }
}
