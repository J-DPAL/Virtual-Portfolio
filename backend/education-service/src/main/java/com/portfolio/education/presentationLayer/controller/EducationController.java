package com.portfolio.education.presentationLayer.controller;

import com.portfolio.education.businessLayer.service.EducationService;
import com.portfolio.education.mappingLayer.dto.EducationDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping
    public ResponseEntity<List<EducationDTO>> getAllEducation() {
        List<EducationDTO> educationList = educationService.getAllEducationOrderedByDate();
        return ResponseEntity.ok(educationList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationDTO> getEducationById(@PathVariable Long id) {
        EducationDTO education = educationService.getEducationById(id);
        return ResponseEntity.ok(education);
    }

    @GetMapping("/current")
    public ResponseEntity<List<EducationDTO>> getCurrentEducation() {
        List<EducationDTO> educationList = educationService.getCurrentEducation();
        return ResponseEntity.ok(educationList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EducationDTO> createEducation(@Valid @RequestBody EducationDTO educationDTO) {
        EducationDTO createdEducation = educationService.createEducation(educationDTO);
        return new ResponseEntity<>(createdEducation, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EducationDTO> updateEducation(@PathVariable Long id, 
                                                         @Valid @RequestBody EducationDTO educationDTO) {
        EducationDTO updatedEducation = educationService.updateEducation(id, educationDTO);
        return ResponseEntity.ok(updatedEducation);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Education service is running");
    }
}
