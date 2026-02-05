package com.portfolio.users.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("SecurityConfig Unit Tests")
class SecurityConfigTest {

  @Autowired private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("Should provide PasswordEncoder bean")
  void testPasswordEncoderBeanExists() {
    // Assert: Verify PasswordEncoder bean is available
    assertNotNull(passwordEncoder);
  }

  @Test
  @DisplayName("Should encode password correctly")
  void testPasswordEncoder_EncodePassword_ReturnsEncodedPassword() {
    // Arrange
    String rawPassword = "testPassword123";

    // Act: Encode password
    String encodedPassword = passwordEncoder.encode(rawPassword);

    // Assert: Verify password is encoded and different from raw
    assertNotNull(encodedPassword);
    assertNotEquals(rawPassword, encodedPassword);
    assertTrue(encodedPassword.length() > rawPassword.length());
  }

  @Test
  @DisplayName("Should match encoded password with raw password")
  void testPasswordEncoder_MatchesPassword_ReturnsTrue() {
    // Arrange
    String rawPassword = "testPassword123";
    String encodedPassword = passwordEncoder.encode(rawPassword);

    // Act: Match password
    boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);

    // Assert: Verify password matches
    assertTrue(matches);
  }

  @Test
  @DisplayName("Should not match wrong password")
  void testPasswordEncoder_WrongPassword_ReturnsFalse() {
    // Arrange
    String rawPassword = "testPassword123";
    String wrongPassword = "wrongPassword456";
    String encodedPassword = passwordEncoder.encode(rawPassword);

    // Act: Match wrong password
    boolean matches = passwordEncoder.matches(wrongPassword, encodedPassword);

    // Assert: Verify password does not match
    assertFalse(matches);
  }

  @Test
  @DisplayName("Should encode empty password")
  void testPasswordEncoder_EncodeEmptyPassword_ReturnsEncodedPassword() {
    // Arrange
    String emptyPassword = "";

    // Act: Encode empty password
    String encodedPassword = passwordEncoder.encode(emptyPassword);

    // Assert: Verify encoding works with empty password
    assertNotNull(encodedPassword);
    assertTrue(passwordEncoder.matches(emptyPassword, encodedPassword));
  }

  @Test
  @DisplayName("Should handle special characters in password")
  void testPasswordEncoder_WithSpecialCharacters_EncodesProperly() {
    // Arrange
    String passwordWithSpecialChars = "p@ssw0rd!#$%^&*()";

    // Act: Encode password
    String encodedPassword = passwordEncoder.encode(passwordWithSpecialChars);

    // Assert: Verify encoding and matching works
    assertNotNull(encodedPassword);
    assertTrue(passwordEncoder.matches(passwordWithSpecialChars, encodedPassword));
  }

  @Test
  @DisplayName("Should handle long passwords")
  void testPasswordEncoder_WithLongPassword_EncodesProperly() {
    // Arrange
    String longPassword = "a".repeat(100);

    // Act: Encode password
    String encodedPassword = passwordEncoder.encode(longPassword);

    // Assert: Verify encoding works with long passwords
    assertNotNull(encodedPassword);
    assertTrue(passwordEncoder.matches(longPassword, encodedPassword));
  }

  @Test
  @DisplayName("Should encode same password differently each time (BCrypt)")
  void testPasswordEncoder_SamePwdEncodedDifferently() {
    // Arrange
    String password = "testPassword123";

    // Act: Encode same password twice
    String encoded1 = passwordEncoder.encode(password);
    String encoded2 = passwordEncoder.encode(password);

    // Assert: BCrypt should produce different hashes each time
    assertNotEquals(encoded1, encoded2);
    // But both should match the original password
    assertTrue(passwordEncoder.matches(password, encoded1));
    assertTrue(passwordEncoder.matches(password, encoded2));
  }

  @Test
  @DisplayName("Should not match empty password with non-empty encoded")
  void testPasswordEncoder_EmptyPasswordDoesNotMatchNonEmpty() {
    // Arrange
    String password = "testPassword123";
    String encodedPassword = passwordEncoder.encode(password);

    // Act: Try to match empty password
    boolean matches = passwordEncoder.matches("", encodedPassword);

    // Assert: Empty password should not match
    assertFalse(matches);
  }
}
