package com.portfolio.experience.dataAccessLayer.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WorkExperience Entity Tests")
class WorkExperienceEntityTest {

  @Test
  @DisplayName("Should create entity with no-args constructor")
  void testNoArgsConstructor() {
    WorkExperience experience = new WorkExperience();
    assertNotNull(experience);
  }

  @Test
  @DisplayName("Should create entity with all-args constructor")
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    WorkExperience experience =
        new WorkExperience(
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
            now);

    assertEquals(1L, experience.getId());
    assertEquals("Company EN", experience.getCompanyNameEn());
    assertEquals("Company FR", experience.getCompanyNameFr());
    assertEquals("Company ES", experience.getCompanyNameEs());
    assertEquals("Position EN", experience.getPositionEn());
    assertEquals("Position FR", experience.getPositionFr());
    assertEquals("Position ES", experience.getPositionEs());
    assertEquals("Description EN", experience.getDescriptionEn());
    assertEquals("Description FR", experience.getDescriptionFr());
    assertEquals("Description ES", experience.getDescriptionEs());
    assertEquals("Location EN", experience.getLocationEn());
    assertEquals("Location FR", experience.getLocationFr());
    assertEquals("Location ES", experience.getLocationEs());
    assertEquals(LocalDate.of(2020, 1, 1), experience.getStartDate());
    assertEquals(LocalDate.of(2021, 1, 1), experience.getEndDate());
    assertFalse(experience.getIsCurrent());
    assertEquals(now, experience.getCreatedAt());
    assertEquals(now, experience.getUpdatedAt());
  }

  @Test
  @DisplayName("Should create entity using builder")
  void testBuilder() {
    WorkExperience experience =
        WorkExperience.builder()
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

    assertEquals(2L, experience.getId());
    assertEquals("Builder Company", experience.getCompanyNameEn());
    assertEquals("Builder Company FR", experience.getCompanyNameFr());
    assertEquals("Builder Company ES", experience.getCompanyNameEs());
    assertEquals("Engineer", experience.getPositionEn());
    assertEquals("Ingénieur", experience.getPositionFr());
    assertEquals("Ingeniero", experience.getPositionEs());
    assertEquals(LocalDate.of(2022, 5, 1), experience.getStartDate());
    assertTrue(experience.getIsCurrent());
  }

  @Test
  @DisplayName("Should set and get all fields")
  void testGettersAndSetters() {
    WorkExperience experience = new WorkExperience();

    experience.setId(5L);
    experience.setCompanyNameEn("Company EN");
    experience.setCompanyNameFr("Company FR");
    experience.setCompanyNameEs("Company ES");
    experience.setPositionEn("Position EN");
    experience.setPositionFr("Position FR");
    experience.setPositionEs("Position ES");
    experience.setDescriptionEn("Description EN");
    experience.setDescriptionFr("Description FR");
    experience.setDescriptionEs("Description ES");
    experience.setLocationEn("Location EN");
    experience.setLocationFr("Location FR");
    experience.setLocationEs("Location ES");
    experience.setStartDate(LocalDate.of(2019, 6, 1));
    experience.setEndDate(LocalDate.of(2020, 6, 1));
    experience.setIsCurrent(false);
    experience.setCreatedAt(LocalDateTime.of(2023, 1, 1, 10, 0));
    experience.setUpdatedAt(LocalDateTime.of(2023, 1, 2, 10, 0));

    assertEquals(5L, experience.getId());
    assertEquals("Company EN", experience.getCompanyNameEn());
    assertEquals("Company FR", experience.getCompanyNameFr());
    assertEquals("Company ES", experience.getCompanyNameEs());
    assertEquals("Position EN", experience.getPositionEn());
    assertEquals("Position FR", experience.getPositionFr());
    assertEquals("Position ES", experience.getPositionEs());
    assertEquals("Description EN", experience.getDescriptionEn());
    assertEquals("Description FR", experience.getDescriptionFr());
    assertEquals("Description ES", experience.getDescriptionEs());
    assertEquals("Location EN", experience.getLocationEn());
    assertEquals("Location FR", experience.getLocationFr());
    assertEquals("Location ES", experience.getLocationEs());
    assertEquals(LocalDate.of(2019, 6, 1), experience.getStartDate());
    assertEquals(LocalDate.of(2020, 6, 1), experience.getEndDate());
    assertFalse(experience.getIsCurrent());
    assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), experience.getCreatedAt());
    assertEquals(LocalDateTime.of(2023, 1, 2, 10, 0), experience.getUpdatedAt());
  }

  @Test
  @DisplayName("Should handle null optional fields")
  void testNullOptionalFields() {
    WorkExperience experience = new WorkExperience();
    experience.setDescriptionEn(null);
    experience.setDescriptionFr(null);
    experience.setDescriptionEs(null);
    experience.setLocationEn(null);
    experience.setLocationFr(null);
    experience.setLocationEs(null);
    experience.setEndDate(null);

    assertNull(experience.getDescriptionEn());
    assertNull(experience.getDescriptionFr());
    assertNull(experience.getDescriptionEs());
    assertNull(experience.getLocationEn());
    assertNull(experience.getLocationFr());
    assertNull(experience.getLocationEs());
    assertNull(experience.getEndDate());
  }

  @Test
  @DisplayName("Should set timestamps and default isCurrent on create")
  void testOnCreate() {
    WorkExperience experience = new WorkExperience();
    experience.setIsCurrent(null);

    experience.onCreate();

    assertNotNull(experience.getCreatedAt());
    assertNotNull(experience.getUpdatedAt());
    assertFalse(experience.getIsCurrent());
  }

  @Test
  @DisplayName("Should update updatedAt on update")
  void testOnUpdate() {
    WorkExperience experience = new WorkExperience();
    LocalDateTime oldUpdatedAt = LocalDateTime.now().minusDays(1);
    experience.setUpdatedAt(oldUpdatedAt);

    experience.onUpdate();

    assertNotNull(experience.getUpdatedAt());
    assertTrue(experience.getUpdatedAt().isAfter(oldUpdatedAt));
  }

  @Test
  @DisplayName("Should implement equals, hashCode, and toString")
  void testEqualsHashCodeToString() {
    WorkExperience experience1 = createBaseEntity();
    WorkExperience experience2 = createBaseEntity();
    WorkExperience experience3 = createBaseEntity();
    experience3.setId(11L);

    assertEquals(experience1, experience2);
    assertEquals(experience1.hashCode(), experience2.hashCode());
    assertNotEquals(experience1, experience3);
    assertNotEquals(experience1, null);
    assertNotEquals(experience1, "not-a-work-experience");
    assertNotNull(experience1.toString());
  }

  @ParameterizedTest(name = "Should not equal when {0} differs")
  @MethodSource("entityMismatchCases")
  void testEqualsWithFieldDifferences(String label, Consumer<WorkExperience> modifier) {
    WorkExperience base = createBaseEntity();
    WorkExperience other = createBaseEntity();

    modifier.accept(other);

    assertNotEquals(base, other, label);
  }

  private static Stream<Arguments> entityMismatchCases() {
    return Stream.of(
        Arguments.of("id", (Consumer<WorkExperience>) e -> e.setId(99L)),
        Arguments.of("companyNameEn", (Consumer<WorkExperience>) e -> e.setCompanyNameEn("Other")),
        Arguments.of("companyNameFr", (Consumer<WorkExperience>) e -> e.setCompanyNameFr("Other")),
        Arguments.of("companyNameEs", (Consumer<WorkExperience>) e -> e.setCompanyNameEs("Other")),
        Arguments.of("positionEn", (Consumer<WorkExperience>) e -> e.setPositionEn("Other")),
        Arguments.of("positionFr", (Consumer<WorkExperience>) e -> e.setPositionFr("Other")),
        Arguments.of("positionEs", (Consumer<WorkExperience>) e -> e.setPositionEs("Other")),
        Arguments.of("descriptionEn", (Consumer<WorkExperience>) e -> e.setDescriptionEn(null)),
        Arguments.of("descriptionFr", (Consumer<WorkExperience>) e -> e.setDescriptionFr(null)),
        Arguments.of("descriptionEs", (Consumer<WorkExperience>) e -> e.setDescriptionEs(null)),
        Arguments.of("locationEn", (Consumer<WorkExperience>) e -> e.setLocationEn("Other")),
        Arguments.of("locationFr", (Consumer<WorkExperience>) e -> e.setLocationFr("Other")),
        Arguments.of("locationEs", (Consumer<WorkExperience>) e -> e.setLocationEs("Other")),
        Arguments.of(
            "startDate", (Consumer<WorkExperience>) e -> e.setStartDate(LocalDate.of(2024, 1, 1))),
        Arguments.of("endDate", (Consumer<WorkExperience>) e -> e.setEndDate(null)),
        Arguments.of("isCurrent", (Consumer<WorkExperience>) e -> e.setIsCurrent(true)),
        Arguments.of(
            "createdAt",
            (Consumer<WorkExperience>) e -> e.setCreatedAt(LocalDateTime.of(2024, 1, 1, 1, 1))),
        Arguments.of(
            "updatedAt",
            (Consumer<WorkExperience>) e -> e.setUpdatedAt(LocalDateTime.of(2024, 1, 2, 1, 1))));
  }

  private static WorkExperience createBaseEntity() {
    return WorkExperience.builder()
        .id(10L)
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
        .build();
  }
}
