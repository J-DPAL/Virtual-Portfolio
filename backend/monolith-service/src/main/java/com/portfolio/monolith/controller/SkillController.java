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

import com.portfolio.monolith.dto.SkillDto;
import com.portfolio.monolith.service.SkillDataService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/skills")
@Validated
public class SkillController {

  private final SkillDataService service;

  public SkillController(SkillDataService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<SkillDto>> getAllSkills() {
    return ResponseEntity.ok(service.getAllSkills());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SkillDto> getSkillById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getSkillById(id));
  }

  @GetMapping("/category/{category}")
  public ResponseEntity<List<SkillDto>> getSkillsByCategory(@PathVariable String category) {
    return ResponseEntity.ok(service.getSkillsByCategory(category));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<SkillDto> createSkill(@Valid @RequestBody SkillDto dto) {
    return new ResponseEntity<>(service.createSkill(dto), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<SkillDto> updateSkill(@PathVariable Long id, @Valid @RequestBody SkillDto dto) {
    return ResponseEntity.ok(service.updateSkill(id, dto));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
    service.deleteSkill(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Skills service is running");
  }
}
