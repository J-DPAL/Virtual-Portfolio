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

import com.portfolio.monolith.dto.HobbyDto;
import com.portfolio.monolith.service.HobbyDataService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hobbies")
@Validated
public class HobbyController {

  private final HobbyDataService service;

  public HobbyController(HobbyDataService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<HobbyDto>> getAllHobbies() {
    return ResponseEntity.ok(service.getAllHobbies());
  }

  @GetMapping("/{id}")
  public ResponseEntity<HobbyDto> getHobbyById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getHobbyById(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<HobbyDto> createHobby(@Valid @RequestBody HobbyDto dto) {
    return new ResponseEntity<>(service.createHobby(dto), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<HobbyDto> updateHobby(@PathVariable Long id, @Valid @RequestBody HobbyDto dto) {
    return ResponseEntity.ok(service.updateHobby(id, dto));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHobby(@PathVariable Long id) {
    service.deleteHobby(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Hobbies service is running");
  }
}
