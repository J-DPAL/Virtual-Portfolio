package com.portfolio.skills.presentationLayer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portfolio.skills.businessLayer.service.SkillService;
import com.portfolio.skills.mappingLayer.dto.SkillDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/skills")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class SkillController {

  private final SkillService skillService;

  public SkillController(SkillService skillService) {
    this.skillService = skillService;
  }

  @GetMapping
  public ResponseEntity<List<SkillDTO>> getAllSkills() {
    List<SkillDTO> skills = skillService.getAllSkills();
    return ResponseEntity.ok(skills);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SkillDTO> getSkillById(@PathVariable Long id) {
    SkillDTO skill = skillService.getSkillById(id);
    return ResponseEntity.ok(skill);
  }

  @GetMapping("/category/{category}")
  public ResponseEntity<List<SkillDTO>> getSkillsByCategory(@PathVariable String category) {
    List<SkillDTO> skills = skillService.getSkillsByCategory(category);
    return ResponseEntity.ok(skills);
  }

  @PostMapping
  public ResponseEntity<SkillDTO> createSkill(@Valid @RequestBody SkillDTO skillDTO) {
    SkillDTO createdSkill = skillService.createSkill(skillDTO);
    return new ResponseEntity<>(createdSkill, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SkillDTO> updateSkill(
      @PathVariable Long id, @Valid @RequestBody SkillDTO skillDTO) {
    SkillDTO updatedSkill = skillService.updateSkill(id, skillDTO);
    return ResponseEntity.ok(updatedSkill);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
    skillService.deleteSkill(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Skills service is running");
  }
}
