package com.portfolio.experience.presentationLayer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portfolio.experience.businessLayer.service.WorkExperienceService;
import com.portfolio.experience.mappingLayer.dto.WorkExperienceDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/experience")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@SuppressWarnings("null")
public class WorkExperienceController {

  private final WorkExperienceService workExperienceService;

  public WorkExperienceController(WorkExperienceService workExperienceService) {
    this.workExperienceService = workExperienceService;
  }

  @GetMapping
  public ResponseEntity<List<WorkExperienceDTO>> getAllExperiences() {
    List<WorkExperienceDTO> experiences = workExperienceService.getAllExperiencesOrderedByDate();
    return ResponseEntity.ok(experiences);
  }

  @GetMapping("/{id}")
  public ResponseEntity<WorkExperienceDTO> getExperienceById(@PathVariable Long id) {
    WorkExperienceDTO experience = workExperienceService.getExperienceById(id);
    return ResponseEntity.ok(experience);
  }

  @GetMapping("/current")
  public ResponseEntity<List<WorkExperienceDTO>> getCurrentExperiences() {
    List<WorkExperienceDTO> experiences = workExperienceService.getCurrentExperiences();
    return ResponseEntity.ok(experiences);
  }

  @PostMapping
  public ResponseEntity<WorkExperienceDTO> createExperience(
      @Valid @RequestBody WorkExperienceDTO workExperienceDTO) {
    WorkExperienceDTO createdExperience = workExperienceService.createExperience(workExperienceDTO);
    return new ResponseEntity<>(createdExperience, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<WorkExperienceDTO> updateExperience(
      @PathVariable Long id, @Valid @RequestBody WorkExperienceDTO workExperienceDTO) {
    WorkExperienceDTO updatedExperience =
        workExperienceService.updateExperience(id, workExperienceDTO);
    return ResponseEntity.ok(updatedExperience);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
    workExperienceService.deleteExperience(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Experience service is running");
  }
}
