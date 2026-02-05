package com.portfolio.users.dataAccessLayer.repository;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.users.dataAccessLayer.entity.User;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("UserRepository Integration Tests")
class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  private User testUser;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data and clear repository
    userRepository.deleteAll();
    testUser =
        User.builder()
            .email("test@example.com")
            .password("hashedPassword123")
            .fullName("Test User")
            .role(User.UserRole.USER)
            .active(true)
            .build();
  }

  @Test
  @DisplayName("Should save a new user successfully")
  void testSaveUser_WithValidUser_ReturnsSavedUser() {
    // Arrange: User entity is created in setUp

    // Act: Save the user
    User savedUser = userRepository.save(testUser);

    // Assert: Verify user is saved with generated ID
    assertNotNull(savedUser.getId());
    assertEquals("test@example.com", savedUser.getEmail());
    assertEquals("Test User", savedUser.getFullName());
    assertEquals(User.UserRole.USER, savedUser.getRole());
    assertTrue(savedUser.getActive());
  }

  @Test
  @DisplayName("Should find user by email successfully")
  void testFindByEmail_WithExistingEmail_ReturnsUser() {
    // Arrange: Save user first
    userRepository.save(testUser);

    // Act: Find user by email
    Optional<User> foundUser = userRepository.findByEmail("test@example.com");

    // Assert: Verify user is found
    assertTrue(foundUser.isPresent());
    assertEquals("test@example.com", foundUser.get().getEmail());
    assertEquals("Test User", foundUser.get().getFullName());
  }

  @Test
  @DisplayName("Should return empty Optional when user email does not exist")
  void testFindByEmail_WithNonExistentEmail_ReturnsEmptyOptional() {
    // Arrange: User not saved, repository is empty

    // Act: Try to find user by email
    Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

    // Assert: Verify Optional is empty
    assertFalse(foundUser.isPresent());
  }

  @Test
  @DisplayName("Should return true when user with email exists")
  void testExistsByEmail_WithExistingEmail_ReturnsTrue() {
    // Arrange: Save user first
    userRepository.save(testUser);

    // Act: Check if user exists by email
    boolean exists = userRepository.existsByEmail("test@example.com");

    // Assert: Verify user exists
    assertTrue(exists);
  }

  @Test
  @DisplayName("Should return false when user with email does not exist")
  void testExistsByEmail_WithNonExistentEmail_ReturnsFalse() {
    // Arrange: User not saved

    // Act: Check if user exists by email
    boolean exists = userRepository.existsByEmail("nonexistent@example.com");

    // Assert: Verify user does not exist
    assertFalse(exists);
  }

  @Test
  @DisplayName("Should update user successfully")
  void testUpdateUser_WithValidChanges_ReturnUpdatedUser() {
    // Arrange: Save user first
    User savedUser = userRepository.save(testUser);

    // Act: Update user details
    savedUser.setFullName("Updated User");
    savedUser.setActive(false);
    User updatedUser = userRepository.save(savedUser);

    // Assert: Verify user is updated
    assertEquals(savedUser.getId(), updatedUser.getId());
    assertEquals("Updated User", updatedUser.getFullName());
    assertFalse(updatedUser.getActive());
  }

  @Test
  @DisplayName("Should delete user successfully")
  void testDeleteUser_WithExistingUser_UserIsRemoved() {
    // Arrange: Save user first
    User savedUser = userRepository.save(testUser);
    Long userId = savedUser.getId();

    // Act: Delete the user
    userRepository.deleteById(userId);

    // Assert: Verify user is deleted
    Optional<User> deletedUser = userRepository.findById(userId);
    assertFalse(deletedUser.isPresent());
  }

  @Test
  @DisplayName("Should find user by ID successfully")
  void testFindById_WithExistingUserId_ReturnsUser() {
    // Arrange: Save user first
    User savedUser = userRepository.save(testUser);

    // Act: Find user by ID
    Optional<User> foundUser = userRepository.findById(savedUser.getId());

    // Assert: Verify user is found
    assertTrue(foundUser.isPresent());
    assertEquals(savedUser.getId(), foundUser.get().getId());
    assertEquals("test@example.com", foundUser.get().getEmail());
  }

  @Test
  @DisplayName("Should return all users successfully")
  void testFindAll_WithMultipleUsers_ReturnsAllUsers() {
    // Arrange: Save multiple users
    User user1 = testUser;
    User user2 =
        User.builder()
            .email("user2@example.com")
            .password("hashedPassword456")
            .fullName("User Two")
            .role(User.UserRole.ADMIN)
            .active(true)
            .build();
    userRepository.save(user1);
    userRepository.save(user2);

    // Act: Retrieve all users
    var allUsers = userRepository.findAll();

    // Assert: Verify all users are returned
    assertEquals(2, allUsers.size());
  }

  @Test
  @DisplayName("Should handle user with ADMIN role")
  void testSaveAndFindUser_WithAdminRole_ReturnUserWithAdminRole() {
    // Arrange: Create admin user
    User adminUser =
        User.builder()
            .email("admin@example.com")
            .password("hashedAdminPassword")
            .fullName("Admin User")
            .role(User.UserRole.ADMIN)
            .active(true)
            .build();

    // Act: Save and retrieve admin user
    User savedAdmin = userRepository.save(adminUser);
    Optional<User> foundAdmin = userRepository.findByEmail("admin@example.com");

    // Assert: Verify admin user is saved and retrieved correctly
    assertTrue(foundAdmin.isPresent());
    assertEquals(User.UserRole.ADMIN, foundAdmin.get().getRole());
  }

  @Test
  @DisplayName("Should maintain email uniqueness constraint")
  void testSaveUser_WithDuplicateEmail_ThrowsException() {
    // Arrange: Save first user
    userRepository.save(testUser);
    User duplicateUser =
        User.builder()
            .email("test@example.com")
            .password("differentPassword")
            .fullName("Different User")
            .role(User.UserRole.USER)
            .active(true)
            .build();

    // Act & Assert: Verify duplicate email constraint is enforced
    assertThrows(Exception.class, () -> userRepository.save(duplicateUser));
  }
}
