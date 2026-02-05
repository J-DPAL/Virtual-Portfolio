package com.portfolio.projects.businessLayer.service;

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

import com.portfolio.projects.dataAccessLayer.entity.Project;
import com.portfolio.projects.dataAccessLayer.repository.ProjectRepository;
import com.portfolio.projects.mappingLayer.dto.ProjectDTO;
import com.portfolio.projects.mappingLayer.mapper.ProjectMapper;
import com.portfolio.projects.utils.exceptions.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProjectService Unit Tests")
class ProjectServiceTest {

  @Mock private ProjectRepository projectRepository;
  @Mock private ProjectMapper projectMapper;

  @InjectMocks private ProjectService projectService;

  private Project testProject;
  private ProjectDTO testProjectDTO;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    testProject =
        Project.builder()
            .id(1L)
            .titleEn("Portfolio")
            .titleFr("Portfolio")
            .titleEs("Portafolio")
            .descriptionEn("Personal portfolio")
            .descriptionFr("Portfolio personnel")
            .descriptionEs("Portafolio personal")
            .technologies("Spring Boot, React")
            .projectUrl("https://example.com")
            .githubUrl("https://github.com/example")
            .imageUrl("https://example.com/image.png")
            .startDate(LocalDate.of(2024, 1, 1))
            .endDate(LocalDate.of(2024, 12, 31))
            .status("Completed")
            .build();

    testProjectDTO =
        ProjectDTO.builder()
            .id(1L)
            .titleEn("Portfolio")
            .titleFr("Portfolio")
            .titleEs("Portafolio")
            .descriptionEn("Personal portfolio")
            .descriptionFr("Portfolio personnel")
            .descriptionEs("Portafolio personal")
            .technologies("Spring Boot, React")
            .projectUrl("https://example.com")
            .githubUrl("https://github.com/example")
            .imageUrl("https://example.com/image.png")
            .startDate(LocalDate.of(2024, 1, 1))
            .endDate(LocalDate.of(2024, 12, 31))
            .status("Completed")
            .build();
  }

  @Test
  @DisplayName("Should retrieve all projects successfully")
  void testGetAllProjects_WithMultipleProjects_ReturnsAllProjects() {
    // Arrange: Setup mocks
    List<Project> projects = Arrays.asList(testProject);
    when(projectRepository.findAll()).thenReturn(projects);
    when(projectMapper.toDTO(testProject)).thenReturn(testProjectDTO);

    // Act: Call the service method
    List<ProjectDTO> result = projectService.getAllProjects();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(testProjectDTO, result.get(0));
    verify(projectRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("Should retrieve project by ID successfully")
  void testGetProjectById_WithValidId_ReturnsProject() {
    // Arrange: Setup mocks
    when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
    when(projectMapper.toDTO(testProject)).thenReturn(testProjectDTO);

    // Act: Call the service method
    ProjectDTO result = projectService.getProjectById(1L);

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(testProjectDTO, result);
    verify(projectRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Should throw exception when project ID does not exist")
  void testGetProjectById_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock to return empty Optional
    when(projectRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception is thrown
    assertThrows(
        ResourceNotFoundException.class,
        () -> projectService.getProjectById(999L),
        "Project not found");
    verify(projectRepository, times(1)).findById(999L);
  }

  @Test
  @DisplayName("Should retrieve projects by status successfully")
  void testGetProjectsByStatus_WithValidStatus_ReturnsProjectsByStatus() {
    // Arrange: Setup mocks
    List<Project> projects = Arrays.asList(testProject);
    when(projectRepository.findByStatus("Completed")).thenReturn(projects);
    when(projectMapper.toDTO(testProject)).thenReturn(testProjectDTO);

    // Act: Call the service method
    List<ProjectDTO> result = projectService.getProjectsByStatus("Completed");

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Completed", result.get(0).getStatus());
    verify(projectRepository, times(1)).findByStatus("Completed");
  }

  @Test
  @DisplayName("Should return empty list when no projects found by status")
  void testGetProjectsByStatus_WithNoProjects_ReturnsEmptyList() {
    // Arrange: Setup mock
    when(projectRepository.findByStatus("Archived")).thenReturn(Arrays.asList());

    // Act: Call the service method
    List<ProjectDTO> result = projectService.getProjectsByStatus("Archived");

    // Assert: Verify empty list
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(projectRepository, times(1)).findByStatus("Archived");
  }

  @Test
  @DisplayName("Should create project successfully")
  void testCreateProject_WithValidDTO_CreatesProject() {
    // Arrange: Setup mocks
    ProjectDTO newProjectDTO =
        ProjectDTO.builder()
            .titleEn("New Project")
            .titleFr("Nouveau projet")
            .titleEs("Nuevo proyecto")
            .status("Active")
            .build();

    Project newProject =
        Project.builder()
            .titleEn("New Project")
            .titleFr("Nouveau projet")
            .titleEs("Nuevo proyecto")
            .status("Active")
            .build();

    Project savedProject =
        Project.builder()
            .id(2L)
            .titleEn("New Project")
            .titleFr("Nouveau projet")
            .titleEs("Nuevo proyecto")
            .status("Active")
            .build();

    when(projectMapper.toEntity(newProjectDTO)).thenReturn(newProject);
    when(projectRepository.save(newProject)).thenReturn(savedProject);
    ProjectDTO savedDTO =
        ProjectDTO.builder()
            .id(2L)
            .titleEn("New Project")
            .titleFr("Nouveau projet")
            .titleEs("Nuevo proyecto")
            .status("Active")
            .build();
    when(projectMapper.toDTO(savedProject)).thenReturn(savedDTO);

    // Act: Call the service method
    ProjectDTO result = projectService.createProject(newProjectDTO);

    // Assert: Verify results
    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals("New Project", result.getTitleEn());
    verify(projectRepository, times(1)).save(any(Project.class));
  }

  @Test
  @DisplayName("Should update project successfully")
  void testUpdateProject_WithValidIdAndDTO_UpdatesProject() {
    // Arrange: Setup mocks
    ProjectDTO updateDTO =
        ProjectDTO.builder()
            .titleEn("Updated Project")
            .titleFr("Projet mis à jour")
            .titleEs("Proyecto actualizado")
            .status("Active")
            .build();

    when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
    when(projectRepository.save(any(Project.class))).thenReturn(testProject);
    when(projectMapper.toDTO(testProject)).thenReturn(testProjectDTO);

    // Act: Call the service method
    ProjectDTO result = projectService.updateProject(1L, updateDTO);

    // Assert: Verify results
    assertNotNull(result);
    verify(projectRepository, times(1)).findById(1L);
    verify(projectRepository, times(1)).save(any(Project.class));
  }

  @Test
  @DisplayName("Should throw exception when updating non-existent project")
  void testUpdateProject_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    ProjectDTO updateDTO = new ProjectDTO();
    when(projectRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception is thrown
    assertThrows(
        ResourceNotFoundException.class, () -> projectService.updateProject(999L, updateDTO));
    verify(projectRepository, times(1)).findById(999L);
    verify(projectRepository, never()).save(any());
  }

  @Test
  @DisplayName("Should delete project successfully")
  void testDeleteProject_WithValidId_DeletesProject() {
    // Arrange: Setup mocks
    when(projectRepository.existsById(1L)).thenReturn(true);
    doNothing().when(projectRepository).deleteById(1L);

    // Act: Call the service method
    projectService.deleteProject(1L);

    // Assert: Verify deletion
    verify(projectRepository, times(1)).existsById(1L);
    verify(projectRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Should throw exception when deleting non-existent project")
  void testDeleteProject_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    when(projectRepository.existsById(999L)).thenReturn(false);

    // Act & Assert: Verify exception is thrown
    assertThrows(ResourceNotFoundException.class, () -> projectService.deleteProject(999L));
    verify(projectRepository, times(1)).existsById(999L);
    verify(projectRepository, never()).deleteById(anyLong());
  }
}
