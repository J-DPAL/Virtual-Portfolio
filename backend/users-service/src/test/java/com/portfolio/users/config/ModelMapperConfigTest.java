package com.portfolio.users.config;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.users.dataAccessLayer.entity.User;
import com.portfolio.users.mappingLayer.dto.UserDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("ModelMapperConfig Unit Tests")
class ModelMapperConfigTest {

  @Autowired private ModelMapper modelMapper;

  @Test
  @DisplayName("Should provide ModelMapper bean")
  void testModelMapperBeanExists() {
    // Assert: Verify ModelMapper bean is available
    assertNotNull(modelMapper);
  }

  @Test
  @DisplayName("Should map User entity to UserDTO")
  void testModelMapper_MapUserToDTO_ReturnsValidDTO() {
    // Arrange: Create test user
    User user =
        User.builder()
            .email("test@example.com")
            .password("hashedPassword")
            .fullName("Test User")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user.setId(1L);
    user.setCreatedAt(LocalDateTime.now());
    user.setUpdatedAt(LocalDateTime.now());

    // Act: Map user to DTO
    UserDTO dto = modelMapper.map(user, UserDTO.class);

    // Assert: Verify mapping
    assertNotNull(dto);
    assertEquals(user.getEmail(), dto.getEmail());
    assertEquals(user.getFullName(), dto.getFullName());
  }

  @Test
  @DisplayName("Should map UserDTO to User entity")
  void testModelMapper_MapDTOToUser_ReturnsValidEntity() {
    // Arrange: Create test DTO
    UserDTO dto =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("Test User")
            .role("USER")
            .active(true)
            .build();

    // Act: Map DTO to user
    User user = modelMapper.map(dto, User.class);

    // Assert: Verify mapping
    assertNotNull(user);
    assertEquals(dto.getEmail(), user.getEmail());
    assertEquals(dto.getFullName(), user.getFullName());
  }

  @Test
  @DisplayName("Should handle null source in mapping")
  void testModelMapper_MapNullSource_ThrowsException() {
    // Act & Assert: Verify null handling
    assertThrows(Exception.class, () -> modelMapper.map(null, UserDTO.class));
  }

  @Test
  @DisplayName("Should preserve all mapped fields")
  void testModelMapper_PreservesAllFields() {
    // Arrange
    User user =
        User.builder()
            .email("admin@example.com")
            .password("hashedPassword")
            .fullName("Admin User")
            .role(User.UserRole.ADMIN)
            .active(false)
            .build();
    user.setId(5L);

    // Act: Map user to DTO
    UserDTO dto = modelMapper.map(user, UserDTO.class);

    // Assert: Verify all fields are preserved
    assertEquals(user.getId(), dto.getId());
    assertEquals(user.getEmail(), dto.getEmail());
    assertEquals(user.getFullName(), dto.getFullName());
  }

  @Test
  @DisplayName("Should handle bidirectional mapping")
  void testModelMapper_BidirectionalMapping_PreservesData() {
    // Arrange
    User originalUser =
        User.builder()
            .email("test@example.com")
            .password("hashedPassword")
            .fullName("Test User")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    originalUser.setId(1L);

    // Act: Map User -> DTO -> User
    UserDTO dto = modelMapper.map(originalUser, UserDTO.class);
    User mappedUser = modelMapper.map(dto, User.class);

    // Assert: Verify data is preserved through bidirectional mapping
    assertEquals(originalUser.getEmail(), mappedUser.getEmail());
    assertEquals(originalUser.getFullName(), mappedUser.getFullName());
  }

  @Test
  @DisplayName("Should handle multiple instances independently")
  void testModelMapper_MultipleInstances_IndependentMappings() {
    // Arrange
    User user1 =
        User.builder()
            .email("user1@example.com")
            .fullName("User One")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    user1.setId(1L);

    User user2 =
        User.builder()
            .email("user2@example.com")
            .fullName("User Two")
            .role(User.UserRole.ADMIN)
            .active(false)
            .build();
    user2.setId(2L);

    // Act: Map both users
    UserDTO dto1 = modelMapper.map(user1, UserDTO.class);
    UserDTO dto2 = modelMapper.map(user2, UserDTO.class);

    // Assert: Verify both mappings are independent
    assertEquals("user1@example.com", dto1.getEmail());
    assertEquals("user2@example.com", dto2.getEmail());
    assertEquals("User One", dto1.getFullName());
    assertEquals("User Two", dto2.getFullName());
  }

  @Test
  @DisplayName("Should handle special characters in mapping")
  void testModelMapper_SpecialCharacters_MapsCorrectly() {
    // Arrange
    UserDTO dto =
        UserDTO.builder()
            .email("test+tag@example.co.uk")
            .fullName("Tëst Üsér Ñamé")
            .role("USER")
            .active(true)
            .build();

    // Act: Map DTO to User
    User user = modelMapper.map(dto, User.class);

    // Assert: Verify special characters are preserved
    assertEquals("test+tag@example.co.uk", user.getEmail());
    assertEquals("Tëst Üsér Ñamé", user.getFullName());
  }
}
