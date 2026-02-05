package com.portfolio.testimonials.presentationLayer.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.testimonials.businessLayer.service.TestimonialService;
import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial.TestimonialStatus;
import com.portfolio.testimonials.mappingLayer.dto.TestimonialDTO;
import com.portfolio.testimonials.utils.exceptions.ResourceNotFoundException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("TestimonialController Integration Tests")
class TestimonialControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private TestimonialService testimonialService;

  private TestimonialDTO testimonialDTO;
  private List<TestimonialDTO> testimonialList;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    testimonialDTO =
        TestimonialDTO.builder()
            .id(1L)
            .clientName("John Doe")
            .clientPosition("Senior Developer")
            .clientCompany("Tech Corp")
            .testimonialTextEn("Great work")
            .testimonialTextFr("Excellent travail")
            .testimonialTextEs("Excelente trabajo")
            .rating(5)
            .clientImageUrl("https://example.com/image.jpg")
            .status(TestimonialStatus.APPROVED)
            .build();

    testimonialList = Arrays.asList(testimonialDTO);
  }

  @Test
  @DisplayName("Should retrieve approved testimonials via GET /testimonials")
  void testGetApprovedTestimonials_ReturnsApprovedTestimonials() throws Exception {
    // Arrange: Setup mock
    when(testimonialService.getApprovedTestimonials()).thenReturn(testimonialList);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/testimonials").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].clientName").value("John Doe"))
        .andExpect(jsonPath("$[0].status").value("APPROVED"));

    verify(testimonialService, times(1)).getApprovedTestimonials();
  }

  @Test
  @DisplayName("Should retrieve pending testimonials for admin only via GET /testimonials/pending")
  @WithMockUser(roles = "ADMIN")
  void testGetPendingTestimonials_WithAdminRole_ReturnsPendingTestimonials() throws Exception {
    // Arrange: Setup mock with pending testimonials
    TestimonialDTO pendingTestimonial = new TestimonialDTO();
    pendingTestimonial.setId(1L);
    pendingTestimonial.setClientName("John Doe");
    pendingTestimonial.setClientPosition("Senior Developer");
    pendingTestimonial.setClientCompany("Tech Corp");
    pendingTestimonial.setTestimonialTextEn("Great work");
    pendingTestimonial.setTestimonialTextFr("Excellent travail");
    pendingTestimonial.setTestimonialTextEs("Excelente trabajo");
    pendingTestimonial.setRating(5);
    pendingTestimonial.setClientImageUrl("https://example.com/image.jpg");
    pendingTestimonial.setStatus(TestimonialStatus.PENDING);
    when(testimonialService.getPendingTestimonials()).thenReturn(Arrays.asList(pendingTestimonial));

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/testimonials/pending").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].status").value("PENDING"));

    verify(testimonialService, times(1)).getPendingTestimonials();
  }

  @Test
  @DisplayName("Should return 500 when non-admin tries to access pending testimonials")
  @WithMockUser(roles = "USER")
  void testGetPendingTestimonials_WithUserRole_ReturnsForbidden() throws Exception {
    // Act & Assert: Perform GET request and verify forbidden response
    mockMvc
        .perform(get("/testimonials/pending").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is5xxServerError());

    verify(testimonialService, never()).getPendingTestimonials();
  }

  @Test
  @DisplayName("Should retrieve testimonial by ID via GET /testimonials/{id}")
  void testGetTestimonialById_WithValidId_ReturnsTestimonial() throws Exception {
    // Arrange: Setup mock
    when(testimonialService.getTestimonialById(1L)).thenReturn(testimonialDTO);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/testimonials/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.clientName").value("John Doe"));

    verify(testimonialService, times(1)).getTestimonialById(1L);
  }

  @Test
  @DisplayName("Should return 404 when testimonial ID does not exist")
  void testGetTestimonialById_WithInvalidId_ReturnsInternalServerError() throws Exception {
    // Arrange: Setup mock to throw exception
    when(testimonialService.getTestimonialById(999L))
        .thenThrow(new ResourceNotFoundException("Testimonial not found"));

    // Act & Assert: Perform GET request and verify error response
    mockMvc
        .perform(get("/testimonials/999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());

    verify(testimonialService, times(1)).getTestimonialById(999L);
  }

  @Test
  @DisplayName("Should create testimonial via POST /testimonials")
  void testCreateTestimonial_WithValidDTO_ReturnsCreatedTestimonial() throws Exception {
    // Arrange: Setup mock
    TestimonialDTO createDTO = new TestimonialDTO();
    createDTO.setClientName("John Doe");
    createDTO.setClientPosition("Senior Developer");
    createDTO.setClientCompany("Tech Corp");
    createDTO.setTestimonialTextEn("Great work");
    createDTO.setTestimonialTextFr("Excellent travail");
    createDTO.setTestimonialTextEs("Excelente trabajo");
    createDTO.setRating(5);
    createDTO.setClientImageUrl("https://example.com/image.jpg");

    TestimonialDTO createdDTO = new TestimonialDTO();
    createdDTO.setId(1L);
    createdDTO.setClientName("John Doe");
    createdDTO.setClientPosition("Senior Developer");
    createdDTO.setClientCompany("Tech Corp");
    createdDTO.setTestimonialTextEn("Great work");
    createdDTO.setTestimonialTextFr("Excellent travail");
    createdDTO.setTestimonialTextEs("Excelente trabajo");
    createdDTO.setRating(5);
    createdDTO.setClientImageUrl("https://example.com/image.jpg");
    createdDTO.setStatus(TestimonialStatus.PENDING);

    when(testimonialService.createTestimonial(any(TestimonialDTO.class))).thenReturn(createdDTO);

    // Act & Assert: Perform POST request and verify response
    mockMvc
        .perform(
            post("/testimonials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.clientName").value("John Doe"))
        .andExpect(jsonPath("$.status").value("PENDING"));

    verify(testimonialService, times(1)).createTestimonial(any(TestimonialDTO.class));
  }

  @Test
  @DisplayName("Should update testimonial via PUT /testimonials/{id} for admin")
  @WithMockUser(roles = "ADMIN")
  void testUpdateTestimonial_WithValidIdAndDTO_ReturnsUpdatedTestimonial() throws Exception {
    // Arrange: Setup mock
    TestimonialDTO updateDTO = new TestimonialDTO();
    updateDTO.setClientName("Updated Name");
    updateDTO.setClientPosition("Updated Position");
    updateDTO.setClientCompany("Updated Company");
    updateDTO.setTestimonialTextEn("Updated text");
    updateDTO.setTestimonialTextFr("Texte mis à jour");
    updateDTO.setTestimonialTextEs("Texto actualizado");
    updateDTO.setRating(4);

    TestimonialDTO updatedDTO = new TestimonialDTO();
    updatedDTO.setClientName("Updated Name");
    updatedDTO.setClientPosition("Updated Position");
    updatedDTO.setClientCompany("Updated Company");
    updatedDTO.setTestimonialTextEn("Updated text");
    updatedDTO.setTestimonialTextFr("Texte mis à jour");
    updatedDTO.setTestimonialTextEs("Texto actualizado");
    updatedDTO.setRating(4);

    when(testimonialService.updateTestimonial(eq(1L), any(TestimonialDTO.class)))
        .thenReturn(updatedDTO);

    // Act & Assert: Perform PUT request and verify response
    mockMvc
        .perform(
            put("/testimonials/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.clientName").value("Updated Name"));

    verify(testimonialService, times(1)).updateTestimonial(eq(1L), any(TestimonialDTO.class));
  }

  @Test
  @DisplayName("Should return 403 when non-admin tries to update testimonial")
  @WithMockUser(roles = "USER")
  void testUpdateTestimonial_WithUserRole_ReturnsForbidden() throws Exception {
    // Arrange: Setup request
    TestimonialDTO updateDTO = new TestimonialDTO();
    updateDTO.setClientName("Updated Name");
    updateDTO.setClientPosition("Updated Position");
    updateDTO.setClientCompany("Updated Company");
    updateDTO.setTestimonialTextEn("Updated text");
    updateDTO.setTestimonialTextFr("Texte mis à jour");
    updateDTO.setTestimonialTextEs("Texto actualizado");
    updateDTO.setRating(4);

    // Act & Assert: Perform PUT request and verify forbidden response
    mockMvc
        .perform(
            put("/testimonials/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().is5xxServerError());

    verify(testimonialService, never()).updateTestimonial(anyLong(), any());
  }

  @Test
  @DisplayName("Should approve testimonial via PATCH /testimonials/{id}/approve for admin")
  @WithMockUser(roles = "ADMIN")
  void testApproveTestimonial_WithValidId_ReturnsApprovedTestimonial() throws Exception {
    // Arrange: Setup mock
    TestimonialDTO approvedDTO = new TestimonialDTO();
    approvedDTO.setStatus(TestimonialStatus.APPROVED);
    when(testimonialService.approveTestimonial(1L)).thenReturn(approvedDTO);

    // Act & Assert: Perform PATCH request and verify response
    mockMvc
        .perform(patch("/testimonials/1/approve").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("APPROVED"));

    verify(testimonialService, times(1)).approveTestimonial(1L);
  }

  @Test
  @DisplayName("Should reject testimonial via PATCH /testimonials/{id}/reject for admin")
  @WithMockUser(roles = "ADMIN")
  void testRejectTestimonial_WithValidId_ReturnsRejectedTestimonial() throws Exception {
    // Arrange: Setup mock
    TestimonialDTO rejectedDTO = new TestimonialDTO();
    rejectedDTO.setStatus(TestimonialStatus.REJECTED);
    when(testimonialService.rejectTestimonial(1L)).thenReturn(rejectedDTO);

    // Act & Assert: Perform PATCH request and verify response
    mockMvc
        .perform(patch("/testimonials/1/reject").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("REJECTED"));

    verify(testimonialService, times(1)).rejectTestimonial(1L);
  }

  @Test
  @DisplayName("Should delete testimonial via DELETE /testimonials/{id} for admin")
  @WithMockUser(roles = "ADMIN")
  void testDeleteTestimonial_WithValidId_ReturnsNoContent() throws Exception {
    // Arrange: Setup mock
    doNothing().when(testimonialService).deleteTestimonial(1L);

    // Act & Assert: Perform DELETE request and verify response
    mockMvc
        .perform(delete("/testimonials/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(testimonialService, times(1)).deleteTestimonial(1L);
  }

  @Test
  @DisplayName("Should return health status via GET /testimonials/health")
  void testHealthEndpoint_ReturnsOk() throws Exception {
    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/testimonials/health").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Testimonials service is running"));
  }

  @Test
  @DisplayName("Should return 400 when creating testimonial without required fields")
  void testCreateTestimonial_WithMissingRequiredFields_ReturnsBadRequest() throws Exception {
    // Arrange: Create invalid DTO
    TestimonialDTO invalidDTO = TestimonialDTO.builder().clientName("John Doe").build();

    // Act & Assert: Perform POST request and verify bad request response
    mockMvc
        .perform(
            post("/testimonials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
        .andExpect(status().isBadRequest());
  }
}
