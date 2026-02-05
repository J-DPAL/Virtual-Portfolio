package com.portfolio.skills.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

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
  void testGenerateToken() {
    String email = "test@example.com";
    String role = "ROLE_USER";

    String token = jwtTokenProvider.generateToken(email, role);

    assertNotNull(token);
    assertFalse(token.isEmpty());
    assertFalse(token.isBlank());
  }

  @Test
  void testGenerateTokenWithDifferentEmails() {
    String role = "ROLE_USER";
    String token1 = jwtTokenProvider.generateToken("user1@example.com", role);
    String token2 = jwtTokenProvider.generateToken("user2@example.com", role);

    assertNotEquals(token1, token2);
  }

  @Test
  void testGenerateTokenWithDifferentRoles() {
    String email = "test@example.com";
    String token1 = jwtTokenProvider.generateToken(email, "ROLE_USER");
    String token2 = jwtTokenProvider.generateToken(email, "ROLE_ADMIN");

    assertNotEquals(token1, token2);
  }

  @Test
  void testGetEmailFromToken() {
    String email = "extracted@example.com";
    String role = "ROLE_USER";
    String token = jwtTokenProvider.generateToken(email, role);

    String extractedEmail = jwtTokenProvider.getEmailFromToken(token);

    assertEquals(email, extractedEmail);
  }

  @Test
  void testGetRoleFromToken() {
    String email = "test@example.com";
    String role = "ROLE_ADMIN";
    String token = jwtTokenProvider.generateToken(email, role);

    String extractedRole = jwtTokenProvider.getRoleFromToken(token);

    assertEquals(role, extractedRole);
  }

  @Test
  void testValidateTokenValid() {
    String email = "test@example.com";
    String role = "ROLE_USER";
    String token = jwtTokenProvider.generateToken(email, role);

    boolean isValid = jwtTokenProvider.validateToken(token);

    assertTrue(isValid);
  }

  @Test
  void testValidateTokenInvalidFormat() {
    String invalidToken = "invalid.token.format";

    boolean isValid = jwtTokenProvider.validateToken(invalidToken);

    assertFalse(isValid);
  }

  @Test
  void testValidateTokenEmpty() {
    String emptyToken = "";

    boolean isValid = jwtTokenProvider.validateToken(emptyToken);

    assertFalse(isValid);
  }

  @Test
  void testValidateTokenNull() {
    boolean isValid = jwtTokenProvider.validateToken(null);

    assertFalse(isValid);
  }

  @Test
  void testGenerateTokenWithSpecialCharactersInEmail() {
    String email = "special+test.user@example.co.uk";
    String role = "ROLE_USER";

    String token = jwtTokenProvider.generateToken(email, role);
    String extractedEmail = jwtTokenProvider.getEmailFromToken(token);

    assertEquals(email, extractedEmail);
  }

  @Test
  void testGenerateTokenWithMultipleRoleTypes() {
    String email = "test@example.com";
    String token1 = jwtTokenProvider.generateToken(email, "ROLE_USER");
    String token2 = jwtTokenProvider.generateToken(email, "ROLE_ADMIN");
    String token3 = jwtTokenProvider.generateToken(email, "ROLE_MODERATOR");

    assertEquals("ROLE_USER", jwtTokenProvider.getRoleFromToken(token1));
    assertEquals("ROLE_ADMIN", jwtTokenProvider.getRoleFromToken(token2));
    assertEquals("ROLE_MODERATOR", jwtTokenProvider.getRoleFromToken(token3));
  }
}
