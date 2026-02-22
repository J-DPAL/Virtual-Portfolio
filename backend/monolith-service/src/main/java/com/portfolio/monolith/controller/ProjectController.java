package com.portfolio.monolith.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.monolith.dto.ProjectDto;
import com.portfolio.monolith.service.ProjectDataService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
@Validated
public class ProjectController {

  private final ProjectDataService service;

  public ProjectController(ProjectDataService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<ProjectDto>> getAllProjects() {
    return ResponseEntity.ok(service.getAllProjects());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getProjectById(id));
  }

  @GetMapping("/status/{status}")
  public ResponseEntity<List<ProjectDto>> getProjectsByStatus(@PathVariable String status) {
    return ResponseEntity.ok(service.getProjectsByStatus(status));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectDto dto) {
    return new ResponseEntity<>(service.createProject(dto), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<ProjectDto> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectDto dto) {
    return ResponseEntity.ok(service.updateProject(id, dto));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
    service.deleteProject(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Projects service is running");
  }
}
