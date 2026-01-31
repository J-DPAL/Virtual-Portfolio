package com.portfolio.projects.presentationLayer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portfolio.projects.businessLayer.service.ProjectService;
import com.portfolio.projects.mappingLayer.dto.ProjectDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@SuppressWarnings("null")
public class ProjectController {

  private final ProjectService projectService;

  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @GetMapping
  public ResponseEntity<List<ProjectDTO>> getAllProjects() {
    List<ProjectDTO> projects = projectService.getAllProjects();
    return ResponseEntity.ok(projects);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
    ProjectDTO project = projectService.getProjectById(id);
    return ResponseEntity.ok(project);
  }

  @GetMapping("/status/{status}")
  public ResponseEntity<List<ProjectDTO>> getProjectsByStatus(@PathVariable String status) {
    List<ProjectDTO> projects = projectService.getProjectsByStatus(status);
    return ResponseEntity.ok(projects);
  }

  @PostMapping
  public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
    ProjectDTO createdProject = projectService.createProject(projectDTO);
    return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProjectDTO> updateProject(
      @PathVariable Long id, @Valid @RequestBody ProjectDTO projectDTO) {
    ProjectDTO updatedProject = projectService.updateProject(id, projectDTO);
    return ResponseEntity.ok(updatedProject);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
    projectService.deleteProject(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Projects service is running");
  }
}
