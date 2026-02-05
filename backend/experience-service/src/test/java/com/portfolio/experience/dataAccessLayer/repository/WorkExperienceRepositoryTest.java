package com.portfolio.experience.dataAccessLayer.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.experience.dataAccessLayer.entity.WorkExperience;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("WorkExperienceRepository Integration Tests")
class WorkExperienceRepositoryTest {

  @Autowired private WorkExperienceRepository workExperienceRepository;

  private WorkExperience testExperience;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data and clear repository
    workExperienceRepository.deleteAll();
    testExperience =
        WorkExperience.builder()
            .companyNameEn("Tech Corp")
            .companyNameFr("Tech Corp")
            .companyNameEs("Tech Corp")
            .positionEn("Developer")
            .positionFr("Développeur")
            .positionEs("Desarrollador")
            .descriptionEn("Worked on backend services")
            .descriptionFr("Services backend")
            .descriptionEs("Servicios backend")
            .locationEn("Remote")
            .locationFr("Télétravail")
            .locationEs("Remoto")
            .startDate(LocalDate.of(2022, 1, 1))
            .endDate(LocalDate.of(2023, 12, 31))
            .isCurrent(false)
            .build();
  }

  @Test
  @DisplayName("Should save a new experience successfully")
  void testSaveExperience_WithValidExperience_ReturnsSavedExperience() {
    // Arrange: Experience entity is created in setUp

    // Act: Save experience
    WorkExperience savedExperience = workExperienceRepository.save(testExperience);

    // Assert: Verify experience is saved with generated ID
    assertNotNull(savedExperience.getId());
    assertEquals("Tech Corp", savedExperience.getCompanyNameEn());
  }

  @Test
  @DisplayName("Should find experience by ID successfully")
  void testFindById_WithExistingExperienceId_ReturnsExperience() {
    // Arrange: Save experience first
    WorkExperience savedExperience = workExperienceRepository.save(testExperience);

    // Act: Find experience by ID
    Optional<WorkExperience> foundExperience =
        workExperienceRepository.findById(savedExperience.getId());

    // Assert: Verify experience is found
    assertTrue(foundExperience.isPresent());
  }

  @Test
  @DisplayName("Should return empty Optional when experience ID does not exist")
  void testFindById_WithNonExistentExperienceId_ReturnsEmptyOptional() {
    // Arrange: Experience not saved

    // Act: Find experience by ID
    Optional<WorkExperience> foundExperience = workExperienceRepository.findById(999L);

    // Assert: Verify Optional is empty
    assertFalse(foundExperience.isPresent());
  }

  @Test
  @DisplayName("Should find current experiences")
  void testFindByIsCurrent_WithCurrentExperience_ReturnsCurrentRecords() {
    // Arrange: Save current experience
    testExperience.setIsCurrent(true);
    workExperienceRepository.save(testExperience);

    // Act: Find current experiences
    List<WorkExperience> currentExperiences = workExperienceRepository.findByIsCurrent(true);

    // Assert: Verify results
    assertNotNull(currentExperiences);
    assertTrue(currentExperiences.size() > 0);
    assertTrue(currentExperiences.get(0).getIsCurrent());
  }

  @Test
  @DisplayName("Should return empty list when no current experiences")
  void testFindByIsCurrent_WithNoCurrentExperiences_ReturnsEmptyList() {
    // Arrange: Save non-current experience
    workExperienceRepository.save(testExperience);

    // Act: Find current experiences
    List<WorkExperience> currentExperiences = workExperienceRepository.findByIsCurrent(true);

    // Assert: Verify empty list
    assertNotNull(currentExperiences);
    assertTrue(currentExperiences.isEmpty());
  }

  @Test
  @DisplayName("Should find experiences ordered by start date descending")
  void testFindByOrderByStartDateDesc_ReturnsOrderedExperiences() {
    // Arrange: Save two experiences with different start dates
    WorkExperience exp1 = testExperience;
    WorkExperience exp2 =
        WorkExperience.builder()
            .companyNameEn("Second Corp")
            .companyNameFr("Second Corp")
            .companyNameEs("Second Corp")
            .positionEn("Engineer")
            .positionFr("Ingénieur")
            .positionEs("Ingeniero")
            .startDate(LocalDate.of(2024, 1, 1))
            .isCurrent(false)
            .build();
    workExperienceRepository.save(exp1);
    workExperienceRepository.save(exp2);

    // Act: Retrieve ordered list
    List<WorkExperience> experiences = workExperienceRepository.findByOrderByStartDateDesc();

    // Assert: Verify order
    assertEquals(2, experiences.size());
    assertEquals("Second Corp", experiences.get(0).getCompanyNameEn());
  }

  @Test
  @DisplayName("Should update experience successfully")
  void testUpdateExperience_WithValidChanges_ReturnUpdatedExperience() {
    // Arrange: Save experience first
    WorkExperience savedExperience = workExperienceRepository.save(testExperience);

    // Act: Update experience details
    savedExperience.setCompanyNameEn("Updated Corp");
    WorkExperience updatedExperience = workExperienceRepository.save(savedExperience);

    // Assert: Verify update
    assertEquals(savedExperience.getId(), updatedExperience.getId());
    assertEquals("Updated Corp", updatedExperience.getCompanyNameEn());
  }

  @Test
  @DisplayName("Should delete experience successfully")
  void testDeleteExperience_WithExistingExperience_ExperienceIsRemoved() {
    // Arrange: Save experience first
    WorkExperience savedExperience = workExperienceRepository.save(testExperience);
    Long experienceId = savedExperience.getId();

    // Act: Delete experience
    workExperienceRepository.deleteById(experienceId);

    // Assert: Verify deletion
    Optional<WorkExperience> deletedExperience = workExperienceRepository.findById(experienceId);
    assertFalse(deletedExperience.isPresent());
  }
}
