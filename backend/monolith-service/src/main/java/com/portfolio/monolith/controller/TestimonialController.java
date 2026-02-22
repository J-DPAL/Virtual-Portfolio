package com.portfolio.monolith.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.monolith.dto.TestimonialDto;
import com.portfolio.monolith.dto.TestimonialStatus;
import com.portfolio.monolith.exception.ForbiddenException;
import com.portfolio.monolith.security.AuthenticatedUser;
import com.portfolio.monolith.service.TestimonialDataService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/testimonials")
@Validated
public class TestimonialController {

  private final TestimonialDataService service;

  public TestimonialController(TestimonialDataService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<TestimonialDto>> getApprovedTestimonials() {
    return ResponseEntity.ok(service.getApprovedTestimonials());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/pending")
  public ResponseEntity<List<TestimonialDto>> getPendingTestimonials() {
    return ResponseEntity.ok(service.getPendingTestimonials());
  }

  @GetMapping("/{id}")
  public ResponseEntity<TestimonialDto> getTestimonialById(
      @PathVariable Long id, @AuthenticationPrincipal AuthenticatedUser user) {
    TestimonialDto testimonial = service.getTestimonialById(id);
    if (testimonial.status != TestimonialStatus.APPROVED && (user == null || !user.isAdmin())) {
      throw new ForbiddenException("Access denied");
    }
    return ResponseEntity.ok(testimonial);
  }

  @PostMapping
  public ResponseEntity<TestimonialDto> createTestimonial(@Valid @RequestBody TestimonialDto dto) {
    dto.status = TestimonialStatus.PENDING;
    return new ResponseEntity<>(service.createTestimonial(dto), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<TestimonialDto> updateTestimonial(@PathVariable Long id, @Valid @RequestBody TestimonialDto dto) {
    return ResponseEntity.ok(service.updateTestimonial(id, dto));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{id}/approve")
  public ResponseEntity<TestimonialDto> approveTestimonial(@PathVariable Long id) {
    return ResponseEntity.ok(service.approveTestimonial(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{id}/reject")
  public ResponseEntity<TestimonialDto> rejectTestimonial(@PathVariable Long id) {
    return ResponseEntity.ok(service.rejectTestimonial(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {
    service.deleteTestimonial(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Testimonials service is running");
  }
}
