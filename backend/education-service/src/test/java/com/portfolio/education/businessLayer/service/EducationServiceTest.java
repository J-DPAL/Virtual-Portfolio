package com.portfolio.education.businessLayer.service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import com.portfolio.education.dataAccessLayer.entity.Education;
import com.portfolio.education.dataAccessLayer.repository.EducationRepository;
import com.portfolio.education.mappingLayer.dto.EducationDTO;
import com.portfolio.education.mappingLayer.mapper.EducationMapper;
import com.portfolio.education.utils.exceptions.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EducationService Unit Tests")
class EducationServiceTest {

  @Mock private EducationRepository educationRepository;
  @Mock private EducationMapper educationMapper;

  @InjectMocks private EducationService educationService;

  private Education testEducation;
  private EducationDTO testEducationDTO;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    testEducation =
        Education.builder()
            .id(1L)
            .institutionNameEn("University")
            .institutionNameFr("Université")
            .institutionNameEs("Universidad")
            .degreeEn("BSc")
            .degreeFr("Licence")
            .degreeEs("Licenciatura")
            .fieldOfStudyEn("Computer Science")
            .fieldOfStudyFr("Informatique")
            .fieldOfStudyEs("Informática")
            .descriptionEn("CS program")
            .descriptionFr("Programme info")
            .descriptionEs("Programa de informática")
            .startDate(LocalDate.of(2020, 9, 1))
            .endDate(LocalDate.of(2024, 6, 1))
            .isCurrent(false)
            .gpa(new BigDecimal("3.75"))
            .build();

    testEducationDTO =
        EducationDTO.builder()
            .id(1L)
            .institutionNameEn("University")
            .institutionNameFr("Université")
            .institutionNameEs("Universidad")
            .degreeEn("BSc")
            .degreeFr("Licence")
            .degreeEs("Licenciatura")
            .fieldOfStudyEn("Computer Science")
            .fieldOfStudyFr("Informatique")
            .fieldOfStudyEs("Informática")
            .descriptionEn("CS program")
            .descriptionFr("Programme info")
            .descriptionEs("Programa de informática")
            .startDate(LocalDate.of(2020, 9, 1))
            .endDate(LocalDate.of(2024, 6, 1))
            .isCurrent(false)
            .gpa(new BigDecimal("3.75"))
            .build();
  }

  @Test
  @DisplayName("Should retrieve all education records")
  void testGetAllEducation_ReturnsAllEducation() {
    // Arrange: Setup mocks
    when(educationRepository.findAll()).thenReturn(Arrays.asList(testEducation));
    when(educationMapper.toDTO(testEducation)).thenReturn(testEducationDTO);

    // Act: Call service method
    List<EducationDTO> result = educationService.getAllEducation();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
    verify(educationRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("Should retrieve education ordered by start date")
  void testGetAllEducationOrderedByDate_ReturnsOrderedList() {
    // Arrange: Setup mocks
    when(educationRepository.findByOrderByStartDateDesc()).thenReturn(Arrays.asList(testEducation));
    when(educationMapper.toDTO(testEducation)).thenReturn(testEducationDTO);

    // Act: Call service method
    List<EducationDTO> result = educationService.getAllEducationOrderedByDate();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
    verify(educationRepository, times(1)).findByOrderByStartDateDesc();
  }

  @Test
  @DisplayName("Should retrieve education by ID")
  void testGetEducationById_WithValidId_ReturnsEducation() {
    // Arrange: Setup mocks
    when(educationRepository.findById(1L)).thenReturn(Optional.of(testEducation));
    when(educationMapper.toDTO(testEducation)).thenReturn(testEducationDTO);

    // Act: Call service method
    EducationDTO result = educationService.getEducationById(1L);

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(testEducationDTO, result);
  }

  @Test
  @DisplayName("Should throw exception when education ID not found")
  void testGetEducationById_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    when(educationRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception
    assertThrows(ResourceNotFoundException.class, () -> educationService.getEducationById(999L));
  }

  @Test
  @DisplayName("Should retrieve current education records")
  void testGetCurrentEducation_ReturnsCurrentEducation() {
    // Arrange: Setup mocks
    testEducation.setIsCurrent(true);
    testEducationDTO.setIsCurrent(true);
    when(educationRepository.findByIsCurrent(true)).thenReturn(Arrays.asList(testEducation));
    when(educationMapper.toDTO(testEducation)).thenReturn(testEducationDTO);

    // Act: Call service method
    List<EducationDTO> result = educationService.getCurrentEducation();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.get(0).getIsCurrent());
  }

  @Test
  @DisplayName("Should create education successfully")
  void testCreateEducation_WithValidDTO_CreatesEducation() {
    // Arrange: Setup mocks
    EducationDTO newEducationDTO =
        EducationDTO.builder()
            .institutionNameEn("College")
            .institutionNameFr("Collège")
            .institutionNameEs("Colegio")
            .degreeEn("Diploma")
            .degreeFr("Diplôme")
            .degreeEs("Diploma")
            .fieldOfStudyEn("IT")
            .fieldOfStudyFr("TI")
            .fieldOfStudyEs("TI")
            .startDate(LocalDate.of(2018, 9, 1))
            .isCurrent(false)
            .build();

    Education newEducation =
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
            .startDate(LocalDate.of(2018, 9, 1))
            .isCurrent(false)
            .build();

    Education savedEducation =
        Education.builder()
            .id(2L)
            .institutionNameEn("College")
            .institutionNameFr("Collège")
            .institutionNameEs("Colegio")
            .degreeEn("Diploma")
            .degreeFr("Diplôme")
            .degreeEs("Diploma")
            .fieldOfStudyEn("IT")
            .fieldOfStudyFr("TI")
            .fieldOfStudyEs("TI")
            .startDate(LocalDate.of(2018, 9, 1))
            .isCurrent(false)
            .build();

    when(educationMapper.toEntity(newEducationDTO)).thenReturn(newEducation);
    when(educationRepository.save(newEducation)).thenReturn(savedEducation);
    EducationDTO savedDTO =
        EducationDTO.builder()
            .id(2L)
            .institutionNameEn("College")
            .institutionNameFr("Collège")
            .institutionNameEs("Colegio")
            .degreeEn("Diploma")
            .degreeFr("Diplôme")
            .degreeEs("Diploma")
            .fieldOfStudyEn("IT")
            .fieldOfStudyFr("TI")
            .fieldOfStudyEs("TI")
            .startDate(LocalDate.of(2018, 9, 1))
            .isCurrent(false)
            .build();
    when(educationMapper.toDTO(savedEducation)).thenReturn(savedDTO);

    // Act: Call service method
    EducationDTO result = educationService.createEducation(newEducationDTO);

    // Assert: Verify results
    assertNotNull(result);
    assertNotNull(result.getId());
    verify(educationRepository, times(1)).save(any(Education.class));
  }

  @Test
  @DisplayName("Should update education successfully")
  void testUpdateEducation_WithValidIdAndDTO_UpdatesEducation() {
    // Arrange: Setup mocks
    EducationDTO updateDTO =
        EducationDTO.builder()
            .institutionNameEn("Updated University")
            .institutionNameFr("Université Mise à jour")
            .institutionNameEs("Universidad Actualizada")
            .degreeEn("MSc")
            .degreeFr("Master")
            .degreeEs("Maestría")
            .fieldOfStudyEn("Computer Science")
            .fieldOfStudyFr("Informatique")
            .fieldOfStudyEs("Informática")
            .startDate(LocalDate.of(2021, 1, 1))
            .isCurrent(true)
            .build();

    when(educationRepository.findById(1L)).thenReturn(Optional.of(testEducation));
    when(educationRepository.save(any(Education.class))).thenReturn(testEducation);
    when(educationMapper.toDTO(testEducation)).thenReturn(testEducationDTO);

    // Act: Call service method
    EducationDTO result = educationService.updateEducation(1L, updateDTO);

    // Assert: Verify results
    assertNotNull(result);
    verify(educationRepository, times(1)).findById(1L);
    verify(educationRepository, times(1)).save(any(Education.class));
  }

  @Test
  @DisplayName("Should throw exception when updating non-existent education")
  void testUpdateEducation_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    EducationDTO updateDTO = new EducationDTO();
    when(educationRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception
    assertThrows(
        ResourceNotFoundException.class, () -> educationService.updateEducation(999L, updateDTO));
  }

  @Test
  @DisplayName("Should delete education successfully")
  void testDeleteEducation_WithValidId_DeletesEducation() {
    // Arrange: Setup mocks
    when(educationRepository.existsById(1L)).thenReturn(true);
    doNothing().when(educationRepository).deleteById(1L);

    // Act: Call service method
    educationService.deleteEducation(1L);

    // Assert: Verify deletion
    verify(educationRepository, times(1)).existsById(1L);
    verify(educationRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Should throw exception when deleting non-existent education")
  void testDeleteEducation_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    when(educationRepository.existsById(999L)).thenReturn(false);

    // Act & Assert: Verify exception
    assertThrows(ResourceNotFoundException.class, () -> educationService.deleteEducation(999L));
  }
}
