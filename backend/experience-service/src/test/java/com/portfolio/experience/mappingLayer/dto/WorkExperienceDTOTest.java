package com.portfolio.experience.mappingLayer.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WorkExperienceDTO Tests")
class WorkExperienceDTOTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("Should create DTO with no-args constructor")
  void testNoArgsConstructor() {
    WorkExperienceDTO dto = new WorkExperienceDTO();
    assertNotNull(dto);
  }

  @Test
  @DisplayName("Should create DTO with all-args constructor")
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    WorkExperienceDTO dto =
        new WorkExperienceDTO(
            1L,
            "Company EN",
            "Company FR",
            "Company ES",
            "Position EN",
            "Position FR",
            "Position ES",
            "Description EN",
            "Description FR",
            "Description ES",
            "Location EN",
            "Location FR",
            "Location ES",
            LocalDate.of(2020, 1, 1),
            LocalDate.of(2021, 1, 1),
            false,
            now,
            now,
            "React, Java",
            "💻");

    assertEquals(1L, dto.getId());
    assertEquals("Company EN", dto.getCompanyNameEn());
    assertEquals("Company FR", dto.getCompanyNameFr());
    assertEquals("Company ES", dto.getCompanyNameEs());
    assertEquals("Position EN", dto.getPositionEn());
    assertEquals("Position FR", dto.getPositionFr());
    assertEquals("Position ES", dto.getPositionEs());
    assertEquals("Description EN", dto.getDescriptionEn());
    assertEquals("Description FR", dto.getDescriptionFr());
    assertEquals("Description ES", dto.getDescriptionEs());
    assertEquals("Location EN", dto.getLocationEn());
    assertEquals("Location FR", dto.getLocationFr());
    assertEquals("Location ES", dto.getLocationEs());
    assertEquals(LocalDate.of(2020, 1, 1), dto.getStartDate());
    assertEquals(LocalDate.of(2021, 1, 1), dto.getEndDate());
    assertFalse(dto.getIsCurrent());
    assertEquals(now, dto.getCreatedAt());
    assertEquals(now, dto.getUpdatedAt());
    assertEquals("React, Java", dto.getSkillsUsed());
    assertEquals("💻", dto.getIcon());
  }

  @Test
  @DisplayName("Should create DTO using builder")
  void testBuilder() {
    WorkExperienceDTO dto =
        WorkExperienceDTO.builder()
            .id(2L)
            .companyNameEn("Builder Company")
            .companyNameFr("Builder Company FR")
            .companyNameEs("Builder Company ES")
            .positionEn("Engineer")
            .positionFr("Ingénieur")
            .positionEs("Ingeniero")
            .startDate(LocalDate.of(2022, 5, 1))
            .isCurrent(true)
            .build();

    assertEquals(2L, dto.getId());
    assertEquals("Builder Company", dto.getCompanyNameEn());
    assertEquals("Builder Company FR", dto.getCompanyNameFr());
    assertEquals("Builder Company ES", dto.getCompanyNameEs());
    assertEquals("Engineer", dto.getPositionEn());
    assertEquals("Ingénieur", dto.getPositionFr());
    assertEquals("Ingeniero", dto.getPositionEs());
    assertEquals(LocalDate.of(2022, 5, 1), dto.getStartDate());
    assertTrue(dto.getIsCurrent());
  }

  @Test
  @DisplayName("Should set and get all fields")
  void testGettersAndSetters() {
    WorkExperienceDTO dto = new WorkExperienceDTO();

    dto.setId(5L);
    dto.setCompanyNameEn("Company EN");
    dto.setCompanyNameFr("Company FR");
    dto.setCompanyNameEs("Company ES");
    dto.setPositionEn("Position EN");
    dto.setPositionFr("Position FR");
    dto.setPositionEs("Position ES");
    dto.setDescriptionEn("Description EN");
    dto.setDescriptionFr("Description FR");
    dto.setDescriptionEs("Description ES");
    dto.setLocationEn("Location EN");
    dto.setLocationFr("Location FR");
    dto.setLocationEs("Location ES");
    dto.setStartDate(LocalDate.of(2019, 6, 1));
    dto.setEndDate(LocalDate.of(2020, 6, 1));
    dto.setIsCurrent(false);
    dto.setCreatedAt(LocalDateTime.of(2023, 1, 1, 10, 0));
    dto.setUpdatedAt(LocalDateTime.of(2023, 1, 2, 10, 0));
    dto.setSkillsUsed("React, Java");
    dto.setIcon("💻");

    assertEquals(5L, dto.getId());
    assertEquals("Company EN", dto.getCompanyNameEn());
    assertEquals("Company FR", dto.getCompanyNameFr());
    assertEquals("Company ES", dto.getCompanyNameEs());
    assertEquals("Position EN", dto.getPositionEn());
    assertEquals("Position FR", dto.getPositionFr());
    assertEquals("Position ES", dto.getPositionEs());
    assertEquals("Description EN", dto.getDescriptionEn());
    assertEquals("Description FR", dto.getDescriptionFr());
    assertEquals("Description ES", dto.getDescriptionEs());
    assertEquals("Location EN", dto.getLocationEn());
    assertEquals("Location FR", dto.getLocationFr());
    assertEquals("Location ES", dto.getLocationEs());
    assertEquals(LocalDate.of(2019, 6, 1), dto.getStartDate());
    assertEquals(LocalDate.of(2020, 6, 1), dto.getEndDate());
    assertFalse(dto.getIsCurrent());
    assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), dto.getCreatedAt());
    assertEquals(LocalDateTime.of(2023, 1, 2, 10, 0), dto.getUpdatedAt());
    assertEquals("React, Java", dto.getSkillsUsed());
    assertEquals("💻", dto.getIcon());
  }

  @Test
  @DisplayName("Should validate a valid DTO")
  void testValidDto() {
    WorkExperienceDTO dto = createValidDto();

    Set<ConstraintViolation<WorkExperienceDTO>> violations = validator.validate(dto);
    assertTrue(violations.isEmpty());
  }

  @Test
  @DisplayName("Should fail validation when companyNameEn is blank")
  void testCompanyNameEnNotBlank() {
    WorkExperienceDTO dto = createValidDto();
    dto.setCompanyNameEn("");

    Set<ConstraintViolation<WorkExperienceDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> "companyNameEn".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should fail validation when companyNameFr is blank")
  void testCompanyNameFrNotBlank() {
    WorkExperienceDTO dto = createValidDto();
    dto.setCompanyNameFr("");

    Set<ConstraintViolation<WorkExperienceDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> "companyNameFr".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should fail validation when companyNameEs is blank")
  void testCompanyNameEsNotBlank() {
    WorkExperienceDTO dto = createValidDto();
    dto.setCompanyNameEs("");

    Set<ConstraintViolation<WorkExperienceDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> "companyNameEs".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should fail validation when positionEn is blank")
  void testPositionEnNotBlank() {
    WorkExperienceDTO dto = createValidDto();
    dto.setPositionEn("");

    Set<ConstraintViolation<WorkExperienceDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> "positionEn".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should fail validation when positionFr is blank")
  void testPositionFrNotBlank() {
    WorkExperienceDTO dto = createValidDto();
    dto.setPositionFr("");

    Set<ConstraintViolation<WorkExperienceDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> "positionFr".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should fail validation when positionEs is blank")
  void testPositionEsNotBlank() {
    WorkExperienceDTO dto = createValidDto();
    dto.setPositionEs("");

    Set<ConstraintViolation<WorkExperienceDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> "positionEs".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should fail validation when startDate is null")
  void testStartDateNotNull() {
    WorkExperienceDTO dto = createValidDto();
    dto.setStartDate(null);

    Set<ConstraintViolation<WorkExperienceDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> "startDate".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should fail validation when isCurrent is null")
  void testIsCurrentNotNull() {
    WorkExperienceDTO dto = createValidDto();
    dto.setIsCurrent(null);

    Set<ConstraintViolation<WorkExperienceDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> "isCurrent".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should implement equals, hashCode, and toString")
  void testEqualsHashCodeToString() {
    WorkExperienceDTO dto1 = createValidDto();
    WorkExperienceDTO dto2 = createValidDto();

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1, null);
    assertNotEquals(dto1, "not-a-dto");
    assertNotNull(dto1.toString());
  }

  @ParameterizedTest(name = "Should not equal when {0} differs")
  @MethodSource("dtoMismatchCases")
  void testEqualsWithFieldDifferences(String label, Consumer<WorkExperienceDTO> modifier) {
    WorkExperienceDTO base = createValidDto();
    WorkExperienceDTO other = createValidDto();

    modifier.accept(other);

    assertNotEquals(base, other, label);
  }

  private static Stream<Arguments> dtoMismatchCases() {
    return Stream.of(
        Arguments.of("id", (Consumer<WorkExperienceDTO>) d -> d.setId(99L)),
        Arguments.of(
            "companyNameEn", (Consumer<WorkExperienceDTO>) d -> d.setCompanyNameEn("Other")),
        Arguments.of(
            "companyNameFr", (Consumer<WorkExperienceDTO>) d -> d.setCompanyNameFr("Other")),
        Arguments.of(
            "companyNameEs", (Consumer<WorkExperienceDTO>) d -> d.setCompanyNameEs("Other")),
        Arguments.of("positionEn", (Consumer<WorkExperienceDTO>) d -> d.setPositionEn("Other")),
        Arguments.of("positionFr", (Consumer<WorkExperienceDTO>) d -> d.setPositionFr("Other")),
        Arguments.of("positionEs", (Consumer<WorkExperienceDTO>) d -> d.setPositionEs("Other")),
        Arguments.of("descriptionEn", (Consumer<WorkExperienceDTO>) d -> d.setDescriptionEn(null)),
        Arguments.of("descriptionFr", (Consumer<WorkExperienceDTO>) d -> d.setDescriptionFr(null)),
        Arguments.of("descriptionEs", (Consumer<WorkExperienceDTO>) d -> d.setDescriptionEs(null)),
        Arguments.of("locationEn", (Consumer<WorkExperienceDTO>) d -> d.setLocationEn("Other")),
        Arguments.of("locationFr", (Consumer<WorkExperienceDTO>) d -> d.setLocationFr("Other")),
        Arguments.of("locationEs", (Consumer<WorkExperienceDTO>) d -> d.setLocationEs("Other")),
        Arguments.of(
            "startDate",
            (Consumer<WorkExperienceDTO>) d -> d.setStartDate(LocalDate.of(2024, 1, 1))),
        Arguments.of("endDate", (Consumer<WorkExperienceDTO>) d -> d.setEndDate(null)),
        Arguments.of("isCurrent", (Consumer<WorkExperienceDTO>) d -> d.setIsCurrent(true)),
        Arguments.of(
            "createdAt",
            (Consumer<WorkExperienceDTO>) d -> d.setCreatedAt(LocalDateTime.of(2024, 1, 1, 1, 1))),
        Arguments.of(
            "updatedAt",
            (Consumer<WorkExperienceDTO>) d -> d.setUpdatedAt(LocalDateTime.of(2024, 1, 2, 1, 1))),
        Arguments.of("skillsUsed", (Consumer<WorkExperienceDTO>) d -> d.setSkillsUsed("Other")),
        Arguments.of("icon", (Consumer<WorkExperienceDTO>) d -> d.setIcon("🎯")));
  }

  private WorkExperienceDTO createValidDto() {
    return WorkExperienceDTO.builder()
        .id(1L)
        .companyNameEn("Company EN")
        .companyNameFr("Company FR")
        .companyNameEs("Company ES")
        .positionEn("Position EN")
        .positionFr("Position FR")
        .positionEs("Position ES")
        .descriptionEn("Description EN")
        .descriptionFr("Description FR")
        .descriptionEs("Description ES")
        .locationEn("Location EN")
        .locationFr("Location FR")
        .locationEs("Location ES")
        .startDate(LocalDate.of(2021, 1, 1))
        .endDate(LocalDate.of(2022, 1, 1))
        .isCurrent(false)
        .createdAt(LocalDateTime.of(2023, 1, 1, 10, 0))
        .updatedAt(LocalDateTime.of(2023, 1, 2, 10, 0))
        .skillsUsed("React, Java")
        .icon("💻")
        .build();
  }
}
