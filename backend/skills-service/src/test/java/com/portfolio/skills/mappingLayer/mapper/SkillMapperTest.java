package com.portfolio.skills.mappingLayer.mapper;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.portfolio.skills.dataAccessLayer.entity.Skill;
import com.portfolio.skills.mappingLayer.dto.SkillDTO;

import static org.junit.jupiter.api.Assertions.*;

class SkillMapperTest {

  private SkillMapper skillMapper;
  private ModelMapper modelMapper;

  @BeforeEach
  void setUp() {
    modelMapper = new ModelMapper();
    skillMapper = new SkillMapper(modelMapper);
  }

  @Test
  void testToDTO() {
    LocalDateTime now = LocalDateTime.now();
    Skill skill =
        Skill.builder()
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

    SkillDTO dto = skillMapper.toDTO(skill);

    assertNotNull(dto);
    assertEquals(1L, dto.getId());
    assertEquals("Java", dto.getNameEn());
    assertEquals("Java", dto.getNameFr());
    assertEquals("Java", dto.getNameEs());
    assertEquals("Java programming language", dto.getDescriptionEn());
    assertEquals("Langage de programmation Java", dto.getDescriptionFr());
    assertEquals("Lenguaje de programación Java", dto.getDescriptionEs());
    assertEquals("Expert", dto.getProficiencyLevel());
    assertEquals("Backend", dto.getCategory());
    assertEquals(10, dto.getYearsOfExperience());
    assertEquals(now, dto.getCreatedAt());
    assertEquals(now, dto.getUpdatedAt());
  }

  @Test
  void testToEntity() {
    LocalDateTime now = LocalDateTime.now();
    SkillDTO dto =
        SkillDTO.builder()
            .id(1L)
            .nameEn("Python")
            .nameFr("Python")
            .nameEs("Python")
            .descriptionEn("Python programming language")
            .descriptionFr("Langage de programmation Python")
            .descriptionEs("Lenguaje de programación Python")
            .proficiencyLevel("Advanced")
            .category("Backend")
            .yearsOfExperience(8)
            .createdAt(now)
            .updatedAt(now)
            .build();

    Skill skill = skillMapper.toEntity(dto);

    assertNotNull(skill);
    assertEquals(1L, skill.getId());
    assertEquals("Python", skill.getNameEn());
    assertEquals("Python", skill.getNameFr());
    assertEquals("Python", skill.getNameEs());
    assertEquals("Python programming language", skill.getDescriptionEn());
    assertEquals("Langage de programmation Python", skill.getDescriptionFr());
    assertEquals("Lenguaje de programación Python", skill.getDescriptionEs());
    assertEquals("Advanced", skill.getProficiencyLevel());
    assertEquals("Backend", skill.getCategory());
    assertEquals(8, skill.getYearsOfExperience());
  }

  @Test
  void testNullOptionalFieldsMapping() {
    Skill skill =
        Skill.builder()
            .id(2L)
            .nameEn("JavaScript")
            .nameFr("JavaScript")
            .nameEs("JavaScript")
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .proficiencyLevel("Intermediate")
            .category("Frontend")
            .yearsOfExperience(null)
            .build();

    SkillDTO dto = skillMapper.toDTO(skill);

    assertNotNull(dto);
    assertNull(dto.getDescriptionEn());
    assertNull(dto.getDescriptionFr());
    assertNull(dto.getDescriptionEs());
    assertNull(dto.getYearsOfExperience());
    assertEquals("Intermediate", dto.getProficiencyLevel());
  }

  @Test
  void testRoundTripMapping() {
    LocalDateTime now = LocalDateTime.now();
    Skill originalSkill =
        Skill.builder()
            .id(3L)
            .nameEn("React")
            .nameFr("React")
            .nameEs("React")
            .descriptionEn("React library for UI")
            .descriptionFr("Bibliothèque React pour l'interface utilisateur")
            .descriptionEs("Biblioteca React para la interfaz de usuario")
            .proficiencyLevel("Advanced")
            .category("Frontend")
            .yearsOfExperience(7)
            .createdAt(now)
            .updatedAt(now)
            .build();

    SkillDTO dto = skillMapper.toDTO(originalSkill);
    Skill mappedSkill = skillMapper.toEntity(dto);

    assertEquals(originalSkill.getId(), mappedSkill.getId());
    assertEquals(originalSkill.getNameEn(), mappedSkill.getNameEn());
    assertEquals(originalSkill.getNameFr(), mappedSkill.getNameFr());
    assertEquals(originalSkill.getNameEs(), mappedSkill.getNameEs());
    assertEquals(originalSkill.getDescriptionEn(), mappedSkill.getDescriptionEn());
    assertEquals(originalSkill.getProficiencyLevel(), mappedSkill.getProficiencyLevel());
    assertEquals(originalSkill.getYearsOfExperience(), mappedSkill.getYearsOfExperience());
  }
}
