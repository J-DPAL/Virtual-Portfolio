package com.portfolio.education.presentationLayer.controller;

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
import com.portfolio.education.businessLayer.service.EducationService;
import com.portfolio.education.mappingLayer.dto.EducationDTO;
import com.portfolio.education.utils.exceptions.ResourceNotFoundException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("EducationController Integration Tests")
class EducationControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private EducationService educationService;

  private EducationDTO educationDTO;
  private List<EducationDTO> educationList;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    educationDTO =
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
            .startDate(LocalDate.of(2020, 9, 1))
            .isCurrent(false)
            .build();

    educationList = Arrays.asList(educationDTO);
  }

  @Test
  @DisplayName("Should retrieve all education via GET /education")
  void testGetAllEducation_ReturnsAllEducation() throws Exception {
    // Arrange: Setup mock
    when(educationService.getAllEducationOrderedByDate()).thenReturn(educationList);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/education").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].institutionNameEn").value("University"));

    verify(educationService, times(1)).getAllEducationOrderedByDate();
  }

  @Test
  @DisplayName("Should retrieve education by ID via GET /education/{id}")
  void testGetEducationById_WithValidId_ReturnsEducation() throws Exception {
    // Arrange: Setup mock
    when(educationService.getEducationById(1L)).thenReturn(educationDTO);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/education/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L));

    verify(educationService, times(1)).getEducationById(1L);
  }

  @Test
  @DisplayName("Should return 404 when education ID does not exist")
  void testGetEducationById_WithInvalidId_ReturnsNotFound() throws Exception {
    // Arrange: Setup mock
    when(educationService.getEducationById(999L))
        .thenThrow(new ResourceNotFoundException("Education not found"));

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/education/999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("Should retrieve current education via GET /education/current")
  void testGetCurrentEducation_ReturnsCurrentEducation() throws Exception {
    // Arrange: Setup mock
    when(educationService.getCurrentEducation()).thenReturn(educationList);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/education/current").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L));

    verify(educationService, times(1)).getCurrentEducation();
  }

  @Test
  @DisplayName("Should create education via POST /education")
  void testCreateEducation_WithValidDTO_ReturnsCreatedEducation() throws Exception {
    // Arrange: Setup mock
    EducationDTO createDTO =
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

    EducationDTO createdDTO =
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

    when(educationService.createEducation(any(EducationDTO.class))).thenReturn(createdDTO);

    // Act & Assert: Perform POST request and verify response
    mockMvc
        .perform(
            post("/education")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.institutionNameEn").value("College"));

    verify(educationService, times(1)).createEducation(any(EducationDTO.class));
  }

  @Test
  @DisplayName("Should update education via PUT /education/{id}")
  void testUpdateEducation_WithValidIdAndDTO_ReturnsUpdatedEducation() throws Exception {
    // Arrange: Setup mock
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

    EducationDTO updatedDTO =
        EducationDTO.builder()
            .id(1L)
            .institutionNameEn("Updated University")
            .institutionNameFr("Université")
            .institutionNameEs("Universidad")
            .degreeEn("BSc")
            .degreeFr("Licence")
            .degreeEs("Licenciatura")
            .fieldOfStudyEn("Computer Science")
            .fieldOfStudyFr("Informatique")
            .fieldOfStudyEs("Informática")
            .startDate(LocalDate.of(2021, 1, 1))
            .isCurrent(true)
            .build();

    when(educationService.updateEducation(eq(1L), any(EducationDTO.class))).thenReturn(updatedDTO);

    // Act & Assert: Perform PUT request and verify response
    mockMvc
        .perform(
            put("/education/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.institutionNameEn").value("Updated University"));

    verify(educationService, times(1)).updateEducation(eq(1L), any(EducationDTO.class));
  }

  @Test
  @DisplayName("Should delete education via DELETE /education/{id}")
  void testDeleteEducation_WithValidId_ReturnsNoContent() throws Exception {
    // Arrange: Setup mock
    doNothing().when(educationService).deleteEducation(1L);

    // Act & Assert: Perform DELETE request and verify response
    mockMvc
        .perform(delete("/education/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(educationService, times(1)).deleteEducation(1L);
  }

  @Test
  @DisplayName("Should return health status via GET /education/health")
  void testHealthEndpoint_ReturnsOk() throws Exception {
    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/education/health").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Education service is running"));
  }

  @Test
  @DisplayName("Should return 400 when creating education without required fields")
  void testCreateEducation_WithMissingRequiredFields_ReturnsBadRequest() throws Exception {
    // Arrange: Create invalid DTO
    EducationDTO invalidDTO = EducationDTO.builder().institutionNameEn("Only English").build();

    // Act & Assert: Perform POST request and verify bad request response
    mockMvc
        .perform(
            post("/education")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
        .andExpect(status().isBadRequest());
  }
}
