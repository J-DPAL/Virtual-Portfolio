package com.portfolio.skills.dataAccessLayer.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.skills.dataAccessLayer.entity.Skill;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("SkillRepository Integration Tests")
class SkillRepositoryTest {

  @Autowired private SkillRepository skillRepository;

  private Skill testSkill;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data and clear repository
    skillRepository.deleteAll();
    testSkill =
        Skill.builder()
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Programming Language")
            .descriptionFr("Langage de programmation")
            .descriptionEs("Lenguaje de programación")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(5)
            .build();
  }

  @Test
  @DisplayName("Should save a new skill successfully")
  void testSaveSkill_WithValidSkill_ReturnsSavedSkill() {
    // Arrange: Skill entity is created in setUp

    // Act: Save the skill
    Skill savedSkill = skillRepository.save(testSkill);

    // Assert: Verify skill is saved with generated ID
    assertNotNull(savedSkill.getId());
    assertEquals("Java", savedSkill.getNameEn());
    assertEquals("Backend", savedSkill.getCategory());
    assertEquals("Expert", savedSkill.getProficiencyLevel());
  }

  @Test
  @DisplayName("Should find skill by ID successfully")
  void testFindById_WithExistingSkillId_ReturnsSkill() {
    // Arrange: Save skill first
    Skill savedSkill = skillRepository.save(testSkill);

    // Act: Find skill by ID
    Optional<Skill> foundSkill = skillRepository.findById(savedSkill.getId());

    // Assert: Verify skill is found
    assertTrue(foundSkill.isPresent());
    assertEquals("Java", foundSkill.get().getNameEn());
    assertEquals("Backend", foundSkill.get().getCategory());
  }

  @Test
  @DisplayName("Should return empty Optional when skill ID does not exist")
  void testFindById_WithNonExistentSkillId_ReturnsEmptyOptional() {
    // Arrange: Skill not saved

    // Act: Try to find skill by ID
    Optional<Skill> foundSkill = skillRepository.findById(999L);

    // Assert: Verify Optional is empty
    assertFalse(foundSkill.isPresent());
  }

  @Test
  @DisplayName("Should find skills by category successfully")
  void testFindByCategory_WithValidCategory_ReturnsSkillsByCategory() {
    // Arrange: Save skill with category
    skillRepository.save(testSkill);

    // Act: Find skills by category
    List<Skill> skillsByCategory = skillRepository.findByCategory("Backend");

    // Assert: Verify skills are found
    assertNotNull(skillsByCategory);
    assertTrue(skillsByCategory.size() > 0);
    assertEquals("Backend", skillsByCategory.get(0).getCategory());
  }

  @Test
  @DisplayName("Should return empty list when no skills in category")
  void testFindByCategory_WithNonExistentCategory_ReturnsEmptyList() {
    // Arrange: Save skill with different category
    skillRepository.save(testSkill);

    // Act: Try to find skills by non-existent category
    List<Skill> skillsByCategory = skillRepository.findByCategory("NonExistent");

    // Assert: Verify empty list
    assertNotNull(skillsByCategory);
    assertTrue(skillsByCategory.isEmpty());
  }

  @Test
  @DisplayName("Should update skill successfully")
  void testUpdateSkill_WithValidChanges_ReturnUpdatedSkill() {
    // Arrange: Save skill first
    Skill savedSkill = skillRepository.save(testSkill);

    // Act: Update skill details
    savedSkill.setNameEn("Python");
    savedSkill.setProficiencyLevel("Advanced");
    Skill updatedSkill = skillRepository.save(savedSkill);

    // Assert: Verify skill is updated
    assertEquals(savedSkill.getId(), updatedSkill.getId());
    assertEquals("Python", updatedSkill.getNameEn());
    assertEquals("Advanced", updatedSkill.getProficiencyLevel());
  }

  @Test
  @DisplayName("Should delete skill successfully")
  void testDeleteSkill_WithExistingSkill_SkillIsRemoved() {
    // Arrange: Save skill first
    Skill savedSkill = skillRepository.save(testSkill);
    Long skillId = savedSkill.getId();

    // Act: Delete the skill
    skillRepository.deleteById(skillId);

    // Assert: Verify skill is deleted
    Optional<Skill> deletedSkill = skillRepository.findById(skillId);
    assertFalse(deletedSkill.isPresent());
  }

  @Test
  @DisplayName("Should find all skills successfully")
  void testFindAll_WithMultipleSkills_ReturnsAllSkills() {
    // Arrange: Save multiple skills
    Skill skill1 = testSkill;
    Skill skill2 =
        Skill.builder()
            .nameEn("Python")
            .nameFr("Python")
            .nameEs("Python")
            .proficiencyLevel("Advanced")
            .category("Backend")
            .yearsOfExperience(3)
            .build();
    skillRepository.save(skill1);
    skillRepository.save(skill2);

    // Act: Retrieve all skills
    List<Skill> allSkills = skillRepository.findAll();

    // Assert: Verify all skills are returned
    assertEquals(2, allSkills.size());
  }

  @Test
  @DisplayName("Should verify skill exists by ID")
  void testExistsById_WithExistingSkillId_ReturnsTrue() {
    // Arrange: Save skill first
    Skill savedSkill = skillRepository.save(testSkill);

    // Act: Check if skill exists
    boolean exists = skillRepository.existsById(savedSkill.getId());

    // Assert: Verify skill exists
    assertTrue(exists);
  }

  @Test
  @DisplayName("Should return false when skill does not exist by ID")
  void testExistsById_WithNonExistentSkillId_ReturnsFalse() {
    // Arrange: Skill not saved

    // Act: Check if skill exists
    boolean exists = skillRepository.existsById(999L);

    // Assert: Verify skill does not exist
    assertFalse(exists);
  }

  @Test
  @DisplayName("Should handle multiple skills in same category")
  void testFindByCategory_WithMultipleSkillsInCategory_ReturnsAllSkillsInCategory() {
    // Arrange: Save multiple skills in same category
    Skill skill1 = testSkill;
    Skill skill2 =
        Skill.builder()
            .nameEn("Kotlin")
            .nameFr("Kotlin")
            .nameEs("Kotlin")
            .proficiencyLevel("Intermediate")
            .category("Backend")
            .yearsOfExperience(2)
            .build();
    skillRepository.save(skill1);
    skillRepository.save(skill2);

    // Act: Find all skills in Backend category
    List<Skill> backendSkills = skillRepository.findByCategory("Backend");

    // Assert: Verify all backend skills are returned
    assertEquals(2, backendSkills.size());
    backendSkills.forEach(skill -> assertEquals("Backend", skill.getCategory()));
  }
}
