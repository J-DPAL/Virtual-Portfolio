package com.portfolio.skills.businessLayer.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.portfolio.skills.dataAccessLayer.entity.Skill;
import com.portfolio.skills.dataAccessLayer.repository.SkillRepository;
import com.portfolio.skills.mappingLayer.dto.SkillDTO;
import com.portfolio.skills.mappingLayer.mapper.SkillMapper;
import com.portfolio.skills.utils.exceptions.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SkillService Unit Tests")
class SkillServiceTest {

  @Mock private SkillRepository skillRepository;
  @Mock private SkillMapper skillMapper;

  @InjectMocks private SkillService skillService;

  private Skill testSkill;
  private SkillDTO testSkillDTO;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    testSkill =
        Skill.builder()
            .id(1L)
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

    testSkillDTO =
        SkillDTO.builder()
            .id(1L)
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
  @DisplayName("Should retrieve all skills successfully")
  void testGetAllSkills_WithMultipleSkills_ReturnsAllSkills() {
    // Arrange: Setup mocks
    List<Skill> skills = Arrays.asList(testSkill);
    when(skillRepository.findAll()).thenReturn(skills);
    when(skillMapper.toDTO(testSkill)).thenReturn(testSkillDTO);

    // Act: Call the service method
    List<SkillDTO> result = skillService.getAllSkills();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(testSkillDTO, result.get(0));
    verify(skillRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("Should retrieve skill by ID successfully")
  void testGetSkillById_WithValidId_ReturnsSkill() {
    // Arrange: Setup mocks
    when(skillRepository.findById(1L)).thenReturn(Optional.of(testSkill));
    when(skillMapper.toDTO(testSkill)).thenReturn(testSkillDTO);

    // Act: Call the service method
    SkillDTO result = skillService.getSkillById(1L);

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(testSkillDTO, result);
    verify(skillRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Should throw exception when skill ID does not exist")
  void testGetSkillById_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock to return empty Optional
    when(skillRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception is thrown
    assertThrows(
        ResourceNotFoundException.class, () -> skillService.getSkillById(999L), "Skill not found");
    verify(skillRepository, times(1)).findById(999L);
  }

  @Test
  @DisplayName("Should retrieve skills by category successfully")
  void testGetSkillsByCategory_WithValidCategory_ReturnsSkillsByCategory() {
    // Arrange: Setup mocks
    List<Skill> backendSkills = Arrays.asList(testSkill);
    when(skillRepository.findByCategory("Backend")).thenReturn(backendSkills);
    when(skillMapper.toDTO(testSkill)).thenReturn(testSkillDTO);

    // Act: Call the service method
    List<SkillDTO> result = skillService.getSkillsByCategory("Backend");

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Backend", result.get(0).getCategory());
    verify(skillRepository, times(1)).findByCategory("Backend");
  }

  @Test
  @DisplayName("Should return empty list when no skills in category")
  void testGetSkillsByCategory_WithNoSkillsInCategory_ReturnsEmptyList() {
    // Arrange: Setup mock
    when(skillRepository.findByCategory("NonExistent")).thenReturn(Arrays.asList());

    // Act: Call the service method
    List<SkillDTO> result = skillService.getSkillsByCategory("NonExistent");

    // Assert: Verify empty list
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(skillRepository, times(1)).findByCategory("NonExistent");
  }

  @Test
  @DisplayName("Should create skill successfully")
  void testCreateSkill_WithValidDTO_CreatesSkill() {
    // Arrange: Setup mocks
    SkillDTO newSkillDTO =
        SkillDTO.builder()
            .nameEn("Python")
            .nameFr("Python")
            .nameEs("Python")
            .proficiencyLevel("Advanced")
            .category("Backend")
            .yearsOfExperience(3)
            .build();

    Skill newSkill =
        Skill.builder()
            .nameEn("Python")
            .nameFr("Python")
            .nameEs("Python")
            .proficiencyLevel("Advanced")
            .category("Backend")
            .yearsOfExperience(3)
            .build();

    Skill savedSkill =
        Skill.builder()
            .id(2L)
            .nameEn("Python")
            .nameFr("Python")
            .nameEs("Python")
            .proficiencyLevel("Advanced")
            .category("Backend")
            .yearsOfExperience(3)
            .build();

    when(skillMapper.toEntity(newSkillDTO)).thenReturn(newSkill);
    when(skillRepository.save(newSkill)).thenReturn(savedSkill);
    SkillDTO savedDTO =
        SkillDTO.builder()
            .id(2L)
            .nameEn("Python")
            .nameFr("Python")
            .nameEs("Python")
            .proficiencyLevel("Advanced")
            .category("Backend")
            .yearsOfExperience(3)
            .build();
    when(skillMapper.toDTO(savedSkill)).thenReturn(savedDTO);

    // Act: Call the service method
    SkillDTO result = skillService.createSkill(newSkillDTO);

    // Assert: Verify results
    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals("Python", result.getNameEn());
    verify(skillRepository, times(1)).save(any(Skill.class));
  }

  @Test
  @DisplayName("Should update skill successfully")
  void testUpdateSkill_WithValidIdAndDTO_UpdatesSkill() {
    // Arrange: Setup mocks
    SkillDTO updateDTO =
        SkillDTO.builder()
            .nameEn("Updated Java")
            .nameFr("Java Mis à Jour")
            .nameEs("Java Actualizado")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .build();

    when(skillRepository.findById(1L)).thenReturn(Optional.of(testSkill));
    when(skillRepository.save(any(Skill.class))).thenReturn(testSkill);
    when(skillMapper.toDTO(testSkill)).thenReturn(testSkillDTO);

    // Act: Call the service method
    SkillDTO result = skillService.updateSkill(1L, updateDTO);

    // Assert: Verify results
    assertNotNull(result);
    verify(skillRepository, times(1)).findById(1L);
    verify(skillRepository, times(1)).save(any(Skill.class));
  }

  @Test
  @DisplayName("Should throw exception when updating non-existent skill")
  void testUpdateSkill_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    SkillDTO updateDTO = new SkillDTO();
    when(skillRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception is thrown
    assertThrows(ResourceNotFoundException.class, () -> skillService.updateSkill(999L, updateDTO));
    verify(skillRepository, times(1)).findById(999L);
    verify(skillRepository, never()).save(any());
  }

  @Test
  @DisplayName("Should delete skill successfully")
  void testDeleteSkill_WithValidId_DeletesSkill() {
    // Arrange: Setup mocks
    when(skillRepository.existsById(1L)).thenReturn(true);
    doNothing().when(skillRepository).deleteById(1L);

    // Act: Call the service method
    skillService.deleteSkill(1L);

    // Assert: Verify deletion
    verify(skillRepository, times(1)).existsById(1L);
    verify(skillRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Should throw exception when deleting non-existent skill")
  void testDeleteSkill_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    when(skillRepository.existsById(999L)).thenReturn(false);

    // Act & Assert: Verify exception is thrown
    assertThrows(ResourceNotFoundException.class, () -> skillService.deleteSkill(999L));
    verify(skillRepository, times(1)).existsById(999L);
    verify(skillRepository, never()).deleteById(anyLong());
  }

  @Test
  @DisplayName("Should return empty list when no skills exist")
  void testGetAllSkills_WithNoSkills_ReturnsEmptyList() {
    // Arrange: Setup mock
    when(skillRepository.findAll()).thenReturn(Arrays.asList());

    // Act: Call the service method
    List<SkillDTO> result = skillService.getAllSkills();

    // Assert: Verify empty list
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(skillRepository, times(1)).findAll();
  }
}
