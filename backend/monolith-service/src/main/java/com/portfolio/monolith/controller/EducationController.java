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

import com.portfolio.monolith.dto.EducationDto;
import com.portfolio.monolith.service.EducationDataService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/education")
@Validated
public class EducationController {

  private final EducationDataService service;

  public EducationController(EducationDataService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<EducationDto>> getAllEducation() {
    return ResponseEntity.ok(service.getAllEducation());
  }

  @GetMapping("/{id}")
  public ResponseEntity<EducationDto> getEducationById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getEducationById(id));
  }

  @GetMapping("/current")
  public ResponseEntity<List<EducationDto>> getCurrentEducation() {
    return ResponseEntity.ok(service.getCurrentEducation());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<EducationDto> createEducation(@Valid @RequestBody EducationDto dto) {
    return new ResponseEntity<>(service.createEducation(dto), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<EducationDto> updateEducation(@PathVariable Long id, @Valid @RequestBody EducationDto dto) {
    return ResponseEntity.ok(service.updateEducation(id, dto));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
    service.deleteEducation(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Education service is running");
  }
}
