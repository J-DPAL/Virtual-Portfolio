package com.portfolio.hobbies.presentationLayer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.portfolio.hobbies.businessLayer.service.HobbyService;
import com.portfolio.hobbies.mappingLayer.dto.HobbyDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hobbies")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class HobbyController {

  private final HobbyService hobbyService;

  public HobbyController(HobbyService hobbyService) {
    this.hobbyService = hobbyService;
  }

  @GetMapping
  public ResponseEntity<List<HobbyDTO>> getAllHobbies() {
    List<HobbyDTO> hobbies = hobbyService.getAllHobbies();
    return ResponseEntity.ok(hobbies);
  }

  @GetMapping("/{id}")
  public ResponseEntity<HobbyDTO> getHobbyById(@PathVariable Long id) {
    HobbyDTO hobby = hobbyService.getHobbyById(id);
    return ResponseEntity.ok(hobby);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<HobbyDTO> createHobby(@Valid @RequestBody HobbyDTO hobbyDTO) {
    HobbyDTO createdHobby = hobbyService.createHobby(hobbyDTO);
    return new ResponseEntity<>(createdHobby, HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<HobbyDTO> updateHobby(
      @PathVariable Long id, @Valid @RequestBody HobbyDTO hobbyDTO) {
    HobbyDTO updatedHobby = hobbyService.updateHobby(id, hobbyDTO);
    return ResponseEntity.ok(updatedHobby);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHobby(@PathVariable Long id) {
    hobbyService.deleteHobby(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Hobbies service is running");
  }
}
