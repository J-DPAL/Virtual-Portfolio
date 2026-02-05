package com.portfolio.education.dataAccessLayer.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Education Entity Tests")
class EducationEntityTest {

  private Education education;

  @BeforeEach
  void setUp() {
    education = new Education();
  }

  @Test
  @DisplayName("Should create Education with no-args constructor")
  void testNoArgsConstructor() {
    assertNotNull(education);
  }

  @Test
  @DisplayName("Should create Education with all-args constructor")
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    Education edu =
        new Education(
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

    assertNotNull(edu);
    assertEquals(1L, edu.getId());
    assertEquals("Harvard University", edu.getInstitutionNameEn());
  }

  @Test
  @DisplayName("Should create Education using builder")
  void testBuilder() {
    Education edu =
        Education.builder()
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

    assertNotNull(edu);
    assertEquals("MIT", edu.getInstitutionNameEn());
    assertTrue(edu.getIsCurrent());
  }

  @Test
  @DisplayName("Should set and get all fields correctly")
  void testSettersAndGetters() {
    LocalDate startDate = LocalDate.of(2020, 9, 1);
    LocalDate endDate = LocalDate.of(2024, 6, 1);
    BigDecimal gpa = new BigDecimal("3.50");

    education.setId(1L);
    education.setInstitutionNameEn("Stanford University");
    education.setInstitutionNameFr("Université de Stanford");
    education.setInstitutionNameEs("Universidad de Stanford");
    education.setDegreeEn("PhD");
    education.setDegreeFr("Doctorat");
    education.setDegreeEs("Doctorado");
    education.setFieldOfStudyEn("Machine Learning");
    education.setFieldOfStudyFr("Apprentissage automatique");
    education.setFieldOfStudyEs("Aprendizaje automático");
    education.setDescriptionEn("English description");
    education.setDescriptionFr("Description française");
    education.setDescriptionEs("Descripción española");
    education.setStartDate(startDate);
    education.setEndDate(endDate);
    education.setIsCurrent(false);
    education.setGpa(gpa);

    assertEquals(1L, education.getId());
    assertEquals("Stanford University", education.getInstitutionNameEn());
    assertEquals("Université de Stanford", education.getInstitutionNameFr());
    assertEquals("Universidad de Stanford", education.getInstitutionNameEs());
    assertEquals("PhD", education.getDegreeEn());
    assertEquals("Doctorat", education.getDegreeFr());
    assertEquals("Doctorado", education.getDegreeEs());
    assertEquals("Machine Learning", education.getFieldOfStudyEn());
    assertEquals("Apprentissage automatique", education.getFieldOfStudyFr());
    assertEquals("Aprendizaje automático", education.getFieldOfStudyEs());
    assertEquals("English description", education.getDescriptionEn());
    assertEquals("Description française", education.getDescriptionFr());
    assertEquals("Descripción española", education.getDescriptionEs());
    assertEquals(startDate, education.getStartDate());
    assertEquals(endDate, education.getEndDate());
    assertFalse(education.getIsCurrent());
    assertEquals(gpa, education.getGpa());
  }

  @Test
  @DisplayName("Should set timestamps on prePersist")
  void testPrePersist() {
    education.setInstitutionNameEn("Test University");
    education.setInstitutionNameFr("Université de Test");
    education.setInstitutionNameEs("Universidad de Prueba");
    education.setDegreeEn("BSc");
    education.setDegreeFr("Licence");
    education.setDegreeEs("Licenciatura");
    education.setFieldOfStudyEn("CS");
    education.setFieldOfStudyFr("Informatique");
    education.setFieldOfStudyEs("Informática");
    education.setStartDate(LocalDate.now());

    education.onCreate();

    assertNotNull(education.getCreatedAt());
    assertNotNull(education.getUpdatedAt());
  }

  @Test
  @DisplayName("Should set isCurrent to false if null on prePersist")
  void testPrePersist_SetsIsCurrentToFalse() {
    education.onCreate();
    assertNotNull(education.getIsCurrent());
    assertFalse(education.getIsCurrent());
  }

  @Test
  @DisplayName("Should not override isCurrent if already set on prePersist")
  void testPrePersist_DoesNotOverrideIsCurrent() {
    education.setIsCurrent(true);
    education.onCreate();
    assertTrue(education.getIsCurrent());
  }

  @Test
  @DisplayName("Should update timestamp on preUpdate")
  void testPreUpdate() {
    LocalDateTime oldTime = LocalDateTime.now().minusDays(1);
    education.setCreatedAt(oldTime);
    education.setUpdatedAt(oldTime);

    education.onUpdate();

    assertNotNull(education.getUpdatedAt());
    assertTrue(education.getUpdatedAt().isAfter(oldTime));
  }

  @Test
  @DisplayName("Should handle null description fields")
  void testNullDescriptions() {
    education.setDescriptionEn(null);
    education.setDescriptionFr(null);
    education.setDescriptionEs(null);

    assertNull(education.getDescriptionEn());
    assertNull(education.getDescriptionFr());
    assertNull(education.getDescriptionEs());
  }

  @Test
  @DisplayName("Should handle null endDate for current education")
  void testNullEndDateForCurrentEducation() {
    education.setIsCurrent(true);
    education.setEndDate(null);

    assertNull(education.getEndDate());
    assertTrue(education.getIsCurrent());
  }

  @Test
  @DisplayName("Should handle null GPA")
  void testNullGpa() {
    education.setGpa(null);
    assertNull(education.getGpa());
  }

  @Test
  @DisplayName("Should test equals and hashCode")
  void testEqualsAndHashCode() {
    Education edu1 =
        Education.builder()
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

    Education edu2 =
        Education.builder()
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

    assertEquals(edu1, edu2);
    assertEquals(edu1.hashCode(), edu2.hashCode());
  }

  @Test
  @DisplayName("Should test toString method")
  void testToString() {
    education.setInstitutionNameEn("Test University");
    education.setDegreeEn("BSc");

    String toString = education.toString();
    assertNotNull(toString);
    assertTrue(toString.contains("Test University"));
  }
}
