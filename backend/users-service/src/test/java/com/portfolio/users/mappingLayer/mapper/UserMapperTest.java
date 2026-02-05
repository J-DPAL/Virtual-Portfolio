package com.portfolio.users.mappingLayer.mapper;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.users.dataAccessLayer.entity.User;
import com.portfolio.users.mappingLayer.dto.UserDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("UserMapper Unit Tests")
class UserMapperTest {

  @Autowired private UserMapper userMapper;

  private User testUser;
  private UserDTO testUserDTO;

  @BeforeEach
  void setUp() {
    // Create test user
    testUser =
        User.builder()
            .email("test@example.com")
            .password("hashedPassword")
            .fullName("Test User")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    testUser.setId(1L);
    testUser.setCreatedAt(LocalDateTime.now());
    testUser.setUpdatedAt(LocalDateTime.now());

    // Create test DTO
    testUserDTO =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("Test User")
            .role("USER")
            .active(true)
            .build();
  }

  @Test
  @DisplayName("Should map User entity to UserDTO successfully")
  void testToDTO_WithValidUser_ReturnsMappedDTO() {
    // Act: Map user to DTO
    UserDTO dto = userMapper.toDTO(testUser);

    // Assert: Verify mapping
    assertNotNull(dto);
    assertEquals(testUser.getId(), dto.getId());
    assertEquals(testUser.getEmail(), dto.getEmail());
    assertEquals(testUser.getFullName(), dto.getFullName());
    assertEquals(testUser.getRole().toString(), dto.getRole());
    assertEquals(testUser.getActive(), dto.getActive());
  }

  @Test
  @DisplayName("Should map UserDTO to User entity successfully")
  void testToEntity_WithValidDTO_ReturnsMappedUser() {
    // Act: Map DTO to user
    User user = userMapper.toEntity(testUserDTO);

    // Assert: Verify mapping
    assertNotNull(user);
    assertEquals(testUserDTO.getId(), user.getId());
    assertEquals(testUserDTO.getEmail(), user.getEmail());
    assertEquals(testUserDTO.getFullName(), user.getFullName());
  }

  @Test
  @DisplayName("Should handle null user when mapping to DTO")
  void testToDTO_WithNullUser_ReturnsNull() {
    // Act & Assert: Verify null handling
    assertThrows(Exception.class, () -> userMapper.toDTO(null));
  }

  @Test
  @DisplayName("Should handle null DTO when mapping to User")
  void testToEntity_WithNullDTO_ReturnsNull() {
    // Act & Assert: Verify null handling
    assertThrows(Exception.class, () -> userMapper.toEntity(null));
  }

  @Test
  @DisplayName("Should preserve all user fields during mapping")
  void testToDTO_PreservesAllFields() {
    // Arrange
    testUser.setEmail("admin@example.com");
    testUser.setFullName("Admin User");
    testUser.setRole(User.UserRole.ADMIN);
    testUser.setActive(false);

    // Act: Map to DTO
    UserDTO dto = userMapper.toDTO(testUser);

    // Assert: Verify all fields are preserved
    assertEquals("admin@example.com", dto.getEmail());
    assertEquals("Admin User", dto.getFullName());
    assertEquals("ADMIN", dto.getRole());
    assertEquals(false, dto.getActive());
  }

  @Test
  @DisplayName("Should handle ADMIN role mapping correctly")
  void testToDTO_WithAdminRole_MapsCorrectly() {
    // Arrange: Create admin user
    testUser.setRole(User.UserRole.ADMIN);

    // Act: Map to DTO
    UserDTO dto = userMapper.toDTO(testUser);

    // Assert: Verify role is ADMIN
    assertEquals("ADMIN", dto.getRole());
  }

  @Test
  @DisplayName("Should handle USER role mapping correctly")
  void testToDTO_WithUserRole_MapsCorrectly() {
    // Arrange: Create user with USER role
    testUser.setRole(User.UserRole.USER);

    // Act: Map to DTO
    UserDTO dto = userMapper.toDTO(testUser);

    // Assert: Verify role is USER
    assertEquals("USER", dto.getRole());
  }

  @Test
  @DisplayName("Should handle inactive user mapping")
  void testToDTO_WithInactiveUser_MapsCorrectly() {
    // Arrange: Create inactive user
    testUser.setActive(false);

    // Act: Map to DTO
    UserDTO dto = userMapper.toDTO(testUser);

    // Assert: Verify active flag is false
    assertEquals(false, dto.getActive());
  }

  @Test
  @DisplayName("Should handle active user mapping")
  void testToDTO_WithActiveUser_MapsCorrectly() {
    // Arrange: Create active user
    testUser.setActive(true);

    // Act: Map to DTO
    UserDTO dto = userMapper.toDTO(testUser);

    // Assert: Verify active flag is true
    assertEquals(true, dto.getActive());
  }

  @Test
  @DisplayName("Should handle special characters in email")
  void testToDTO_WithSpecialCharactersInEmail_MapsCorrectly() {
    // Arrange
    testUser.setEmail("test+tag@example.co.uk");

    // Act: Map to DTO
    UserDTO dto = userMapper.toDTO(testUser);

    // Assert: Verify email is mapped correctly
    assertEquals("test+tag@example.co.uk", dto.getEmail());
  }

  @Test
  @DisplayName("Should handle long names in mapping")
  void testToDTO_WithLongName_MapsCorrectly() {
    // Arrange
    testUser.setFullName("This Is A Very Long Name With Many Words And Special Characters");

    // Act: Map to DTO
    UserDTO dto = userMapper.toDTO(testUser);

    // Assert: Verify name is mapped correctly
    assertEquals(
        "This Is A Very Long Name With Many Words And Special Characters", dto.getFullName());
  }
}
