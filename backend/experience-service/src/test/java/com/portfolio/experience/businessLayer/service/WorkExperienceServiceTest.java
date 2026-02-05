package com.portfolio.experience.businessLayer.service;

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

import com.portfolio.experience.dataAccessLayer.entity.WorkExperience;
import com.portfolio.experience.dataAccessLayer.repository.WorkExperienceRepository;
import com.portfolio.experience.mappingLayer.dto.WorkExperienceDTO;
import com.portfolio.experience.mappingLayer.mapper.WorkExperienceMapper;
import com.portfolio.experience.utils.exceptions.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorkExperienceService Unit Tests")
class WorkExperienceServiceTest {

  @Mock private WorkExperienceRepository workExperienceRepository;
  @Mock private WorkExperienceMapper workExperienceMapper;

  @InjectMocks private WorkExperienceService workExperienceService;

  private WorkExperience testExperience;
  private WorkExperienceDTO testExperienceDTO;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    testExperience =
        WorkExperience.builder()
            .id(1L)
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

    testExperienceDTO =
        WorkExperienceDTO.builder()
            .id(1L)
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
  @DisplayName("Should retrieve all experiences")
  void testGetAllExperiences_ReturnsAllExperiences() {
    // Arrange: Setup mocks
    when(workExperienceRepository.findAll()).thenReturn(Arrays.asList(testExperience));
    when(workExperienceMapper.toDTO(testExperience)).thenReturn(testExperienceDTO);

    // Act: Call service method
    List<WorkExperienceDTO> result = workExperienceService.getAllExperiences();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("Should retrieve experiences ordered by start date")
  void testGetAllExperiencesOrderedByDate_ReturnsOrderedList() {
    // Arrange: Setup mocks
    when(workExperienceRepository.findByOrderByStartDateDesc())
        .thenReturn(Arrays.asList(testExperience));
    when(workExperienceMapper.toDTO(testExperience)).thenReturn(testExperienceDTO);

    // Act: Call service method
    List<WorkExperienceDTO> result = workExperienceService.getAllExperiencesOrderedByDate();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("Should retrieve experience by ID")
  void testGetExperienceById_WithValidId_ReturnsExperience() {
    // Arrange: Setup mocks
    when(workExperienceRepository.findById(1L)).thenReturn(Optional.of(testExperience));
    when(workExperienceMapper.toDTO(testExperience)).thenReturn(testExperienceDTO);

    // Act: Call service method
    WorkExperienceDTO result = workExperienceService.getExperienceById(1L);

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(testExperienceDTO, result);
  }

  @Test
  @DisplayName("Should throw exception when experience ID not found")
  void testGetExperienceById_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    when(workExperienceRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception
    assertThrows(
        ResourceNotFoundException.class, () -> workExperienceService.getExperienceById(999L));
  }

  @Test
  @DisplayName("Should retrieve current experiences")
  void testGetCurrentExperiences_ReturnsCurrentExperiences() {
    // Arrange: Setup mocks
    testExperience.setIsCurrent(true);
    testExperienceDTO.setIsCurrent(true);
    when(workExperienceRepository.findByIsCurrent(true)).thenReturn(Arrays.asList(testExperience));
    when(workExperienceMapper.toDTO(testExperience)).thenReturn(testExperienceDTO);

    // Act: Call service method
    List<WorkExperienceDTO> result = workExperienceService.getCurrentExperiences();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.get(0).getIsCurrent());
  }

  @Test
  @DisplayName("Should create experience successfully")
  void testCreateExperience_WithValidDTO_CreatesExperience() {
    // Arrange: Setup mocks
    WorkExperienceDTO newExperienceDTO =
        WorkExperienceDTO.builder()
            .companyNameEn("New Company")
            .companyNameFr("Nouvelle entreprise")
            .companyNameEs("Nueva empresa")
            .positionEn("Engineer")
            .positionFr("Ingénieur")
            .positionEs("Ingeniero")
            .startDate(LocalDate.of(2021, 1, 1))
            .isCurrent(true)
            .build();

    WorkExperience newExperience =
        WorkExperience.builder()
            .companyNameEn("New Company")
            .companyNameFr("Nouvelle entreprise")
            .companyNameEs("Nueva empresa")
            .positionEn("Engineer")
            .positionFr("Ingénieur")
            .positionEs("Ingeniero")
            .startDate(LocalDate.of(2021, 1, 1))
            .isCurrent(true)
            .build();

    WorkExperience savedExperience =
        WorkExperience.builder()
            .id(2L)
            .companyNameEn("New Company")
            .companyNameFr("Nouvelle entreprise")
            .companyNameEs("Nueva empresa")
            .positionEn("Engineer")
            .positionFr("Ingénieur")
            .positionEs("Ingeniero")
            .startDate(LocalDate.of(2021, 1, 1))
            .isCurrent(true)
            .build();

    when(workExperienceMapper.toEntity(newExperienceDTO)).thenReturn(newExperience);
    when(workExperienceRepository.save(newExperience)).thenReturn(savedExperience);
    WorkExperienceDTO savedDTO =
        WorkExperienceDTO.builder()
            .id(2L)
            .companyNameEn("New Company")
            .companyNameFr("Nouvelle entreprise")
            .companyNameEs("Nueva empresa")
            .positionEn("Engineer")
            .positionFr("Ingénieur")
            .positionEs("Ingeniero")
            .startDate(LocalDate.of(2021, 1, 1))
            .isCurrent(true)
            .build();
    when(workExperienceMapper.toDTO(savedExperience)).thenReturn(savedDTO);

    // Act: Call service method
    WorkExperienceDTO result = workExperienceService.createExperience(newExperienceDTO);

    // Assert: Verify results
    assertNotNull(result);
    assertNotNull(result.getId());
  }

  @Test
  @DisplayName("Should update experience successfully")
  void testUpdateExperience_WithValidIdAndDTO_UpdatesExperience() {
    // Arrange: Setup mocks
    WorkExperienceDTO updateDTO =
        WorkExperienceDTO.builder()
            .companyNameEn("Updated Company")
            .companyNameFr("Entreprise mise à jour")
            .companyNameEs("Empresa actualizada")
            .positionEn("Senior Engineer")
            .positionFr("Ingénieur senior")
            .positionEs("Ingeniero senior")
            .startDate(LocalDate.of(2022, 1, 1))
            .isCurrent(false)
            .build();

    when(workExperienceRepository.findById(1L)).thenReturn(Optional.of(testExperience));
    when(workExperienceRepository.save(any(WorkExperience.class))).thenReturn(testExperience);
    when(workExperienceMapper.toDTO(testExperience)).thenReturn(testExperienceDTO);

    // Act: Call service method
    WorkExperienceDTO result = workExperienceService.updateExperience(1L, updateDTO);

    // Assert: Verify results
    assertNotNull(result);
    verify(workExperienceRepository, times(1)).findById(1L);
    verify(workExperienceRepository, times(1)).save(any(WorkExperience.class));
  }

  @Test
  @DisplayName("Should throw exception when updating non-existent experience")
  void testUpdateExperience_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    WorkExperienceDTO updateDTO = new WorkExperienceDTO();
    when(workExperienceRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception
    assertThrows(
        ResourceNotFoundException.class,
        () -> workExperienceService.updateExperience(999L, updateDTO));
  }

  @Test
  @DisplayName("Should delete experience successfully")
  void testDeleteExperience_WithValidId_DeletesExperience() {
    // Arrange: Setup mocks
    when(workExperienceRepository.existsById(1L)).thenReturn(true);
    doNothing().when(workExperienceRepository).deleteById(1L);

    // Act: Call service method
    workExperienceService.deleteExperience(1L);

    // Assert: Verify deletion
    verify(workExperienceRepository, times(1)).existsById(1L);
    verify(workExperienceRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Should throw exception when deleting non-existent experience")
  void testDeleteExperience_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    when(workExperienceRepository.existsById(999L)).thenReturn(false);

    // Act & Assert: Verify exception
    assertThrows(
        ResourceNotFoundException.class, () -> workExperienceService.deleteExperience(999L));
  }
}
