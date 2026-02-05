package com.portfolio.skills.mappingLayer.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class SkillDTOTest {

  private Validator validator;
  private SkillDTO skillDTO;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

    skillDTO =
        SkillDTO.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Java programming language")
            .descriptionFr("Langage de programmation Java")
            .descriptionEs("Lenguaje de programación Java")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testNoArgsConstructor() {
    SkillDTO newDTO = new SkillDTO();
    assertNull(newDTO.getId());
    assertNull(newDTO.getNameEn());
    assertNull(newDTO.getNameFr());
    assertNull(newDTO.getNameEs());
    assertNull(newDTO.getDescriptionEn());
    assertNull(newDTO.getDescriptionFr());
    assertNull(newDTO.getDescriptionEs());
    assertNull(newDTO.getProficiencyLevel());
    assertNull(newDTO.getCategory());
    assertNull(newDTO.getYearsOfExperience());
    assertNull(newDTO.getCreatedAt());
    assertNull(newDTO.getUpdatedAt());
  }

  @Test
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    SkillDTO testDTO =
        new SkillDTO(
            1L,
            "Python",
            "Python",
            "Python",
            "Python programming language",
            "Langage de programmation Python",
            "Lenguaje de programación Python",
            "Advanced",
            "Backend",
            8,
            now,
            now);

    assertEquals(1L, testDTO.getId());
    assertEquals("Python", testDTO.getNameEn());
    assertEquals("Python", testDTO.getNameFr());
    assertEquals("Python", testDTO.getNameEs());
    assertEquals("Advanced", testDTO.getProficiencyLevel());
    assertEquals("Backend", testDTO.getCategory());
    assertEquals(8, testDTO.getYearsOfExperience());
  }

  @Test
  void testBuilder() {
    SkillDTO builtDTO =
        SkillDTO.builder()
            .id(2L)
            .nameEn("JavaScript")
            .nameFr("JavaScript")
            .nameEs("JavaScript")
            .descriptionEn("JavaScript programming language")
            .descriptionFr("Langage de programmation JavaScript")
            .descriptionEs("Lenguaje de programación JavaScript")
            .proficiencyLevel("Expert")
            .category("Frontend")
            .yearsOfExperience(12)
            .build();

    assertEquals(2L, builtDTO.getId());
    assertEquals("JavaScript", builtDTO.getNameEn());
    assertEquals("Frontend", builtDTO.getCategory());
  }

  @Test
  void testGettersAndSetters() {
    SkillDTO testDTO = new SkillDTO();
    testDTO.setId(3L);
    testDTO.setNameEn("React");
    testDTO.setNameFr("React");
    testDTO.setNameEs("React");
    testDTO.setDescriptionEn("React library");
    testDTO.setDescriptionFr("Bibliothèque React");
    testDTO.setDescriptionEs("Biblioteca React");
    testDTO.setProficiencyLevel("Intermediate");
    testDTO.setCategory("Frontend");
    testDTO.setYearsOfExperience(5);
    testDTO.setCreatedAt(LocalDateTime.now());
    testDTO.setUpdatedAt(LocalDateTime.now());

    assertEquals(3L, testDTO.getId());
    assertEquals("React", testDTO.getNameEn());
    assertEquals("Intermediate", testDTO.getProficiencyLevel());
    assertEquals(5, testDTO.getYearsOfExperience());
  }

  @Test
  void testValidDTO() {
    Set<ConstraintViolation<SkillDTO>> violations = validator.validate(skillDTO);
    assertTrue(violations.isEmpty(), "Valid DTO should have no violations");
  }

  @Test
  void testNameEnNotBlank() {
    skillDTO.setNameEn("");
    Set<ConstraintViolation<SkillDTO>> violations = validator.validate(skillDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> v.getMessage().contains("English name is required")));
  }

  @Test
  void testNameEnNull() {
    skillDTO.setNameEn(null);
    Set<ConstraintViolation<SkillDTO>> violations = validator.validate(skillDTO);
    assertFalse(violations.isEmpty());
  }

  @Test
  void testNameFrNotBlank() {
    skillDTO.setNameFr("");
    Set<ConstraintViolation<SkillDTO>> violations = validator.validate(skillDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> v.getMessage().contains("French name is required")));
  }

  @Test
  void testNameEsNotBlank() {
    skillDTO.setNameEs("");
    Set<ConstraintViolation<SkillDTO>> violations = validator.validate(skillDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> v.getMessage().contains("Spanish name is required")));
  }

  @Test
  void testProficiencyLevelNotBlank() {
    skillDTO.setProficiencyLevel("");
    Set<ConstraintViolation<SkillDTO>> violations = validator.validate(skillDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream()
            .anyMatch(v -> v.getMessage().contains("Proficiency level is required")));
  }

  @Test
  void testCategoryNotBlank() {
    skillDTO.setCategory("  ");
    Set<ConstraintViolation<SkillDTO>> violations = validator.validate(skillDTO);
    assertFalse(violations.isEmpty());
  }

  @Test
  void testYearsOfExperienceMin() {
    skillDTO.setYearsOfExperience(-1);
    Set<ConstraintViolation<SkillDTO>> violations = validator.validate(skillDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream()
            .anyMatch(v -> v.getMessage().contains("Years of experience must be positive")));
  }

  @Test
  void testYearsOfExperienceZero() {
    skillDTO.setYearsOfExperience(0);
    Set<ConstraintViolation<SkillDTO>> violations = validator.validate(skillDTO);
    assertTrue(
        violations.isEmpty()
            || violations.stream()
                .noneMatch(v -> v.getPropertyPath().toString().contains("yearsOfExperience")));
  }

  @Test
  void testDescriptionsCanBeNull() {
    skillDTO.setDescriptionEn(null);
    skillDTO.setDescriptionFr(null);
    skillDTO.setDescriptionEs(null);
    Set<ConstraintViolation<SkillDTO>> violations = validator.validate(skillDTO);
    assertTrue(
        violations.isEmpty()
            || violations.stream()
                .noneMatch(v -> v.getPropertyPath().toString().contains("description")));
  }

  @Test
  void testEqualsHashCodeToString() {
    SkillDTO another =
        SkillDTO.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Java programming language")
            .descriptionFr("Langage de programmation Java")
            .descriptionEs("Lenguaje de programación Java")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .createdAt(skillDTO.getCreatedAt())
            .updatedAt(skillDTO.getUpdatedAt())
            .build();

    assertEquals(skillDTO, another);
    assertEquals(skillDTO.hashCode(), another.hashCode());
    assertNotNull(skillDTO.toString());
    assertTrue(skillDTO.toString().contains("Java"));
  }

  @Test
  void testEqualsWithFieldDifferences() {
    SkillDTO different =
        SkillDTO.builder()
            .id(2L)
            .nameEn("Python")
            .nameFr("Python")
            .nameEs("Python")
            .descriptionEn("Python programming language")
            .descriptionFr("Langage de programmation Python")
            .descriptionEs("Lenguaje de programación Python")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .build();

    assertNotEquals(skillDTO, different);
  }

  @Test
  void testEqualsWithNullDescriptions() {
    SkillDTO skillDTO1 =
        SkillDTO.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .build();

    SkillDTO skillDTO2 =
        SkillDTO.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .build();

    assertEquals(skillDTO1, skillDTO2);
  }

  @ParameterizedTest
  @MethodSource("provideProficiencyLevels")
  void testEqualsWithDifferentProficiencyLevel(String proficiencyLevel) {
    LocalDateTime now = LocalDateTime.now();
    SkillDTO testDTO =
        SkillDTO.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Java programming language")
            .descriptionFr("Langage de programmation Java")
            .descriptionEs("Lenguaje de programación Java")
            .proficiencyLevel(proficiencyLevel)
            .category("Backend")
            .yearsOfExperience(10)
            .createdAt(now)
            .updatedAt(now)
            .build();

    SkillDTO baseDTO =
        SkillDTO.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Java programming language")
            .descriptionFr("Langage de programmation Java")
            .descriptionEs("Lenguaje de programación Java")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .createdAt(now)
            .updatedAt(now)
            .build();

    if (!proficiencyLevel.equals("Expert")) {
      assertNotEquals(baseDTO, testDTO);
    } else {
      assertEquals(baseDTO, testDTO);
    }
  }

  @ParameterizedTest
  @MethodSource("provideYearsOfExperience")
  void testEqualsWithDifferentYearsOfExperience(Integer yearsOfExperience) {
    LocalDateTime now = LocalDateTime.now();
    SkillDTO testDTO =
        SkillDTO.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Java programming language")
            .descriptionFr("Langage de programmation Java")
            .descriptionEs("Lenguaje de programación Java")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(yearsOfExperience)
            .createdAt(now)
            .updatedAt(now)
            .build();

    SkillDTO baseDTO =
        SkillDTO.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Java programming language")
            .descriptionFr("Langage de programmation Java")
            .descriptionEs("Lenguaje de programación Java")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .createdAt(now)
            .updatedAt(now)
            .build();

    if (!yearsOfExperience.equals(10)) {
      assertNotEquals(baseDTO, testDTO);
    } else {
      assertEquals(baseDTO, testDTO);
    }
  }

  private static Stream<String> provideProficiencyLevels() {
    return Stream.of("Beginner", "Intermediate", "Advanced", "Expert");
  }

  private static Stream<Integer> provideYearsOfExperience() {
    return Stream.of(0, 1, 5, 10, 20);
  }
}
