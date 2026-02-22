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

import com.portfolio.monolith.dto.WorkExperienceDto;
import com.portfolio.monolith.service.ExperienceDataService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/experience")
@Validated
public class WorkExperienceController {

  private final ExperienceDataService service;

  public WorkExperienceController(ExperienceDataService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<WorkExperienceDto>> getAllExperiences() {
    return ResponseEntity.ok(service.getAllExperiences());
  }

  @GetMapping("/{id}")
  public ResponseEntity<WorkExperienceDto> getExperienceById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getExperienceById(id));
  }

  @GetMapping("/current")
  public ResponseEntity<List<WorkExperienceDto>> getCurrentExperiences() {
    return ResponseEntity.ok(service.getCurrentExperiences());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<WorkExperienceDto> createExperience(@Valid @RequestBody WorkExperienceDto dto) {
    return new ResponseEntity<>(service.createExperience(dto), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<WorkExperienceDto> updateExperience(@PathVariable Long id, @Valid @RequestBody WorkExperienceDto dto) {
    return ResponseEntity.ok(service.updateExperience(id, dto));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
    service.deleteExperience(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Experience service is running");
  }
}
