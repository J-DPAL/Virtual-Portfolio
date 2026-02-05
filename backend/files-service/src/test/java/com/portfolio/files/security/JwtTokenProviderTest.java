package com.portfolio.files.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtTokenProvider Tests")
class JwtTokenProviderTest {

  private JwtTokenProvider jwtTokenProvider;

  private static final String TEST_SECRET =
      "MySecretKeyForJwtTokenGenerationAndValidationPurposesOnlyForTesting123456";
  private static final long TEST_EXPIRATION = 3600000;

  @BeforeEach
  void setUp() {
    jwtTokenProvider = new JwtTokenProvider();
    ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", TEST_SECRET);
    ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", TEST_EXPIRATION);
  }

  @Test
  @DisplayName("Should generate token")
  void testGenerateToken() {
    String token = jwtTokenProvider.generateToken("test@example.com", "USER");

    assertNotNull(token);
    assertFalse(token.isBlank());
  }

  @Test
  @DisplayName("Should extract email from token")
  void testGetEmailFromToken() {
    String email = "test@example.com";
    String token = jwtTokenProvider.generateToken(email, "USER");

    assertEquals(email, jwtTokenProvider.getEmailFromToken(token));
  }

  @Test
  @DisplayName("Should extract role from token")
  void testGetRoleFromToken() {
    String role = "ADMIN";
    String token = jwtTokenProvider.generateToken("test@example.com", role);

    assertEquals(role, jwtTokenProvider.getRoleFromToken(token));
  }

  @Test
  @DisplayName("Should validate a valid token")
  void testValidateTokenValid() {
    String token = jwtTokenProvider.generateToken("test@example.com", "USER");

    assertTrue(jwtTokenProvider.validateToken(token));
  }

  @Test
  @DisplayName("Should reject invalid token")
  void testValidateTokenInvalid() {
    assertFalse(jwtTokenProvider.validateToken("invalid.token"));
  }

  @Test
  @DisplayName("Should reject empty token")
  void testValidateTokenEmpty() {
    assertFalse(jwtTokenProvider.validateToken(""));
  }

  @Test
  @DisplayName("Should reject null token")
  void testValidateTokenNull() {
    assertFalse(jwtTokenProvider.validateToken(null));
  }
}
