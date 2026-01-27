package com.portfolio.testimonials.presentationLayer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.portfolio.testimonials.businessLayer.service.TestimonialService;
import com.portfolio.testimonials.mappingLayer.dto.TestimonialDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/testimonials")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class TestimonialController {

  private final TestimonialService testimonialService;

  public TestimonialController(TestimonialService testimonialService) {
    this.testimonialService = testimonialService;
  }

  @GetMapping
  public ResponseEntity<List<TestimonialDTO>> getApprovedTestimonials() {
    List<TestimonialDTO> testimonials = testimonialService.getApprovedTestimonials();
    return ResponseEntity.ok(testimonials);
  }

  @GetMapping("/pending")
  public ResponseEntity<List<TestimonialDTO>> getPendingTestimonials() {
    List<TestimonialDTO> testimonials = testimonialService.getPendingTestimonials();
    return ResponseEntity.ok(testimonials);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TestimonialDTO> getTestimonialById(@PathVariable Long id) {
    TestimonialDTO testimonial = testimonialService.getTestimonialById(id);
    return ResponseEntity.ok(testimonial);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<TestimonialDTO> createTestimonial(
      @Valid @RequestBody TestimonialDTO testimonialDTO) {
    TestimonialDTO createdTestimonial = testimonialService.createTestimonial(testimonialDTO);
    return new ResponseEntity<>(createdTestimonial, HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<TestimonialDTO> updateTestimonial(
      @PathVariable Long id, @Valid @RequestBody TestimonialDTO testimonialDTO) {
    TestimonialDTO updatedTestimonial = testimonialService.updateTestimonial(id, testimonialDTO);
    return ResponseEntity.ok(updatedTestimonial);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{id}/approve")
  public ResponseEntity<TestimonialDTO> approveTestimonial(@PathVariable Long id) {
    TestimonialDTO approvedTestimonial = testimonialService.approveTestimonial(id);
    return ResponseEntity.ok(approvedTestimonial);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{id}/reject")
  public ResponseEntity<TestimonialDTO> rejectTestimonial(@PathVariable Long id) {
    TestimonialDTO rejectedTestimonial = testimonialService.rejectTestimonial(id);
    return ResponseEntity.ok(rejectedTestimonial);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {
    testimonialService.deleteTestimonial(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Testimonials service is running");
  }
}
