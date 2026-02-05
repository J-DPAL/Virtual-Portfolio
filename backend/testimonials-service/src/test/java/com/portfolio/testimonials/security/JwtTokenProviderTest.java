package com.portfolio.testimonials.security;

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
    String role = "ADMIN";
    String token = jwtTokenProvider.generateToken(email, role);

    assertNotNull(token);
    assertFalse(token.isEmpty());
    assertTrue(token.contains("."));
  }

  @Test
  void testGetEmailFromToken() {
    String email = "test@example.com";
    String role = "ADMIN";
    String token = jwtTokenProvider.generateToken(email, role);
    String extractedEmail = jwtTokenProvider.getEmailFromToken(token);

    assertEquals(email, extractedEmail);
  }

  @Test
  void testGetRoleFromToken() {
    String email = "test@example.com";
    String role = "ADMIN";
    String token = jwtTokenProvider.generateToken(email, role);
    String extractedRole = jwtTokenProvider.getRoleFromToken(token);

    assertEquals(role, extractedRole);
  }

  @Test
  void testValidateToken_Valid() {
    String token = jwtTokenProvider.generateToken("test@example.com", "ADMIN");
    boolean isValid = jwtTokenProvider.validateToken(token);

    assertTrue(isValid);
  }

  @Test
  void testValidateToken_Invalid() {
    String invalidToken = "invalid.jwt.token";
    boolean isValid = jwtTokenProvider.validateToken(invalidToken);

    assertFalse(isValid);
  }

  @Test
  void testValidateToken_Null() {
    boolean isValid = jwtTokenProvider.validateToken(null);
    assertFalse(isValid);
  }

  @Test
  void testValidateToken_Empty() {
    boolean isValid = jwtTokenProvider.validateToken("");
    assertFalse(isValid);
  }

  @Test
  void testDifferentTokensForDifferentEmails() {
    String token1 = jwtTokenProvider.generateToken("user1@example.com", "ADMIN");
    String token2 = jwtTokenProvider.generateToken("user2@example.com", "ADMIN");

    assertNotEquals(token1, token2);
    assertEquals("user1@example.com", jwtTokenProvider.getEmailFromToken(token1));
    assertEquals("user2@example.com", jwtTokenProvider.getEmailFromToken(token2));
  }

  @Test
  void testDifferentTokensForDifferentRoles() {
    String token1 = jwtTokenProvider.generateToken("test@example.com", "ADMIN");
    String token2 = jwtTokenProvider.generateToken("test@example.com", "USER");

    assertNotEquals(token1, token2);
    assertEquals("ADMIN", jwtTokenProvider.getRoleFromToken(token1));
    assertEquals("USER", jwtTokenProvider.getRoleFromToken(token2));
  }

  @Test
  void testSpecialCharacterEmail() {
    String specialEmail = "user+test@example.co.uk";
    String token = jwtTokenProvider.generateToken(specialEmail, "ADMIN");
    String extractedEmail = jwtTokenProvider.getEmailFromToken(token);

    assertEquals(specialEmail, extractedEmail);
  }

  @Test
  void testMultipleRoles() {
    String roleWithUnderscore = "SUPER_ADMIN";
    String token = jwtTokenProvider.generateToken("test@example.com", roleWithUnderscore);
    String extractedRole = jwtTokenProvider.getRoleFromToken(token);

    assertEquals(roleWithUnderscore, extractedRole);
  }
}
