package com.portfolio.projects.dataAccessLayer.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.projects.dataAccessLayer.entity.Project;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("ProjectRepository Integration Tests")
class ProjectRepositoryTest {

  @Autowired private ProjectRepository projectRepository;

  private Project testProject;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data and clear repository
    projectRepository.deleteAll();
    testProject =
        Project.builder()
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
  @DisplayName("Should save a new project successfully")
  void testSaveProject_WithValidProject_ReturnsSavedProject() {
    // Arrange: Project entity is created in setUp

    // Act: Save the project
    Project savedProject = projectRepository.save(testProject);

    // Assert: Verify project is saved with generated ID
    assertNotNull(savedProject.getId());
    assertEquals("Portfolio", savedProject.getTitleEn());
    assertEquals("Completed", savedProject.getStatus());
  }

  @Test
  @DisplayName("Should find project by ID successfully")
  void testFindById_WithExistingProjectId_ReturnsProject() {
    // Arrange: Save project first
    Project savedProject = projectRepository.save(testProject);

    // Act: Find project by ID
    Optional<Project> foundProject = projectRepository.findById(savedProject.getId());

    // Assert: Verify project is found
    assertTrue(foundProject.isPresent());
    assertEquals("Portfolio", foundProject.get().getTitleEn());
  }

  @Test
  @DisplayName("Should return empty Optional when project ID does not exist")
  void testFindById_WithNonExistentProjectId_ReturnsEmptyOptional() {
    // Arrange: Project not saved

    // Act: Try to find project by ID
    Optional<Project> foundProject = projectRepository.findById(999L);

    // Assert: Verify Optional is empty
    assertFalse(foundProject.isPresent());
  }

  @Test
  @DisplayName("Should find projects by status successfully")
  void testFindByStatus_WithValidStatus_ReturnsProjectsByStatus() {
    // Arrange: Save project first
    projectRepository.save(testProject);

    // Act: Find projects by status
    List<Project> completedProjects = projectRepository.findByStatus("Completed");

    // Assert: Verify projects are found
    assertNotNull(completedProjects);
    assertTrue(completedProjects.size() > 0);
    assertEquals("Completed", completedProjects.get(0).getStatus());
  }

  @Test
  @DisplayName("Should return empty list when no projects in status")
  void testFindByStatus_WithNonExistentStatus_ReturnsEmptyList() {
    // Arrange: Save project first
    projectRepository.save(testProject);

    // Act: Find projects by non-existent status
    List<Project> archivedProjects = projectRepository.findByStatus("Archived");

    // Assert: Verify empty list
    assertNotNull(archivedProjects);
    assertTrue(archivedProjects.isEmpty());
  }

  @Test
  @DisplayName("Should find projects ordered by start date descending")
  void testFindByOrderByStartDateDesc_ReturnsProjectsInCorrectOrder() {
    // Arrange: Save two projects with different start dates
    Project project1 = testProject;
    Project project2 =
        Project.builder()
            .titleEn("Second Project")
            .titleFr("Deuxième projet")
            .titleEs("Segundo proyecto")
            .status("Active")
            .startDate(LocalDate.of(2025, 1, 1))
            .build();
    projectRepository.save(project1);
    projectRepository.save(project2);

    // Act: Retrieve projects ordered by start date desc
    List<Project> projects = projectRepository.findByOrderByStartDateDesc();

    // Assert: Verify order
    assertEquals(2, projects.size());
    assertEquals("Second Project", projects.get(0).getTitleEn());
  }

  @Test
  @DisplayName("Should update project successfully")
  void testUpdateProject_WithValidChanges_ReturnUpdatedProject() {
    // Arrange: Save project first
    Project savedProject = projectRepository.save(testProject);

    // Act: Update project details
    savedProject.setTitleEn("Updated Portfolio");
    savedProject.setStatus("Active");
    Project updatedProject = projectRepository.save(savedProject);

    // Assert: Verify project is updated
    assertEquals(savedProject.getId(), updatedProject.getId());
    assertEquals("Updated Portfolio", updatedProject.getTitleEn());
  }

  @Test
  @DisplayName("Should delete project successfully")
  void testDeleteProject_WithExistingProject_ProjectIsRemoved() {
    // Arrange: Save project first
    Project savedProject = projectRepository.save(testProject);
    Long projectId = savedProject.getId();

    // Act: Delete the project
    projectRepository.deleteById(projectId);

    // Assert: Verify project is deleted
    Optional<Project> deletedProject = projectRepository.findById(projectId);
    assertFalse(deletedProject.isPresent());
  }
}
