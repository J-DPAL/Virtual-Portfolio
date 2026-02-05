package com.portfolio.education.dataAccessLayer.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.education.dataAccessLayer.entity.Education;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("EducationRepository Integration Tests")
class EducationRepositoryTest {

  @Autowired private EducationRepository educationRepository;

  private Education testEducation;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data and clear repository
    educationRepository.deleteAll();
    testEducation =
        Education.builder()
            .institutionNameEn("University")
            .institutionNameFr("Université")
            .institutionNameEs("Universidad")
            .degreeEn("BSc")
            .degreeFr("Licence")
            .degreeEs("Licenciatura")
            .fieldOfStudyEn("Computer Science")
            .fieldOfStudyFr("Informatique")
            .fieldOfStudyEs("Informática")
            .startDate(LocalDate.of(2020, 9, 1))
            .endDate(LocalDate.of(2024, 6, 1))
            .isCurrent(false)
            .gpa(new BigDecimal("3.75"))
            .build();
  }

  @Test
  @DisplayName("Should save a new education record successfully")
  void testSaveEducation_WithValidEducation_ReturnsSavedEducation() {
    // Arrange: Education entity is created in setUp

    // Act: Save education
    Education savedEducation = educationRepository.save(testEducation);

    // Assert: Verify education is saved with generated ID
    assertNotNull(savedEducation.getId());
    assertEquals("University", savedEducation.getInstitutionNameEn());
  }

  @Test
  @DisplayName("Should find education by ID successfully")
  void testFindById_WithExistingEducationId_ReturnsEducation() {
    // Arrange: Save education first
    Education savedEducation = educationRepository.save(testEducation);

    // Act: Find education by ID
    Optional<Education> foundEducation = educationRepository.findById(savedEducation.getId());

    // Assert: Verify education is found
    assertTrue(foundEducation.isPresent());
  }

  @Test
  @DisplayName("Should return empty Optional when education ID does not exist")
  void testFindById_WithNonExistentEducationId_ReturnsEmptyOptional() {
    // Arrange: Education not saved

    // Act: Find education by ID
    Optional<Education> foundEducation = educationRepository.findById(999L);

    // Assert: Verify Optional is empty
    assertFalse(foundEducation.isPresent());
  }

  @Test
  @DisplayName("Should find current education records")
  void testFindByIsCurrent_WithCurrentEducation_ReturnsCurrentRecords() {
    // Arrange: Save current education
    testEducation.setIsCurrent(true);
    educationRepository.save(testEducation);

    // Act: Find current education
    List<Education> currentEducation = educationRepository.findByIsCurrent(true);

    // Assert: Verify results
    assertNotNull(currentEducation);
    assertTrue(currentEducation.size() > 0);
    assertTrue(currentEducation.get(0).getIsCurrent());
  }

  @Test
  @DisplayName("Should return empty list when no current education records")
  void testFindByIsCurrent_WithNoCurrentEducation_ReturnsEmptyList() {
    // Arrange: Save non-current education
    educationRepository.save(testEducation);

    // Act: Find current education
    List<Education> currentEducation = educationRepository.findByIsCurrent(true);

    // Assert: Verify empty list
    assertNotNull(currentEducation);
    assertTrue(currentEducation.isEmpty());
  }

  @Test
  @DisplayName("Should find education ordered by start date descending")
  void testFindByOrderByStartDateDesc_ReturnsOrderedEducation() {
    // Arrange: Save two education records with different start dates
    Education edu1 = testEducation;
    Education edu2 =
        Education.builder()
            .institutionNameEn("College")
            .institutionNameFr("Collège")
            .institutionNameEs("Colegio")
            .degreeEn("Diploma")
            .degreeFr("Diplôme")
            .degreeEs("Diploma")
            .fieldOfStudyEn("IT")
            .fieldOfStudyFr("TI")
            .fieldOfStudyEs("TI")
            .startDate(LocalDate.of(2022, 9, 1))
            .isCurrent(true)
            .build();
    educationRepository.save(edu1);
    educationRepository.save(edu2);

    // Act: Retrieve ordered list
    List<Education> educationList = educationRepository.findByOrderByStartDateDesc();

    // Assert: Verify order
    assertEquals(2, educationList.size());
    assertEquals("College", educationList.get(0).getInstitutionNameEn());
  }

  @Test
  @DisplayName("Should update education successfully")
  void testUpdateEducation_WithValidChanges_ReturnUpdatedEducation() {
    // Arrange: Save education first
    Education savedEducation = educationRepository.save(testEducation);

    // Act: Update education details
    savedEducation.setInstitutionNameEn("Updated University");
    Education updatedEducation = educationRepository.save(savedEducation);

    // Assert: Verify update
    assertEquals(savedEducation.getId(), updatedEducation.getId());
    assertEquals("Updated University", updatedEducation.getInstitutionNameEn());
  }

  @Test
  @DisplayName("Should delete education successfully")
  void testDeleteEducation_WithExistingEducation_EducationIsRemoved() {
    // Arrange: Save education first
    Education savedEducation = educationRepository.save(testEducation);
    Long educationId = savedEducation.getId();

    // Act: Delete education
    educationRepository.deleteById(educationId);

    // Assert: Verify deletion
    Optional<Education> deletedEducation = educationRepository.findById(educationId);
    assertFalse(deletedEducation.isPresent());
  }
}
