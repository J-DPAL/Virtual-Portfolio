package com.portfolio.experience.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtTokenProvider Tests")
class JwtTokenProviderTest {

  private JwtTokenProvider jwtTokenProvider;

  @BeforeEach
  void setUp() {
    jwtTokenProvider = new JwtTokenProvider();
    ReflectionTestUtils.setField(
        jwtTokenProvider, "jwtSecret", "test-secret-key-12345678901234567890123456789012");
    ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", 3600000L);
  }

  @Test
  @DisplayName("Should generate token with email and role")
  void testGenerateToken() {
    String token = jwtTokenProvider.generateToken("test@example.com", "USER");

    assertNotNull(token);
    assertTrue(token.split("\\.").length >= 3);
  }

  @Test
  @DisplayName("Should extract email from token")
  void testGetEmailFromToken() {
    String token = jwtTokenProvider.generateToken("test@example.com", "USER");

    String email = jwtTokenProvider.getEmailFromToken(token);

    assertEquals("test@example.com", email);
  }

  @Test
  @DisplayName("Should extract role from token")
  void testGetRoleFromToken() {
    String token = jwtTokenProvider.generateToken("test@example.com", "ADMIN");

    String role = jwtTokenProvider.getRoleFromToken(token);

    assertEquals("ADMIN", role);
  }

  @Test
  @DisplayName("Should validate valid token")
  void testValidateToken_Valid() {
    String token = jwtTokenProvider.generateToken("test@example.com", "USER");

    assertTrue(jwtTokenProvider.validateToken(token));
  }

  @Test
  @DisplayName("Should return false for invalid token")
  void testValidateToken_Invalid() {
    assertFalse(jwtTokenProvider.validateToken("invalid.token.value"));
  }

  @Test
  @DisplayName("Should return false for null token")
  void testValidateToken_Null() {
    assertFalse(jwtTokenProvider.validateToken(null));
  }

  @Test
  @DisplayName("Should return false for empty token")
  void testValidateToken_Empty() {
    assertFalse(jwtTokenProvider.validateToken(""));
  }

  @Test
  @DisplayName("Should generate different tokens for different emails")
  void testDifferentTokensForDifferentEmails() {
    String token1 = jwtTokenProvider.generateToken("one@example.com", "USER");
    String token2 = jwtTokenProvider.generateToken("two@example.com", "USER");

    assertNotEquals(token1, token2);
  }

  @Test
  @DisplayName("Should generate different tokens for different roles")
  void testDifferentTokensForDifferentRoles() {
    String token1 = jwtTokenProvider.generateToken("test@example.com", "USER");
    String token2 = jwtTokenProvider.generateToken("test@example.com", "ADMIN");

    assertNotEquals(token1, token2);
  }

  @Test
  @DisplayName("Should handle special characters in email")
  void testSpecialCharacterEmail() {
    String token = jwtTokenProvider.generateToken("test.user+tag@example.com", "USER");

    assertEquals("test.user+tag@example.com", jwtTokenProvider.getEmailFromToken(token));
  }
}
