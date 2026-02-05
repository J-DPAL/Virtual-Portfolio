package com.portfolio.projects.mappingLayer.dto;

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

@DisplayName("ProjectDTO Tests")
class ProjectDTOTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("Should create DTO with no-args constructor")
  void testNoArgsConstructor() {
    ProjectDTO dto = new ProjectDTO();
    assertNotNull(dto);
  }

  @Test
  @DisplayName("Should create DTO with all-args constructor")
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    ProjectDTO dto =
        new ProjectDTO(
            1L,
            "Title EN",
            "Title FR",
            "Title ES",
            "Desc EN",
            "Desc FR",
            "Desc ES",
            "Java,Spring",
            "https://example.com",
            "https://github.com/example",
            "https://example.com/image.png",
            LocalDate.of(2020, 1, 1),
            LocalDate.of(2021, 1, 1),
            "Completed",
            now,
            now);

    assertEquals(1L, dto.getId());
    assertEquals("Title EN", dto.getTitleEn());
    assertEquals("Title FR", dto.getTitleFr());
    assertEquals("Title ES", dto.getTitleEs());
    assertEquals("Desc EN", dto.getDescriptionEn());
    assertEquals("Desc FR", dto.getDescriptionFr());
    assertEquals("Desc ES", dto.getDescriptionEs());
    assertEquals("Java,Spring", dto.getTechnologies());
    assertEquals("https://example.com", dto.getProjectUrl());
    assertEquals("https://github.com/example", dto.getGithubUrl());
    assertEquals("https://example.com/image.png", dto.getImageUrl());
    assertEquals(LocalDate.of(2020, 1, 1), dto.getStartDate());
    assertEquals(LocalDate.of(2021, 1, 1), dto.getEndDate());
    assertEquals("Completed", dto.getStatus());
    assertEquals(now, dto.getCreatedAt());
    assertEquals(now, dto.getUpdatedAt());
  }

  @Test
  @DisplayName("Should create DTO using builder")
  void testBuilder() {
    ProjectDTO dto =
        ProjectDTO.builder()
            .id(2L)
            .titleEn("Builder Title")
            .titleFr("Builder Title FR")
            .titleEs("Builder Title ES")
            .status("Active")
            .startDate(LocalDate.of(2022, 5, 1))
            .build();

    assertEquals(2L, dto.getId());
    assertEquals("Builder Title", dto.getTitleEn());
    assertEquals("Builder Title FR", dto.getTitleFr());
    assertEquals("Builder Title ES", dto.getTitleEs());
    assertEquals("Active", dto.getStatus());
    assertEquals(LocalDate.of(2022, 5, 1), dto.getStartDate());
  }

  @Test
  @DisplayName("Should set and get all fields")
  void testGettersAndSetters() {
    ProjectDTO dto = new ProjectDTO();

    dto.setId(5L);
    dto.setTitleEn("Title EN");
    dto.setTitleFr("Title FR");
    dto.setTitleEs("Title ES");
    dto.setDescriptionEn("Desc EN");
    dto.setDescriptionFr("Desc FR");
    dto.setDescriptionEs("Desc ES");
    dto.setTechnologies("Java,Spring");
    dto.setProjectUrl("https://example.com");
    dto.setGithubUrl("https://github.com/example");
    dto.setImageUrl("https://example.com/image.png");
    dto.setStartDate(LocalDate.of(2019, 6, 1));
    dto.setEndDate(LocalDate.of(2020, 6, 1));
    dto.setStatus("In Progress");
    dto.setCreatedAt(LocalDateTime.of(2023, 1, 1, 10, 0));
    dto.setUpdatedAt(LocalDateTime.of(2023, 1, 2, 10, 0));

    assertEquals(5L, dto.getId());
    assertEquals("Title EN", dto.getTitleEn());
    assertEquals("Title FR", dto.getTitleFr());
    assertEquals("Title ES", dto.getTitleEs());
    assertEquals("Desc EN", dto.getDescriptionEn());
    assertEquals("Desc FR", dto.getDescriptionFr());
    assertEquals("Desc ES", dto.getDescriptionEs());
    assertEquals("Java,Spring", dto.getTechnologies());
    assertEquals("https://example.com", dto.getProjectUrl());
    assertEquals("https://github.com/example", dto.getGithubUrl());
    assertEquals("https://example.com/image.png", dto.getImageUrl());
    assertEquals(LocalDate.of(2019, 6, 1), dto.getStartDate());
    assertEquals(LocalDate.of(2020, 6, 1), dto.getEndDate());
    assertEquals("In Progress", dto.getStatus());
    assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), dto.getCreatedAt());
    assertEquals(LocalDateTime.of(2023, 1, 2, 10, 0), dto.getUpdatedAt());
  }

  @Test
  @DisplayName("Should validate a valid DTO")
  void testValidDto() {
    ProjectDTO dto = createValidDto();

    Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
    assertTrue(violations.isEmpty());
  }

  @Test
  @DisplayName("Should fail validation when titleEn is blank")
  void testTitleEnNotBlank() {
    ProjectDTO dto = createValidDto();
    dto.setTitleEn("");

    Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> "titleEn".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should fail validation when titleFr is blank")
  void testTitleFrNotBlank() {
    ProjectDTO dto = createValidDto();
    dto.setTitleFr("");

    Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> "titleFr".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should fail validation when titleEs is blank")
  void testTitleEsNotBlank() {
    ProjectDTO dto = createValidDto();
    dto.setTitleEs("");

    Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> "titleEs".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should fail validation when projectUrl is invalid")
  void testProjectUrlPattern() {
    ProjectDTO dto = createValidDto();
    dto.setProjectUrl("ftp://invalid-url");

    Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> "projectUrl".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should fail validation when githubUrl is invalid")
  void testGithubUrlPattern() {
    ProjectDTO dto = createValidDto();
    dto.setGithubUrl("invalid-url");

    Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> "githubUrl".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should fail validation when imageUrl is invalid")
  void testImageUrlPattern() {
    ProjectDTO dto = createValidDto();
    dto.setImageUrl("invalid-url");

    Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> "imageUrl".equals(v.getPropertyPath().toString())));
  }

  @Test
  @DisplayName("Should allow null URLs")
  void testNullUrlsAreValid() {
    ProjectDTO dto = createValidDto();
    dto.setProjectUrl(null);
    dto.setGithubUrl(null);
    dto.setImageUrl(null);

    Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
    assertTrue(violations.isEmpty());
  }

  @Test
  @DisplayName("Should implement equals, hashCode, and toString")
  void testEqualsHashCodeToString() {
    ProjectDTO dto1 = createValidDto();
    ProjectDTO dto2 = createValidDto();

    assertEquals(dto1, dto1);
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1, null);
    assertNotEquals(dto1, "not-a-dto");
    assertNotNull(dto1.toString());
  }

  @Test
  @DisplayName("Should consider DTOs equal when optional fields are null")
  void testEqualsWithNullOptionalFields() {
    ProjectDTO dto1 = createValidDto();
    dto1.setDescriptionEn(null);
    dto1.setDescriptionFr(null);
    dto1.setDescriptionEs(null);
    dto1.setTechnologies(null);
    dto1.setProjectUrl(null);
    dto1.setGithubUrl(null);
    dto1.setImageUrl(null);
    dto1.setStartDate(null);
    dto1.setEndDate(null);

    ProjectDTO dto2 = createValidDto();
    dto2.setDescriptionEn(null);
    dto2.setDescriptionFr(null);
    dto2.setDescriptionEs(null);
    dto2.setTechnologies(null);
    dto2.setProjectUrl(null);
    dto2.setGithubUrl(null);
    dto2.setImageUrl(null);
    dto2.setStartDate(null);
    dto2.setEndDate(null);

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  @DisplayName("Should not equal when base optional fields are null and other has values")
  void testEqualsWithNullOnBaseOnly() {
    ProjectDTO dto1 = createValidDto();
    dto1.setDescriptionEn(null);
    dto1.setProjectUrl(null);

    ProjectDTO dto2 = createValidDto();

    assertNotEquals(dto1, dto2);
  }

  @ParameterizedTest(name = "Should not equal when {0} differs")
  @MethodSource("dtoMismatchCases")
  void testEqualsWithFieldDifferences(String label, Consumer<ProjectDTO> modifier) {
    ProjectDTO base = createValidDto();
    ProjectDTO other = createValidDto();

    modifier.accept(other);

    assertNotEquals(base, other, label);
  }

  private static Stream<Arguments> dtoMismatchCases() {
    return Stream.of(
        Arguments.of("id", (Consumer<ProjectDTO>) d -> d.setId(99L)),
        Arguments.of("titleEn", (Consumer<ProjectDTO>) d -> d.setTitleEn("Other")),
        Arguments.of("titleFr", (Consumer<ProjectDTO>) d -> d.setTitleFr("Other")),
        Arguments.of("titleEs", (Consumer<ProjectDTO>) d -> d.setTitleEs("Other")),
        Arguments.of("descriptionEn", (Consumer<ProjectDTO>) d -> d.setDescriptionEn(null)),
        Arguments.of("descriptionFr", (Consumer<ProjectDTO>) d -> d.setDescriptionFr(null)),
        Arguments.of("descriptionEs", (Consumer<ProjectDTO>) d -> d.setDescriptionEs(null)),
        Arguments.of("technologies", (Consumer<ProjectDTO>) d -> d.setTechnologies("Other")),
        Arguments.of(
            "projectUrl", (Consumer<ProjectDTO>) d -> d.setProjectUrl("https://other.com")),
        Arguments.of(
            "githubUrl", (Consumer<ProjectDTO>) d -> d.setGithubUrl("https://github.com/other")),
        Arguments.of(
            "imageUrl", (Consumer<ProjectDTO>) d -> d.setImageUrl("https://other.com/image.png")),
        Arguments.of(
            "startDate", (Consumer<ProjectDTO>) d -> d.setStartDate(LocalDate.of(2024, 1, 1))),
        Arguments.of("endDate", (Consumer<ProjectDTO>) d -> d.setEndDate(null)),
        Arguments.of("status", (Consumer<ProjectDTO>) d -> d.setStatus("Archived")),
        Arguments.of(
            "createdAt",
            (Consumer<ProjectDTO>) d -> d.setCreatedAt(LocalDateTime.of(2024, 1, 1, 1, 1))),
        Arguments.of(
            "updatedAt",
            (Consumer<ProjectDTO>) d -> d.setUpdatedAt(LocalDateTime.of(2024, 1, 2, 1, 1))));
  }

  private ProjectDTO createValidDto() {
    return ProjectDTO.builder()
        .id(1L)
        .titleEn("Title EN")
        .titleFr("Title FR")
        .titleEs("Title ES")
        .descriptionEn("Desc EN")
        .descriptionFr("Desc FR")
        .descriptionEs("Desc ES")
        .technologies("Java,Spring")
        .projectUrl("https://example.com")
        .githubUrl("https://github.com/example")
        .imageUrl("https://example.com/image.png")
        .startDate(LocalDate.of(2021, 1, 1))
        .endDate(LocalDate.of(2022, 1, 1))
        .status("Completed")
        .createdAt(LocalDateTime.of(2023, 1, 1, 10, 0))
        .updatedAt(LocalDateTime.of(2023, 1, 2, 10, 0))
        .build();
  }
}
