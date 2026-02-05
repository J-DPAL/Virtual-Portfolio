package com.portfolio.experience.presentationLayer.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.experience.businessLayer.service.WorkExperienceService;
import com.portfolio.experience.mappingLayer.dto.WorkExperienceDTO;
import com.portfolio.experience.utils.exceptions.ResourceNotFoundException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("WorkExperienceController Integration Tests")
class WorkExperienceControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private WorkExperienceService workExperienceService;

  private WorkExperienceDTO experienceDTO;
  private List<WorkExperienceDTO> experienceList;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    experienceDTO =
        WorkExperienceDTO.builder()
            .id(1L)
            .companyNameEn("Tech Corp")
            .companyNameFr("Tech Corp")
            .companyNameEs("Tech Corp")
            .positionEn("Developer")
            .positionFr("Développeur")
            .positionEs("Desarrollador")
            .startDate(LocalDate.of(2022, 1, 1))
            .isCurrent(false)
            .build();

    experienceList = Arrays.asList(experienceDTO);
  }

  @Test
  @DisplayName("Should retrieve all experiences via GET /experience")
  void testGetAllExperiences_ReturnsAllExperiences() throws Exception {
    // Arrange: Setup mock
    when(workExperienceService.getAllExperiencesOrderedByDate()).thenReturn(experienceList);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/experience").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L));

    verify(workExperienceService, times(1)).getAllExperiencesOrderedByDate();
  }

  @Test
  @DisplayName("Should retrieve experience by ID via GET /experience/{id}")
  void testGetExperienceById_WithValidId_ReturnsExperience() throws Exception {
    // Arrange: Setup mock
    when(workExperienceService.getExperienceById(1L)).thenReturn(experienceDTO);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/experience/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L));

    verify(workExperienceService, times(1)).getExperienceById(1L);
  }

  @Test
  @DisplayName("Should return 404 when experience ID does not exist")
  void testGetExperienceById_WithInvalidId_ReturnsNotFound() throws Exception {
    // Arrange: Setup mock
    when(workExperienceService.getExperienceById(999L))
        .thenThrow(new ResourceNotFoundException("Experience not found"));

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/experience/999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("Should retrieve current experiences via GET /experience/current")
  void testGetCurrentExperiences_ReturnsCurrentExperiences() throws Exception {
    // Arrange: Setup mock
    when(workExperienceService.getCurrentExperiences()).thenReturn(experienceList);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/experience/current").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L));

    verify(workExperienceService, times(1)).getCurrentExperiences();
  }

  @Test
  @DisplayName("Should create experience via POST /experience")
  void testCreateExperience_WithValidDTO_ReturnsCreatedExperience() throws Exception {
    // Arrange: Setup mock
    WorkExperienceDTO createDTO =
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

    WorkExperienceDTO createdDTO =
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

    when(workExperienceService.createExperience(any(WorkExperienceDTO.class)))
        .thenReturn(createdDTO);

    // Act & Assert: Perform POST request and verify response
    mockMvc
        .perform(
            post("/experience")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.companyNameEn").value("New Company"));

    verify(workExperienceService, times(1)).createExperience(any(WorkExperienceDTO.class));
  }

  @Test
  @DisplayName("Should update experience via PUT /experience/{id}")
  void testUpdateExperience_WithValidIdAndDTO_ReturnsUpdatedExperience() throws Exception {
    // Arrange: Setup mock
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

    WorkExperienceDTO updatedDTO =
        WorkExperienceDTO.builder()
            .id(1L)
            .companyNameEn("Updated Company")
            .companyNameFr("Tech Corp")
            .companyNameEs("Tech Corp")
            .positionEn("Developer")
            .positionFr("Développeur")
            .positionEs("Desarrollador")
            .startDate(LocalDate.of(2022, 1, 1))
            .isCurrent(false)
            .build();

    when(workExperienceService.updateExperience(eq(1L), any(WorkExperienceDTO.class)))
        .thenReturn(updatedDTO);

    // Act & Assert: Perform PUT request and verify response
    mockMvc
        .perform(
            put("/experience/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.companyNameEn").value("Updated Company"));

    verify(workExperienceService, times(1)).updateExperience(eq(1L), any(WorkExperienceDTO.class));
  }

  @Test
  @DisplayName("Should delete experience via DELETE /experience/{id}")
  void testDeleteExperience_WithValidId_ReturnsNoContent() throws Exception {
    // Arrange: Setup mock
    doNothing().when(workExperienceService).deleteExperience(1L);

    // Act & Assert: Perform DELETE request and verify response
    mockMvc
        .perform(delete("/experience/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(workExperienceService, times(1)).deleteExperience(1L);
  }

  @Test
  @DisplayName("Should return health status via GET /experience/health")
  void testHealthEndpoint_ReturnsOk() throws Exception {
    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/experience/health").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Experience service is running"));
  }

  @Test
  @DisplayName("Should return 400 when creating experience without required fields")
  void testCreateExperience_WithMissingRequiredFields_ReturnsBadRequest() throws Exception {
    // Arrange: Create invalid DTO
    WorkExperienceDTO invalidDTO =
        WorkExperienceDTO.builder().companyNameEn("Only English").build();

    // Act & Assert: Perform POST request and verify bad request response
    mockMvc
        .perform(
            post("/experience")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
        .andExpect(status().isBadRequest());
  }
}
