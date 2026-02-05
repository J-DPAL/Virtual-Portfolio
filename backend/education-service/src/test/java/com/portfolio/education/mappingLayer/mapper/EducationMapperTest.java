package com.portfolio.education.mappingLayer.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.portfolio.education.dataAccessLayer.entity.Education;
import com.portfolio.education.mappingLayer.dto.EducationDTO;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EducationMapper Tests")
class EducationMapperTest {

  private EducationMapper educationMapper;
  private ModelMapper modelMapper;

  @BeforeEach
  void setUp() {
    modelMapper = new ModelMapper();
    educationMapper = new EducationMapper(modelMapper);
  }

  @Test
  @DisplayName("Should map Education entity to EducationDTO")
  void testToDTO() {
    // Arrange
    LocalDateTime now = LocalDateTime.now();
    Education education =
        Education.builder()
            .id(1L)
            .institutionNameEn("Harvard University")
            .institutionNameFr("Université Harvard")
            .institutionNameEs("Universidad de Harvard")
            .degreeEn("Bachelor of Science")
            .degreeFr("Licence ès sciences")
            .degreeEs("Licenciatura en Ciencias")
            .fieldOfStudyEn("Computer Science")
            .fieldOfStudyFr("Informatique")
            .fieldOfStudyEs("Informática")
            .descriptionEn("Description in English")
            .descriptionFr("Description en français")
            .descriptionEs("Descripción en español")
            .startDate(LocalDate.of(2020, 9, 1))
            .endDate(LocalDate.of(2024, 6, 1))
            .isCurrent(false)
            .gpa(new BigDecimal("3.75"))
            .createdAt(now)
            .updatedAt(now)
            .build();

    // Act
    EducationDTO dto = educationMapper.toDTO(education);

    // Assert
    assertNotNull(dto);
    assertEquals(education.getId(), dto.getId());
    assertEquals(education.getInstitutionNameEn(), dto.getInstitutionNameEn());
    assertEquals(education.getInstitutionNameFr(), dto.getInstitutionNameFr());
    assertEquals(education.getInstitutionNameEs(), dto.getInstitutionNameEs());
    assertEquals(education.getDegreeEn(), dto.getDegreeEn());
    assertEquals(education.getDegreeFr(), dto.getDegreeFr());
    assertEquals(education.getDegreeEs(), dto.getDegreeEs());
    assertEquals(education.getFieldOfStudyEn(), dto.getFieldOfStudyEn());
    assertEquals(education.getFieldOfStudyFr(), dto.getFieldOfStudyFr());
    assertEquals(education.getFieldOfStudyEs(), dto.getFieldOfStudyEs());
    assertEquals(education.getDescriptionEn(), dto.getDescriptionEn());
    assertEquals(education.getDescriptionFr(), dto.getDescriptionFr());
    assertEquals(education.getDescriptionEs(), dto.getDescriptionEs());
    assertEquals(education.getStartDate(), dto.getStartDate());
    assertEquals(education.getEndDate(), dto.getEndDate());
    assertEquals(education.getIsCurrent(), dto.getIsCurrent());
    assertEquals(education.getGpa(), dto.getGpa());
    assertEquals(education.getCreatedAt(), dto.getCreatedAt());
    assertEquals(education.getUpdatedAt(), dto.getUpdatedAt());
  }

  @Test
  @DisplayName("Should map EducationDTO to Education entity")
  void testToEntity() {
    // Arrange
    LocalDateTime now = LocalDateTime.now();
    EducationDTO dto =
        EducationDTO.builder()
            .id(1L)
            .institutionNameEn("MIT")
            .institutionNameFr("MIT")
            .institutionNameEs("MIT")
            .degreeEn("Master of Science")
            .degreeFr("Master ès sciences")
            .degreeEs("Maestría en Ciencias")
            .fieldOfStudyEn("Artificial Intelligence")
            .fieldOfStudyFr("Intelligence artificielle")
            .fieldOfStudyEs("Inteligencia Artificial")
            .descriptionEn("AI program")
            .descriptionFr("Programme IA")
            .descriptionEs("Programa IA")
            .startDate(LocalDate.of(2024, 9, 1))
            .endDate(LocalDate.of(2026, 6, 1))
            .isCurrent(true)
            .gpa(new BigDecimal("4.00"))
            .createdAt(now)
            .updatedAt(now)
            .build();

    // Act
    Education education = educationMapper.toEntity(dto);

    // Assert
    assertNotNull(education);
    assertEquals(dto.getId(), education.getId());
    assertEquals(dto.getInstitutionNameEn(), education.getInstitutionNameEn());
    assertEquals(dto.getInstitutionNameFr(), education.getInstitutionNameFr());
    assertEquals(dto.getInstitutionNameEs(), education.getInstitutionNameEs());
    assertEquals(dto.getDegreeEn(), education.getDegreeEn());
    assertEquals(dto.getDegreeFr(), education.getDegreeFr());
    assertEquals(dto.getDegreeEs(), education.getDegreeEs());
    assertEquals(dto.getFieldOfStudyEn(), education.getFieldOfStudyEn());
    assertEquals(dto.getFieldOfStudyFr(), education.getFieldOfStudyFr());
    assertEquals(dto.getFieldOfStudyEs(), education.getFieldOfStudyEs());
    assertEquals(dto.getDescriptionEn(), education.getDescriptionEn());
    assertEquals(dto.getDescriptionFr(), education.getDescriptionFr());
    assertEquals(dto.getDescriptionEs(), education.getDescriptionEs());
    assertEquals(dto.getStartDate(), education.getStartDate());
    assertEquals(dto.getEndDate(), education.getEndDate());
    assertEquals(dto.getIsCurrent(), education.getIsCurrent());
    assertEquals(dto.getGpa(), education.getGpa());
    assertEquals(dto.getCreatedAt(), education.getCreatedAt());
    assertEquals(dto.getUpdatedAt(), education.getUpdatedAt());
  }

  @Test
  @DisplayName("Should map entity with null optional fields to DTO")
  void testToDTO_WithNullOptionalFields() {
    // Arrange
    Education education =
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
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .startDate(LocalDate.now())
            .endDate(null)
            .isCurrent(true)
            .gpa(null)
            .build();

    // Act
    EducationDTO dto = educationMapper.toDTO(education);

    // Assert
    assertNotNull(dto);
    assertNull(dto.getDescriptionEn());
    assertNull(dto.getDescriptionFr());
    assertNull(dto.getDescriptionEs());
    assertNull(dto.getEndDate());
    assertNull(dto.getGpa());
  }

  @Test
  @DisplayName("Should map DTO with null optional fields to entity")
  void testToEntity_WithNullOptionalFields() {
    // Arrange
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
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .startDate(LocalDate.now())
            .endDate(null)
            .isCurrent(true)
            .gpa(null)
            .build();

    // Act
    Education education = educationMapper.toEntity(dto);

    // Assert
    assertNotNull(education);
    assertNull(education.getDescriptionEn());
    assertNull(education.getDescriptionFr());
    assertNull(education.getDescriptionEs());
    assertNull(education.getEndDate());
    assertNull(education.getGpa());
  }

  @Test
  @DisplayName("Should handle round-trip mapping from entity to DTO and back")
  void testRoundTripMapping() {
    // Arrange
    LocalDateTime now = LocalDateTime.now();
    Education originalEducation =
        Education.builder()
            .id(1L)
            .institutionNameEn("Stanford")
            .institutionNameFr("Stanford")
            .institutionNameEs("Stanford")
            .degreeEn("PhD")
            .degreeFr("Doctorat")
            .degreeEs("Doctorado")
            .fieldOfStudyEn("ML")
            .fieldOfStudyFr("AA")
            .fieldOfStudyEs("AA")
            .startDate(LocalDate.of(2025, 9, 1))
            .isCurrent(true)
            .gpa(new BigDecimal("3.95"))
            .createdAt(now)
            .updatedAt(now)
            .build();

    // Act
    EducationDTO dto = educationMapper.toDTO(originalEducation);
    Education mappedEducation = educationMapper.toEntity(dto);

    // Assert
    assertEquals(originalEducation.getId(), mappedEducation.getId());
    assertEquals(originalEducation.getInstitutionNameEn(), mappedEducation.getInstitutionNameEn());
    assertEquals(originalEducation.getDegreeEn(), mappedEducation.getDegreeEn());
    assertEquals(originalEducation.getFieldOfStudyEn(), mappedEducation.getFieldOfStudyEn());
    assertEquals(originalEducation.getStartDate(), mappedEducation.getStartDate());
    assertEquals(originalEducation.getIsCurrent(), mappedEducation.getIsCurrent());
    assertEquals(originalEducation.getGpa(), mappedEducation.getGpa());
  }
}
