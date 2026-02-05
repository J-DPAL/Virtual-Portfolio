package com.portfolio.projects.presentationLayer.controller;

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
import com.portfolio.projects.businessLayer.service.ProjectService;
import com.portfolio.projects.mappingLayer.dto.ProjectDTO;
import com.portfolio.projects.utils.exceptions.ResourceNotFoundException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("ProjectController Integration Tests")
class ProjectControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private ProjectService projectService;

  private ProjectDTO projectDTO;
  private List<ProjectDTO> projectList;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    projectDTO =
        ProjectDTO.builder()
            .id(1L)
            .titleEn("Portfolio")
            .titleFr("Portfolio")
            .titleEs("Portafolio")
            .status("Completed")
            .build();

    projectList = Arrays.asList(projectDTO);
  }

  @Test
  @DisplayName("Should retrieve all projects via GET /projects")
  void testGetAllProjects_ReturnsAllProjects() throws Exception {
    // Arrange: Setup mock
    when(projectService.getAllProjects()).thenReturn(projectList);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/projects").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].titleEn").value("Portfolio"));

    verify(projectService, times(1)).getAllProjects();
  }

  @Test
  @DisplayName("Should retrieve project by ID via GET /projects/{id}")
  void testGetProjectById_WithValidId_ReturnsProject() throws Exception {
    // Arrange: Setup mock
    when(projectService.getProjectById(1L)).thenReturn(projectDTO);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/projects/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.titleEn").value("Portfolio"));

    verify(projectService, times(1)).getProjectById(1L);
  }

  @Test
  @DisplayName("Should return 404 when project ID does not exist")
  void testGetProjectById_WithInvalidId_ReturnsNotFound() throws Exception {
    // Arrange: Setup mock to throw exception
    when(projectService.getProjectById(999L))
        .thenThrow(new ResourceNotFoundException("Project not found"));

    // Act & Assert: Perform GET request and verify error response
    mockMvc
        .perform(get("/projects/999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());

    verify(projectService, times(1)).getProjectById(999L);
  }

  @Test
  @DisplayName("Should retrieve projects by status via GET /projects/status/{status}")
  void testGetProjectsByStatus_WithValidStatus_ReturnsProjectsByStatus() throws Exception {
    // Arrange: Setup mock
    when(projectService.getProjectsByStatus("Completed")).thenReturn(projectList);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/projects/status/Completed").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].status").value("Completed"));

    verify(projectService, times(1)).getProjectsByStatus("Completed");
  }

  @Test
  @DisplayName("Should create project via POST /projects")
  void testCreateProject_WithValidDTO_ReturnsCreatedProject() throws Exception {
    // Arrange: Setup mock
    ProjectDTO createDTO =
        ProjectDTO.builder()
            .titleEn("New Project")
            .titleFr("Nouveau projet")
            .titleEs("Nuevo proyecto")
            .status("Active")
            .build();

    ProjectDTO createdDTO =
        ProjectDTO.builder()
            .id(2L)
            .titleEn("New Project")
            .titleFr("Nouveau projet")
            .titleEs("Nuevo proyecto")
            .status("Active")
            .build();
    when(projectService.createProject(any(ProjectDTO.class))).thenReturn(createdDTO);

    // Act & Assert: Perform POST request and verify response
    mockMvc
        .perform(
            post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.titleEn").value("New Project"));

    verify(projectService, times(1)).createProject(any(ProjectDTO.class));
  }

  @Test
  @DisplayName("Should update project via PUT /projects/{id}")
  void testUpdateProject_WithValidIdAndDTO_ReturnsUpdatedProject() throws Exception {
    // Arrange: Setup mock
    ProjectDTO updateDTO =
        ProjectDTO.builder()
            .titleEn("Updated Project")
            .titleFr("Projet mis à jour")
            .titleEs("Proyecto actualizado")
            .status("Active")
            .build();

    ProjectDTO updatedDTO =
        ProjectDTO.builder()
            .id(1L)
            .titleEn("Updated Project")
            .titleFr("Portfolio")
            .titleEs("Portafolio")
            .status("Completed")
            .build();
    when(projectService.updateProject(eq(1L), any(ProjectDTO.class))).thenReturn(updatedDTO);

    // Act & Assert: Perform PUT request and verify response
    mockMvc
        .perform(
            put("/projects/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.titleEn").value("Updated Project"));

    verify(projectService, times(1)).updateProject(eq(1L), any(ProjectDTO.class));
  }

  @Test
  @DisplayName("Should delete project via DELETE /projects/{id}")
  void testDeleteProject_WithValidId_ReturnsNoContent() throws Exception {
    // Arrange: Setup mock
    doNothing().when(projectService).deleteProject(1L);

    // Act & Assert: Perform DELETE request and verify response
    mockMvc
        .perform(delete("/projects/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(projectService, times(1)).deleteProject(1L);
  }

  @Test
  @DisplayName("Should return health status via GET /projects/health")
  void testHealthEndpoint_ReturnsOk() throws Exception {
    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/projects/health").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Projects service is running"));
  }

  @Test
  @DisplayName("Should return 400 when creating project without required fields")
  void testCreateProject_WithMissingRequiredFields_ReturnsBadRequest() throws Exception {
    // Arrange: Create invalid DTO
    ProjectDTO invalidDTO = ProjectDTO.builder().titleEn("Only English").build();

    // Act & Assert: Perform POST request and verify bad request response
    mockMvc
        .perform(
            post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
        .andExpect(status().isBadRequest());
  }
}
