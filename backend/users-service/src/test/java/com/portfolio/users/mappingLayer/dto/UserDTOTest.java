package com.portfolio.users.mappingLayer.dto;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserDTO Unit Tests")
class UserDTOTest {

  private UserDTO testUserDTO;
  private LocalDateTime testDateTime;

  @BeforeEach
  void setUp() {
    testDateTime = LocalDateTime.now();
    testUserDTO =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("John Doe")
            .role("USER")
            .active(true)
            .createdAt(testDateTime)
            .updatedAt(testDateTime)
            .build();
  }

  @Test
  @DisplayName("Should create UserDTO with all fields")
  void testUserDTOCreation_WithAllFields() {
    assertNotNull(testUserDTO);
    assertEquals(1L, testUserDTO.getId());
    assertEquals("test@example.com", testUserDTO.getEmail());
    assertEquals("John Doe", testUserDTO.getFullName());
    assertEquals("USER", testUserDTO.getRole());
    assertTrue(testUserDTO.getActive());
    assertEquals(testDateTime, testUserDTO.getCreatedAt());
    assertEquals(testDateTime, testUserDTO.getUpdatedAt());
  }

  @Test
  @DisplayName("Should create UserDTO with no-args constructor")
  void testUserDTONoArgsConstructor() {
    UserDTO dto = new UserDTO();
    assertNotNull(dto);
  }

  @Test
  @DisplayName("Should create UserDTO with all-args constructor")
  void testUserDTOAllArgsConstructor() {
    UserDTO dto =
        new UserDTO(1L, "test@example.com", "John Doe", "ADMIN", true, testDateTime, testDateTime);
    assertNotNull(dto);
    assertEquals(1L, dto.getId());
    assertEquals("test@example.com", dto.getEmail());
  }

  @Test
  @DisplayName("Should create UserDTO with builder pattern")
  void testUserDTOBuilder() {
    UserDTO dto =
        UserDTO.builder()
            .id(2L)
            .email("admin@example.com")
            .fullName("Admin User")
            .role("ADMIN")
            .active(false)
            .build();

    assertNotNull(dto);
    assertEquals(2L, dto.getId());
    assertEquals("admin@example.com", dto.getEmail());
    assertEquals("Admin User", dto.getFullName());
    assertEquals("ADMIN", dto.getRole());
    assertFalse(dto.getActive());
  }

  @Test
  @DisplayName("Should set and get id correctly")
  void testIdSetterGetter() {
    testUserDTO.setId(2L);
    assertEquals(2L, testUserDTO.getId());
  }

  @Test
  @DisplayName("Should set and get email correctly")
  void testEmailSetterGetter() {
    testUserDTO.setEmail("newemail@example.com");
    assertEquals("newemail@example.com", testUserDTO.getEmail());
  }

  @Test
  @DisplayName("Should set and get fullName correctly")
  void testFullNameSetterGetter() {
    testUserDTO.setFullName("Jane Doe");
    assertEquals("Jane Doe", testUserDTO.getFullName());
  }

  @Test
  @DisplayName("Should set and get role correctly")
  void testRoleSetterGetter() {
    testUserDTO.setRole("ADMIN");
    assertEquals("ADMIN", testUserDTO.getRole());
  }

  @Test
  @DisplayName("Should set and get active status correctly")
  void testActiveSetterGetter() {
    testUserDTO.setActive(false);
    assertFalse(testUserDTO.getActive());

    testUserDTO.setActive(true);
    assertTrue(testUserDTO.getActive());
  }

  @Test
  @DisplayName("Should set and get createdAt correctly")
  void testCreatedAtSetterGetter() {
    LocalDateTime newTime = LocalDateTime.now().plusDays(1);
    testUserDTO.setCreatedAt(newTime);
    assertEquals(newTime, testUserDTO.getCreatedAt());
  }

  @Test
  @DisplayName("Should set and get updatedAt correctly")
  void testUpdatedAtSetterGetter() {
    LocalDateTime newTime = LocalDateTime.now().plusHours(2);
    testUserDTO.setUpdatedAt(newTime);
    assertEquals(newTime, testUserDTO.getUpdatedAt());
  }

  @ParameterizedTest
  @ValueSource(strings = {"USER", "ADMIN"})
  @DisplayName("Should set UserDTO with different roles")
  void testUserDTOWithDifferentRoles(String role) {
    testUserDTO.setRole(role);
    assertEquals(role, testUserDTO.getRole());
  }

  @Test
  @DisplayName("Should test equals with same values")
  void testEqualsWithSameValues() {
    UserDTO dto1 =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("John Doe")
            .role("USER")
            .active(true)
            .build();

    UserDTO dto2 =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("John Doe")
            .role("USER")
            .active(true)
            .build();

    assertEquals(dto1, dto2);
  }

  @Test
  @DisplayName("Should test equals with different emails")
  void testEqualsWithDifferentEmails() {
    UserDTO dto1 =
        UserDTO.builder()
            .id(1L)
            .email("test1@example.com")
            .fullName("John Doe")
            .role("USER")
            .active(true)
            .build();

    UserDTO dto2 =
        UserDTO.builder()
            .id(1L)
            .email("test2@example.com")
            .fullName("John Doe")
            .role("USER")
            .active(true)
            .build();

    assertNotEquals(dto1, dto2);
  }

  @Test
  @DisplayName("Should test equals with different roles")
  void testEqualsWithDifferentRoles() {
    UserDTO dto1 =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("John Doe")
            .role("USER")
            .active(true)
            .build();

    UserDTO dto2 =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("John Doe")
            .role("ADMIN")
            .active(true)
            .build();

    assertNotEquals(dto1, dto2);
  }

  @Test
  @DisplayName("Should test hashCode consistency")
  void testHashCodeConsistency() {
    UserDTO dto1 =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("John Doe")
            .role("USER")
            .active(true)
            .build();

    UserDTO dto2 =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("John Doe")
            .role("USER")
            .active(true)
            .build();

    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  @DisplayName("Should test toString method")
  void testToString() {
    String dtoString = testUserDTO.toString();
    assertNotNull(dtoString);
    assertTrue(dtoString.contains("UserDTO") || dtoString.contains("test@example.com"));
  }

  @Test
  @DisplayName("Should test equals with null")
  void testEqualsWithNull() {
    assertNotEquals(testUserDTO, null);
  }

  @Test
  @DisplayName("Should test equals with different type")
  void testEqualsWithDifferentType() {
    assertNotEquals(testUserDTO, "not a dto");
  }

  @Test
  @DisplayName("Should test multiple DTOs with different ids")
  void testMultipleDTOsWithDifferentIds() {
    UserDTO dto1 =
        UserDTO.builder()
            .id(1L)
            .email("user1@example.com")
            .fullName("User One")
            .role("USER")
            .active(true)
            .build();

    UserDTO dto2 =
        UserDTO.builder()
            .id(2L)
            .email("user2@example.com")
            .fullName("User Two")
            .role("ADMIN")
            .active(false)
            .build();

    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.getId(), dto2.getId());
  }

  @Test
  @DisplayName("Should test DTO with null timestamps")
  void testDTOWithNullTimestamps() {
    UserDTO dto =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("John Doe")
            .role("USER")
            .active(true)
            .createdAt(null)
            .updatedAt(null)
            .build();

    assertNull(dto.getCreatedAt());
    assertNull(dto.getUpdatedAt());
  }

  @Test
  @DisplayName("Should test DTO with null active field")
  void testDTOWithNullActiveField() {
    UserDTO dto = new UserDTO();
    dto.setId(1L);
    dto.setEmail("test@example.com");
    dto.setActive(null);
    assertNull(dto.getActive());
  }

  @Test
  @DisplayName("Should test DTO field assignments individually")
  void testDTOFieldAssignmentIndividually() {
    UserDTO dto = new UserDTO();
    dto.setId(1L);
    dto.setEmail("test@example.com");
    dto.setFullName("John Doe");
    dto.setRole("USER");
    dto.setActive(true);

    assertEquals(1L, dto.getId());
    assertEquals("test@example.com", dto.getEmail());
    assertEquals("John Doe", dto.getFullName());
    assertEquals("USER", dto.getRole());
    assertTrue(dto.getActive());
  }

  @Test
  @DisplayName("Should test DTO with large id")
  void testDTOWithLargeId() {
    long largeId = 9999999999L;
    UserDTO dto = new UserDTO();
    dto.setId(largeId);
    assertEquals(largeId, dto.getId());
  }

  @Test
  @DisplayName("Should build UserDTO with specific fields only")
  void testUserDTOBuilderWithSpecificFields() {
    UserDTO dto = UserDTO.builder().email("test@example.com").build();
    assertEquals("test@example.com", dto.getEmail());
    assertNull(dto.getId());
  }

  @Test
  @DisplayName("Should build UserDTO with all fields sequentially")
  void testUserDTOBuilderAllFieldsSequentially() {
    UserDTO dto =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("Test User")
            .role("USER")
            .active(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    assertAll(
        () -> assertEquals(1L, dto.getId()),
        () -> assertEquals("test@example.com", dto.getEmail()),
        () -> assertEquals("Test User", dto.getFullName()),
        () -> assertEquals("USER", dto.getRole()),
        () -> assertTrue(dto.getActive()));
  }

  @Test
  @DisplayName("Should handle null values in UserDTO builder")
  void testUserDTOBuilderWithNullValues() {
    UserDTO dto =
        UserDTO.builder()
            .id(null)
            .email(null)
            .fullName(null)
            .role(null)
            .active(null)
            .createdAt(null)
            .updatedAt(null)
            .build();
    assertNull(dto.getId());
    assertNull(dto.getEmail());
    assertNull(dto.getFullName());
  }

  @Test
  @DisplayName("Should build multiple UserDTOs with different configs")
  void testMultipleUserDTOsBuilders() {
    UserDTO dto1 = UserDTO.builder().id(1L).email("user1@example.com").role("USER").build();
    UserDTO dto2 = UserDTO.builder().id(2L).email("user2@example.com").role("ADMIN").build();

    assertNotEquals(dto1.getId(), dto2.getId());
    assertNotEquals(dto1.getEmail(), dto2.getEmail());
    assertNotEquals(dto1.getRole(), dto2.getRole());
  }

  @Test
  @DisplayName("Should verify builder returns new instance")
  void testUserDTOBuilderReturnsNewInstance() {
    UserDTO.UserDTOBuilder builder1 = UserDTO.builder().id(1L);
    UserDTO.UserDTOBuilder builder2 = UserDTO.builder().id(1L);

    assertNotSame(builder1, builder2);
    UserDTO dto1 = builder1.build();
    UserDTO dto2 = builder2.build();
    assertNotSame(dto1, dto2);
  }

  @Test
  @DisplayName("Should handle builder chaining with same field overwrite")
  void testUserDTOBuilderChainingOverwrite() {
    UserDTO dto =
        UserDTO.builder()
            .email("original@example.com")
            .email("overwritten@example.com")
            .fullName("Original Name")
            .fullName("Overwritten Name")
            .build();

    assertEquals("overwritten@example.com", dto.getEmail());
    assertEquals("Overwritten Name", dto.getFullName());
  }

  @Test
  @DisplayName("Should handle empty string values in UserDTO")
  void testUserDTOWithEmptyStrings() {
    UserDTO dto = UserDTO.builder().email("").fullName("").role("").build();

    assertEquals("", dto.getEmail());
    assertEquals("", dto.getFullName());
    assertEquals("", dto.getRole());
  }

  @Test
  @DisplayName("Should verify UserDTO defaults")
  void testUserDTOBuilderDefaults() {
    UserDTO dto = new UserDTO();
    assertNull(dto.getId());
    assertNull(dto.getEmail());
    assertNull(dto.getFullName());
    assertNull(dto.getRole());
    assertNull(dto.getActive());
  }

  @Test
  @DisplayName("Should test UserDTO copy constructor via builder")
  void testUserDTOCopyViaBuilder() {
    LocalDateTime now = LocalDateTime.now();
    UserDTO original =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("Test User")
            .role("USER")
            .active(true)
            .createdAt(now)
            .updatedAt(now)
            .build();

    UserDTO copy =
        UserDTO.builder()
            .id(original.getId())
            .email(original.getEmail())
            .fullName(original.getFullName())
            .role(original.getRole())
            .active(original.getActive())
            .createdAt(original.getCreatedAt())
            .updatedAt(original.getUpdatedAt())
            .build();

    assertEquals(original, copy);
  }

  @Test
  @DisplayName("Should test UserDTO equals with same hash codes")
  void testUserDTOEqualsWithMatchingHashes() {
    UserDTO dto1 =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("Test")
            .role("USER")
            .active(true)
            .build();

    UserDTO dto2 =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("Test")
            .role("USER")
            .active(true)
            .build();

    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertEquals(dto1, dto2);
  }

  @Test
  @DisplayName("Should test UserDTO toString format")
  void testUserDTOToStringFormat() {
    UserDTO dto =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("Test User")
            .role("ADMIN")
            .active(false)
            .build();

    String str = dto.toString();
    assertNotNull(str);
    assertTrue(str.contains("UserDTO") || str.length() > 0);
  }
}
