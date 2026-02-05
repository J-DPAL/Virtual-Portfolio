package com.portfolio.projects.dataAccessLayer.entity;

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

@DisplayName("Project Entity Tests")
class ProjectEntityTest {

  @Test
  @DisplayName("Should create entity with no-args constructor")
  void testNoArgsConstructor() {
    Project project = new Project();
    assertNotNull(project);
  }

  @Test
  @DisplayName("Should create entity with all-args constructor")
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    Project project =
        new Project(
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

    assertEquals(1L, project.getId());
    assertEquals("Title EN", project.getTitleEn());
    assertEquals("Title FR", project.getTitleFr());
    assertEquals("Title ES", project.getTitleEs());
    assertEquals("Desc EN", project.getDescriptionEn());
    assertEquals("Desc FR", project.getDescriptionFr());
    assertEquals("Desc ES", project.getDescriptionEs());
    assertEquals("Java,Spring", project.getTechnologies());
    assertEquals("https://example.com", project.getProjectUrl());
    assertEquals("https://github.com/example", project.getGithubUrl());
    assertEquals("https://example.com/image.png", project.getImageUrl());
    assertEquals(LocalDate.of(2020, 1, 1), project.getStartDate());
    assertEquals(LocalDate.of(2021, 1, 1), project.getEndDate());
    assertEquals("Completed", project.getStatus());
    assertEquals(now, project.getCreatedAt());
    assertEquals(now, project.getUpdatedAt());
  }

  @Test
  @DisplayName("Should create entity using builder")
  void testBuilder() {
    Project project =
        Project.builder()
            .id(2L)
            .titleEn("Builder Title")
            .titleFr("Builder Title FR")
            .titleEs("Builder Title ES")
            .status("Active")
            .startDate(LocalDate.of(2022, 5, 1))
            .build();

    assertEquals(2L, project.getId());
    assertEquals("Builder Title", project.getTitleEn());
    assertEquals("Builder Title FR", project.getTitleFr());
    assertEquals("Builder Title ES", project.getTitleEs());
    assertEquals("Active", project.getStatus());
    assertEquals(LocalDate.of(2022, 5, 1), project.getStartDate());
  }

  @Test
  @DisplayName("Should set and get all fields")
  void testGettersAndSetters() {
    Project project = new Project();

    project.setId(5L);
    project.setTitleEn("Title EN");
    project.setTitleFr("Title FR");
    project.setTitleEs("Title ES");
    project.setDescriptionEn("Desc EN");
    project.setDescriptionFr("Desc FR");
    project.setDescriptionEs("Desc ES");
    project.setTechnologies("Java,Spring");
    project.setProjectUrl("https://example.com");
    project.setGithubUrl("https://github.com/example");
    project.setImageUrl("https://example.com/image.png");
    project.setStartDate(LocalDate.of(2019, 6, 1));
    project.setEndDate(LocalDate.of(2020, 6, 1));
    project.setStatus("In Progress");
    project.setCreatedAt(LocalDateTime.of(2023, 1, 1, 10, 0));
    project.setUpdatedAt(LocalDateTime.of(2023, 1, 2, 10, 0));

    assertEquals(5L, project.getId());
    assertEquals("Title EN", project.getTitleEn());
    assertEquals("Title FR", project.getTitleFr());
    assertEquals("Title ES", project.getTitleEs());
    assertEquals("Desc EN", project.getDescriptionEn());
    assertEquals("Desc FR", project.getDescriptionFr());
    assertEquals("Desc ES", project.getDescriptionEs());
    assertEquals("Java,Spring", project.getTechnologies());
    assertEquals("https://example.com", project.getProjectUrl());
    assertEquals("https://github.com/example", project.getGithubUrl());
    assertEquals("https://example.com/image.png", project.getImageUrl());
    assertEquals(LocalDate.of(2019, 6, 1), project.getStartDate());
    assertEquals(LocalDate.of(2020, 6, 1), project.getEndDate());
    assertEquals("In Progress", project.getStatus());
    assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), project.getCreatedAt());
    assertEquals(LocalDateTime.of(2023, 1, 2, 10, 0), project.getUpdatedAt());
  }

  @Test
  @DisplayName("Should handle null optional fields")
  void testNullOptionalFields() {
    Project project = new Project();
    project.setDescriptionEn(null);
    project.setDescriptionFr(null);
    project.setDescriptionEs(null);
    project.setTechnologies(null);
    project.setProjectUrl(null);
    project.setGithubUrl(null);
    project.setImageUrl(null);
    project.setStartDate(null);
    project.setEndDate(null);

    assertNull(project.getDescriptionEn());
    assertNull(project.getDescriptionFr());
    assertNull(project.getDescriptionEs());
    assertNull(project.getTechnologies());
    assertNull(project.getProjectUrl());
    assertNull(project.getGithubUrl());
    assertNull(project.getImageUrl());
    assertNull(project.getStartDate());
    assertNull(project.getEndDate());
  }

  @Test
  @DisplayName("Should set timestamps on create")
  void testOnCreate() {
    Project project = new Project();

    project.onCreate();

    assertNotNull(project.getCreatedAt());
    assertNotNull(project.getUpdatedAt());
  }

  @Test
  @DisplayName("Should update updatedAt on update")
  void testOnUpdate() {
    Project project = new Project();
    LocalDateTime oldUpdatedAt = LocalDateTime.now().minusDays(1);
    project.setUpdatedAt(oldUpdatedAt);

    project.onUpdate();

    assertNotNull(project.getUpdatedAt());
    assertTrue(project.getUpdatedAt().isAfter(oldUpdatedAt));
  }

  @Test
  @DisplayName("Should implement equals, hashCode, and toString")
  void testEqualsHashCodeToString() {
    Project project1 = createBaseProject();
    Project project2 = createBaseProject();
    Project project3 = createBaseProject();
    project3.setId(11L);

    assertEquals(project1, project1);
    assertEquals(project1, project2);
    assertEquals(project1.hashCode(), project2.hashCode());
    assertNotEquals(project1, project3);
    assertNotEquals(project1, null);
    assertNotEquals(project1, "not-a-project");
    assertNotNull(project1.toString());
  }

  @Test
  @DisplayName("Should consider objects equal when optional fields are null")
  void testEqualsWithNullOptionalFields() {
    Project project1 = createBaseProject();
    project1.setDescriptionEn(null);
    project1.setDescriptionFr(null);
    project1.setDescriptionEs(null);
    project1.setTechnologies(null);
    project1.setProjectUrl(null);
    project1.setGithubUrl(null);
    project1.setImageUrl(null);
    project1.setStartDate(null);
    project1.setEndDate(null);

    Project project2 = createBaseProject();
    project2.setDescriptionEn(null);
    project2.setDescriptionFr(null);
    project2.setDescriptionEs(null);
    project2.setTechnologies(null);
    project2.setProjectUrl(null);
    project2.setGithubUrl(null);
    project2.setImageUrl(null);
    project2.setStartDate(null);
    project2.setEndDate(null);

    assertEquals(project1, project2);
    assertEquals(project1.hashCode(), project2.hashCode());
  }

  @Test
  @DisplayName("Should not equal when base optional fields are null and other has values")
  void testEqualsWithNullOnBaseOnly() {
    Project project1 = createBaseProject();
    project1.setDescriptionEn(null);
    project1.setProjectUrl(null);

    Project project2 = createBaseProject();

    assertNotEquals(project1, project2);
  }

  @ParameterizedTest(name = "Should not equal when {0} differs")
  @MethodSource("projectMismatchCases")
  void testEqualsWithFieldDifferences(String label, Consumer<Project> modifier) {
    Project base = createBaseProject();
    Project other = createBaseProject();

    modifier.accept(other);

    assertNotEquals(base, other, label);
  }

  private static Stream<Arguments> projectMismatchCases() {
    return Stream.of(
        Arguments.of("id", (Consumer<Project>) p -> p.setId(99L)),
        Arguments.of("titleEn", (Consumer<Project>) p -> p.setTitleEn("Other")),
        Arguments.of("titleFr", (Consumer<Project>) p -> p.setTitleFr("Other")),
        Arguments.of("titleEs", (Consumer<Project>) p -> p.setTitleEs("Other")),
        Arguments.of("descriptionEn", (Consumer<Project>) p -> p.setDescriptionEn(null)),
        Arguments.of("descriptionFr", (Consumer<Project>) p -> p.setDescriptionFr(null)),
        Arguments.of("descriptionEs", (Consumer<Project>) p -> p.setDescriptionEs(null)),
        Arguments.of("technologies", (Consumer<Project>) p -> p.setTechnologies("Other")),
        Arguments.of("projectUrl", (Consumer<Project>) p -> p.setProjectUrl("https://other.com")),
        Arguments.of(
            "githubUrl", (Consumer<Project>) p -> p.setGithubUrl("https://github.com/other")),
        Arguments.of(
            "imageUrl", (Consumer<Project>) p -> p.setImageUrl("https://other.com/image.png")),
        Arguments.of(
            "startDate", (Consumer<Project>) p -> p.setStartDate(LocalDate.of(2024, 1, 1))),
        Arguments.of("endDate", (Consumer<Project>) p -> p.setEndDate(null)),
        Arguments.of("status", (Consumer<Project>) p -> p.setStatus("Archived")),
        Arguments.of(
            "createdAt",
            (Consumer<Project>) p -> p.setCreatedAt(LocalDateTime.of(2024, 1, 1, 1, 1))),
        Arguments.of(
            "updatedAt",
            (Consumer<Project>) p -> p.setUpdatedAt(LocalDateTime.of(2024, 1, 2, 1, 1))));
  }

  private static Project createBaseProject() {
    return Project.builder()
        .id(10L)
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
