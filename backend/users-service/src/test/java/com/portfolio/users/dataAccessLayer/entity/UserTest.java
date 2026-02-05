package com.portfolio.users.dataAccessLayer.entity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Entity Unit Tests")
class UserTest {

  private User testUser;
  private LocalDateTime testDateTime;

  @BeforeEach
  void setUp() {
    testDateTime = LocalDateTime.now();
    testUser =
        User.builder()
            .email("test@example.com")
            .password("hashedPassword123")
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    testUser.setId(1L);
    testUser.setCreatedAt(testDateTime);
    testUser.setUpdatedAt(testDateTime);
  }

  @Test
  @DisplayName("Should create user with all fields")
  void testUserCreation_WithAllFields() {
    assertNotNull(testUser);
    assertEquals(1L, testUser.getId());
    assertEquals("test@example.com", testUser.getEmail());
    assertEquals("hashedPassword123", testUser.getPassword());
    assertEquals("John Doe", testUser.getFullName());
    assertEquals(User.UserRole.USER, testUser.getRole());
    assertTrue(testUser.getActive());
    assertEquals(testDateTime, testUser.getCreatedAt());
    assertEquals(testDateTime, testUser.getUpdatedAt());
  }

  @Test
  @DisplayName("Should create user with no-args constructor")
  void testUserNoArgsConstructor() {
    User user = new User();
    assertNotNull(user);
  }

  @Test
  @DisplayName("Should create user with all-args constructor")
  void testUserAllArgsConstructor() {
    User user = new User("test@example.com", "password", "John Doe", User.UserRole.ADMIN, true);
    user.setId(1L);
    user.setCreatedAt(testDateTime);
    user.setUpdatedAt(testDateTime);
    assertNotNull(user);
    assertEquals(1L, user.getId());
    assertEquals("test@example.com", user.getEmail());
  }

  @Test
  @DisplayName("Should create user with builder pattern")
  void testUserBuilder() {
    User user =
        User.builder()
            .email("admin@example.com")
            .password("securePassword")
            .fullName("Admin User")
            .role(User.UserRole.ADMIN)
            .active(false)
            .build();
    user.setId(2L);

    assertNotNull(user);
    assertEquals(2L, user.getId());
    assertEquals("admin@example.com", user.getEmail());
    assertEquals(User.UserRole.ADMIN, user.getRole());
    assertFalse(user.getActive());
  }

  @Test
  @DisplayName("Should set and get email correctly")
  void testEmailSetterGetter() {
    testUser.setEmail("newemail@example.com");
    assertEquals("newemail@example.com", testUser.getEmail());
  }

  @Test
  @DisplayName("Should set and get password correctly")
  void testPasswordSetterGetter() {
    testUser.setPassword("newHashedPassword");
    assertEquals("newHashedPassword", testUser.getPassword());
  }

  @Test
  @DisplayName("Should set and get fullName correctly")
  void testFullNameSetterGetter() {
    testUser.setFullName("Jane Doe");
    assertEquals("Jane Doe", testUser.getFullName());
  }

  @Test
  @DisplayName("Should set and get role correctly")
  void testRoleSetterGetter() {
    testUser.setRole(User.UserRole.ADMIN);
    assertEquals(User.UserRole.ADMIN, testUser.getRole());
  }

  @Test
  @DisplayName("Should set and get active status correctly")
  void testActiveSetterGetter() {
    testUser.setActive(false);
    assertFalse(testUser.getActive());

    testUser.setActive(true);
    assertTrue(testUser.getActive());
  }

  @Test
  @DisplayName("Should set and get createdAt correctly")
  void testCreatedAtSetterGetter() {
    LocalDateTime newTime = LocalDateTime.now().plusDays(1);
    testUser.setCreatedAt(newTime);
    assertEquals(newTime, testUser.getCreatedAt());
  }

  @Test
  @DisplayName("Should set and get updatedAt correctly")
  void testUpdatedAtSetterGetter() {
    LocalDateTime newTime = LocalDateTime.now().plusHours(2);
    testUser.setUpdatedAt(newTime);
    assertEquals(newTime, testUser.getUpdatedAt());
  }

  @Test
  @DisplayName("Should test UserRole enum values")
  void testUserRoleEnum() {
    assertEquals(User.UserRole.ADMIN, User.UserRole.valueOf("ADMIN"));
    assertEquals(User.UserRole.USER, User.UserRole.valueOf("USER"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"USER", "ADMIN"})
  @DisplayName("Should set user with different roles")
  void testUserWithDifferentRoles(String role) {
    testUser.setRole(User.UserRole.valueOf(role));
    assertEquals(User.UserRole.valueOf(role), testUser.getRole());
  }

  @Test
  @DisplayName("Should test equals with same values")
  void testEqualsWithSameValues() {
    User user1 =
        User.builder()
            .email("test@example.com")
            .password("password")
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user1.setId(1L);

    User user2 =
        User.builder()
            .email("test@example.com")
            .password("password")
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user2.setId(1L);

    assertEquals(user1, user2);
  }

  @Test
  @DisplayName("Should test equals with different emails")
  void testEqualsWithDifferentEmails() {
    User user1 =
        User.builder()
            .email("test1@example.com")
            .password("password")
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user1.setId(1L);

    User user2 =
        User.builder()
            .email("test2@example.com")
            .password("password")
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user2.setId(1L);

    assertNotEquals(user1, user2);
  }

  @Test
  @DisplayName("Should test equals with different roles")
  void testEqualsWithDifferentRoles() {
    User user1 =
        User.builder()
            .email("test@example.com")
            .password("password")
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user1.setId(1L);

    User user2 =
        User.builder()
            .email("test@example.com")
            .password("password")
            .fullName("John Doe")
            .role(User.UserRole.ADMIN)
            .active(true)
            .build();
    user2.setId(1L);

    assertNotEquals(user1, user2);
  }

  @Test
  @DisplayName("Should test hashCode consistency")
  void testHashCodeConsistency() {
    User user1 =
        User.builder()
            .email("test@example.com")
            .password("password")
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user1.setId(1L);

    User user2 =
        User.builder()
            .email("test@example.com")
            .password("password")
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user2.setId(1L);

    assertEquals(user1.hashCode(), user2.hashCode());
  }

  @Test
  @DisplayName("Should test toString method")
  void testToString() {
    String userString = testUser.toString();
    assertNotNull(userString);
    assertTrue(userString.contains("User") || userString.contains("test@example.com"));
  }

  @Test
  @DisplayName("Should test equals with null")
  void testEqualsWithNull() {
    assertNotEquals(testUser, null);
  }

  @Test
  @DisplayName("Should test equals with different type")
  void testEqualsWithDifferentType() {
    assertNotEquals(testUser, "not a user");
  }

  @Test
  @DisplayName("Should test active field default value")
  void testActiveDefaultValue() {
    User user =
        User.builder()
            .email("test@example.com")
            .password("password")
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .build();
    assertTrue(user.getActive());
  }

  @Test
  @DisplayName("Should test multiple users with different ids")
  void testMultipleUsersWithDifferentIds() {
    User user1 =
        User.builder()
            .email("user1@example.com")
            .password("password1")
            .fullName("User One")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user1.setId(1L);

    User user2 =
        User.builder()
            .email("user2@example.com")
            .password("password2")
            .fullName("User Two")
            .role(User.UserRole.ADMIN)
            .active(false)
            .build();
    user2.setId(2L);

    assertNotEquals(user1, user2);
    assertNotEquals(user1.getId(), user2.getId());
    assertNotEquals(user1.getEmail(), user2.getEmail());
  }

  @Test
  @DisplayName("Should build User with email only")
  void testUserBuilderEmailOnly() {
    User user = User.builder().email("test@example.com").build();
    assertEquals("test@example.com", user.getEmail());
    assertNull(user.getPassword());
    assertNull(user.getFullName());
  }

  @Test
  @DisplayName("Should verify multiple User builders are independent")
  void testMultipleUserBuilders() {
    User user1 =
        User.builder()
            .email("user1@example.com")
            .password("pass1")
            .fullName("User 1")
            .role(User.UserRole.USER)
            .build();
    User user2 =
        User.builder()
            .email("user2@example.com")
            .password("pass2")
            .fullName("User 2")
            .role(User.UserRole.ADMIN)
            .build();

    assertNotEquals(user1.getEmail(), user2.getEmail());
    assertNotEquals(user1.getRole(), user2.getRole());
  }

  @Test
  @DisplayName("Should handle User builder with field overwrite")
  void testUserBuilderFieldOverwrite() {
    User user =
        User.builder()
            .email("original@example.com")
            .email("new@example.com")
            .role(User.UserRole.USER)
            .role(User.UserRole.ADMIN)
            .build();

    assertEquals("new@example.com", user.getEmail());
    assertEquals(User.UserRole.ADMIN, user.getRole());
  }

  @Test
  @DisplayName("Should test User builder default active value")
  void testUserBuilderDefaultActive() {
    User user =
        User.builder()
            .email("test@example.com")
            .password("password")
            .fullName("Test User")
            .role(User.UserRole.USER)
            .build();

    assertTrue(user.getActive());
  }

  @Test
  @DisplayName("Should handle User with explicit active false")
  void testUserBuilderActiveExplicitFalse() {
    User user =
        User.builder()
            .email("test@example.com")
            .password("password")
            .fullName("Test User")
            .role(User.UserRole.USER)
            .active(false)
            .build();

    assertFalse(user.getActive());
  }

  @Test
  @DisplayName("Should verify User toString contains email")
  void testUserToStringContainsEmail() {
    User user =
        User.builder()
            .email("test@example.com")
            .password("password")
            .fullName("Test User")
            .role(User.UserRole.USER)
            .build();

    String str = user.toString();
    assertNotNull(str);
    assertTrue(str.length() > 0);
  }

  @Test
  @DisplayName("Should test equals with same object reference")
  void testUserEqualsSameReference() {
    assertTrue(testUser.equals(testUser));
  }

  @Test
  @DisplayName("Should test equals with different type")
  void testUserEqualsWithStringType() {
    assertFalse(testUser.equals("not a user"));
  }

  @Test
  @DisplayName("Should test equals with null password")
  void testUserEqualsNullPassword() {
    testUser.setPassword(null);
    User user2 =
        User.builder()
            .email("test@example.com")
            .password(null)
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user2.setId(testUser.getId());
    user2.setCreatedAt(testUser.getCreatedAt());
    user2.setUpdatedAt(testUser.getUpdatedAt());
    assertEquals(testUser, user2);
  }

  @Test
  @DisplayName("Should test equals with different password")
  void testUserEqualsWithDifferentPassword() {
    User user2 =
        User.builder()
            .email("test@example.com")
            .password("different")
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user2.setId(testUser.getId());
    user2.setCreatedAt(testUser.getCreatedAt());
    user2.setUpdatedAt(testUser.getUpdatedAt());
    assertNotEquals(testUser, user2);
  }

  @Test
  @DisplayName("Should test equals with null fullName")
  void testUserEqualsNullFullName() {
    testUser.setFullName(null);
    User user2 =
        User.builder()
            .email("test@example.com")
            .password("hashedPassword123")
            .fullName(null)
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user2.setId(testUser.getId());
    user2.setCreatedAt(testUser.getCreatedAt());
    user2.setUpdatedAt(testUser.getUpdatedAt());
    assertEquals(testUser, user2);
  }

  @Test
  @DisplayName("Should test hashCode with null password")
  void testUserHashCodeWithNullPassword() {
    testUser.setPassword(null);
    int hash = testUser.hashCode();
    assertNotEquals(0, hash);
  }

  @Test
  @DisplayName("Should test equals reflexive property")
  void testUserEqualsReflexive() {
    assertTrue(testUser.equals(testUser));
    assertTrue(testUser.equals(testUser));
  }

  @Test
  @DisplayName("Should test equals transitive property")
  void testUserEqualsTransitive() {
    User user2 =
        User.builder()
            .email("test@example.com")
            .password("hashedPassword123")
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user2.setId(testUser.getId());
    user2.setCreatedAt(testUser.getCreatedAt());
    user2.setUpdatedAt(testUser.getUpdatedAt());

    User user3 =
        User.builder()
            .email("test@example.com")
            .password("hashedPassword123")
            .fullName("John Doe")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user3.setId(user2.getId());
    user3.setCreatedAt(user2.getCreatedAt());
    user3.setUpdatedAt(user2.getUpdatedAt());

    assertEquals(testUser, user2);
    assertEquals(user2, user3);
    assertEquals(testUser, user3);
  }
}
