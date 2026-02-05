package com.portfolio.education.mappingLayer.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EducationDTO Tests")
class EducationDTOTest {

  private Validator validator;
  private EducationDTO educationDTO;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    educationDTO = new EducationDTO();
  }

  @Test
  @DisplayName("Should create EducationDTO with no-args constructor")
  void testNoArgsConstructor() {
    assertNotNull(educationDTO);
  }

  @Test
  @DisplayName("Should create EducationDTO with all-args constructor")
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    EducationDTO dto =
        new EducationDTO(
            1L,
            "Harvard University",
            "Université Harvard",
            "Universidad de Harvard",
            "Bachelor of Science",
            "Licence ès sciences",
            "Licenciatura en Ciencias",
            "Computer Science",
            "Informatique",
            "Informática",
            "Description in English",
            "Description en français",
            "Descripción en español",
            LocalDate.of(2020, 9, 1),
            LocalDate.of(2024, 6, 1),
            false,
            new BigDecimal("3.75"),
            now,
            now);

    assertNotNull(dto);
    assertEquals(1L, dto.getId());
    assertEquals("Harvard University", dto.getInstitutionNameEn());
  }

  @Test
  @DisplayName("Should create EducationDTO using builder")
  void testBuilder() {
    EducationDTO dto =
        EducationDTO.builder()
            .id(1L)
            .institutionNameEn("MIT")
            .institutionNameFr("MIT")
            .institutionNameEs("MIT")
            .degreeEn("Master of Science")
            .degreeFr("Master ès sciences")
            .degreeEs("Maestría en Ciencias")
            .fieldOfStudyEn("AI")
            .fieldOfStudyFr("IA")
            .fieldOfStudyEs("IA")
            .startDate(LocalDate.of(2024, 9, 1))
            .isCurrent(true)
            .gpa(new BigDecimal("4.00"))
            .build();

    assertNotNull(dto);
    assertEquals("MIT", dto.getInstitutionNameEn());
    assertTrue(dto.getIsCurrent());
  }

  @Test
  @DisplayName("Should set and get all fields correctly")
  void testSettersAndGetters() {
    LocalDate startDate = LocalDate.of(2020, 9, 1);
    LocalDate endDate = LocalDate.of(2024, 6, 1);
    LocalDateTime now = LocalDateTime.now();
    BigDecimal gpa = new BigDecimal("3.50");

    educationDTO.setId(1L);
    educationDTO.setInstitutionNameEn("Stanford University");
    educationDTO.setInstitutionNameFr("Université de Stanford");
    educationDTO.setInstitutionNameEs("Universidad de Stanford");
    educationDTO.setDegreeEn("PhD");
    educationDTO.setDegreeFr("Doctorat");
    educationDTO.setDegreeEs("Doctorado");
    educationDTO.setFieldOfStudyEn("Machine Learning");
    educationDTO.setFieldOfStudyFr("Apprentissage automatique");
    educationDTO.setFieldOfStudyEs("Aprendizaje automático");
    educationDTO.setDescriptionEn("English description");
    educationDTO.setDescriptionFr("Description française");
    educationDTO.setDescriptionEs("Descripción española");
    educationDTO.setStartDate(startDate);
    educationDTO.setEndDate(endDate);
    educationDTO.setIsCurrent(false);
    educationDTO.setGpa(gpa);
    educationDTO.setCreatedAt(now);
    educationDTO.setUpdatedAt(now);

    assertEquals(1L, educationDTO.getId());
    assertEquals("Stanford University", educationDTO.getInstitutionNameEn());
    assertEquals("Université de Stanford", educationDTO.getInstitutionNameFr());
    assertEquals("Universidad de Stanford", educationDTO.getInstitutionNameEs());
    assertEquals("PhD", educationDTO.getDegreeEn());
    assertEquals("Doctorat", educationDTO.getDegreeFr());
    assertEquals("Doctorado", educationDTO.getDegreeEs());
    assertEquals("Machine Learning", educationDTO.getFieldOfStudyEn());
    assertEquals("Apprentissage automatique", educationDTO.getFieldOfStudyFr());
    assertEquals("Aprendizaje automático", educationDTO.getFieldOfStudyEs());
    assertEquals("English description", educationDTO.getDescriptionEn());
    assertEquals("Description française", educationDTO.getDescriptionFr());
    assertEquals("Descripción española", educationDTO.getDescriptionEs());
    assertEquals(startDate, educationDTO.getStartDate());
    assertEquals(endDate, educationDTO.getEndDate());
    assertFalse(educationDTO.getIsCurrent());
    assertEquals(gpa, educationDTO.getGpa());
    assertEquals(now, educationDTO.getCreatedAt());
    assertEquals(now, educationDTO.getUpdatedAt());
  }

  @Test
  @DisplayName("Should validate valid EducationDTO")
  void testValidation_ValidDTO() {
    EducationDTO dto =
        EducationDTO.builder()
            .institutionNameEn("University")
            .institutionNameFr("Université")
            .institutionNameEs("Universidad")
            .degreeEn("BSc")
            .degreeFr("Licence")
            .degreeEs("Licenciatura")
            .fieldOfStudyEn("CS")
            .fieldOfStudyFr("Informatique")
            .fieldOfStudyEs("Informática")
            .startDate(LocalDate.now())
            .isCurrent(true)
            .gpa(new BigDecimal("3.75"))
            .build();

    Set<ConstraintViolation<EducationDTO>> violations = validator.validate(dto);
    assertTrue(violations.isEmpty());
  }

  @Test
  @DisplayName("Should fail validation when institutionNameEn is blank")
  void testValidation_BlankInstitutionNameEn() {
    educationDTO.setInstitutionNameEn("");
    educationDTO.setInstitutionNameFr("Université");
    educationDTO.setInstitutionNameEs("Universidad");
    educationDTO.setDegreeEn("BSc");
    educationDTO.setDegreeFr("Licence");
    educationDTO.setDegreeEs("Licenciatura");
    educationDTO.setFieldOfStudyEn("CS");
    educationDTO.setFieldOfStudyFr("Informatique");
    educationDTO.setFieldOfStudyEs("Informática");
    educationDTO.setStartDate(LocalDate.now());
    educationDTO.setIsCurrent(true);

    Set<ConstraintViolation<EducationDTO>> violations = validator.validate(educationDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream()
            .anyMatch(v -> v.getMessage().contains("Institution name in English is required")));
  }

  @Test
  @DisplayName("Should fail validation when institutionNameFr is blank")
  void testValidation_BlankInstitutionNameFr() {
    educationDTO.setInstitutionNameEn("University");
    educationDTO.setInstitutionNameFr("");
    educationDTO.setInstitutionNameEs("Universidad");
    educationDTO.setDegreeEn("BSc");
    educationDTO.setDegreeFr("Licence");
    educationDTO.setDegreeEs("Licenciatura");
    educationDTO.setFieldOfStudyEn("CS");
    educationDTO.setFieldOfStudyFr("Informatique");
    educationDTO.setFieldOfStudyEs("Informática");
    educationDTO.setStartDate(LocalDate.now());
    educationDTO.setIsCurrent(true);

    Set<ConstraintViolation<EducationDTO>> violations = validator.validate(educationDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream()
            .anyMatch(v -> v.getMessage().contains("Institution name in French is required")));
  }

  @Test
  @DisplayName("Should fail validation when institutionNameEs is blank")
  void testValidation_BlankInstitutionNameEs() {
    educationDTO.setInstitutionNameEn("University");
    educationDTO.setInstitutionNameFr("Université");
    educationDTO.setInstitutionNameEs("");
    educationDTO.setDegreeEn("BSc");
    educationDTO.setDegreeFr("Licence");
    educationDTO.setDegreeEs("Licenciatura");
    educationDTO.setFieldOfStudyEn("CS");
    educationDTO.setFieldOfStudyFr("Informatique");
    educationDTO.setFieldOfStudyEs("Informática");
    educationDTO.setStartDate(LocalDate.now());
    educationDTO.setIsCurrent(true);

    Set<ConstraintViolation<EducationDTO>> violations = validator.validate(educationDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream()
            .anyMatch(v -> v.getMessage().contains("Institution name in Spanish is required")));
  }

  @Test
  @DisplayName("Should fail validation when startDate is null")
  void testValidation_NullStartDate() {
    educationDTO.setInstitutionNameEn("University");
    educationDTO.setInstitutionNameFr("Université");
    educationDTO.setInstitutionNameEs("Universidad");
    educationDTO.setDegreeEn("BSc");
    educationDTO.setDegreeFr("Licence");
    educationDTO.setDegreeEs("Licenciatura");
    educationDTO.setFieldOfStudyEn("CS");
    educationDTO.setFieldOfStudyFr("Informatique");
    educationDTO.setFieldOfStudyEs("Informática");
    educationDTO.setStartDate(null);
    educationDTO.setIsCurrent(true);

    Set<ConstraintViolation<EducationDTO>> violations = validator.validate(educationDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> v.getMessage().contains("Start date is required")));
  }

  @Test
  @DisplayName("Should fail validation when isCurrent is null")
  void testValidation_NullIsCurrent() {
    educationDTO.setInstitutionNameEn("University");
    educationDTO.setInstitutionNameFr("Université");
    educationDTO.setInstitutionNameEs("Universidad");
    educationDTO.setDegreeEn("BSc");
    educationDTO.setDegreeFr("Licence");
    educationDTO.setDegreeEs("Licenciatura");
    educationDTO.setFieldOfStudyEn("CS");
    educationDTO.setFieldOfStudyFr("Informatique");
    educationDTO.setFieldOfStudyEs("Informática");
    educationDTO.setStartDate(LocalDate.now());
    educationDTO.setIsCurrent(null);

    Set<ConstraintViolation<EducationDTO>> violations = validator.validate(educationDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> v.getMessage().contains("Current status is required")));
  }

  @Test
  @DisplayName("Should fail validation when GPA is below minimum")
  void testValidation_GpaBelowMinimum() {
    educationDTO.setInstitutionNameEn("University");
    educationDTO.setInstitutionNameFr("Université");
    educationDTO.setInstitutionNameEs("Universidad");
    educationDTO.setDegreeEn("BSc");
    educationDTO.setDegreeFr("Licence");
    educationDTO.setDegreeEs("Licenciatura");
    educationDTO.setFieldOfStudyEn("CS");
    educationDTO.setFieldOfStudyFr("Informatique");
    educationDTO.setFieldOfStudyEs("Informática");
    educationDTO.setStartDate(LocalDate.now());
    educationDTO.setIsCurrent(true);
    educationDTO.setGpa(new BigDecimal("-0.01"));

    Set<ConstraintViolation<EducationDTO>> violations = validator.validate(educationDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> v.getMessage().contains("GPA must be at least 0.00")));
  }

  @Test
  @DisplayName("Should fail validation when GPA exceeds maximum")
  void testValidation_GpaAboveMaximum() {
    educationDTO.setInstitutionNameEn("University");
    educationDTO.setInstitutionNameFr("Université");
    educationDTO.setInstitutionNameEs("Universidad");
    educationDTO.setDegreeEn("BSc");
    educationDTO.setDegreeFr("Licence");
    educationDTO.setDegreeEs("Licenciatura");
    educationDTO.setFieldOfStudyEn("CS");
    educationDTO.setFieldOfStudyFr("Informatique");
    educationDTO.setFieldOfStudyEs("Informática");
    educationDTO.setStartDate(LocalDate.now());
    educationDTO.setIsCurrent(true);
    educationDTO.setGpa(new BigDecimal("4.01"));

    Set<ConstraintViolation<EducationDTO>> violations = validator.validate(educationDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> v.getMessage().contains("GPA must not exceed 4.00")));
  }

  @Test
  @DisplayName("Should test equals and hashCode")
  void testEqualsAndHashCode() {
    EducationDTO dto1 =
        EducationDTO.builder()
            .id(1L)
            .institutionNameEn("University")
            .institutionNameFr("Université")
            .institutionNameEs("Universidad")
            .degreeEn("BSc")
            .degreeFr("Licence")
            .degreeEs("Licenciatura")
            .fieldOfStudyEn("CS")
            .fieldOfStudyFr("Informatique")
            .fieldOfStudyEs("Informática")
            .startDate(LocalDate.now())
            .isCurrent(false)
            .build();

    EducationDTO dto2 =
        EducationDTO.builder()
            .id(1L)
            .institutionNameEn("University")
            .institutionNameFr("Université")
            .institutionNameEs("Universidad")
            .degreeEn("BSc")
            .degreeFr("Licence")
            .degreeEs("Licenciatura")
            .fieldOfStudyEn("CS")
            .fieldOfStudyFr("Informatique")
            .fieldOfStudyEs("Informática")
            .startDate(LocalDate.now())
            .isCurrent(false)
            .build();

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  @DisplayName("Should test toString method")
  void testToString() {
    educationDTO.setInstitutionNameEn("Test University");
    educationDTO.setDegreeEn("BSc");

    String toString = educationDTO.toString();
    assertNotNull(toString);
    assertTrue(toString.contains("Test University"));
  }
}
