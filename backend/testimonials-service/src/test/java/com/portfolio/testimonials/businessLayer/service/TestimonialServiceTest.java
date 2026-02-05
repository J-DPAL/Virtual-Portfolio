package com.portfolio.testimonials.businessLayer.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial;
import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial.TestimonialStatus;
import com.portfolio.testimonials.dataAccessLayer.repository.TestimonialRepository;
import com.portfolio.testimonials.mappingLayer.dto.TestimonialDTO;
import com.portfolio.testimonials.mappingLayer.mapper.TestimonialMapper;
import com.portfolio.testimonials.utils.exceptions.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TestimonialService Unit Tests")
class TestimonialServiceTest {

  @Mock private TestimonialRepository testimonialRepository;
  @Mock private TestimonialMapper testimonialMapper;

  @InjectMocks private TestimonialService testimonialService;

  private Testimonial testTestimonial;
  private TestimonialDTO testTestimonialDTO;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    testTestimonial =
        Testimonial.builder()
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
            .createdAt(LocalDateTime.now())
            .build();

    testTestimonialDTO =
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
  }

  @Test
  @DisplayName("Should retrieve all testimonials successfully")
  void testGetAllTestimonials_WithMultipleTestimonials_ReturnsAllTestimonials() {
    // Arrange: Setup mocks
    List<Testimonial> testimonials = Arrays.asList(testTestimonial);
    when(testimonialRepository.findAll()).thenReturn(testimonials);
    when(testimonialMapper.toDTO(testTestimonial)).thenReturn(testTestimonialDTO);

    // Act: Call the service method
    List<TestimonialDTO> result = testimonialService.getAllTestimonials();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(testTestimonialDTO, result.get(0));
    verify(testimonialRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("Should return approved testimonials ordered by creation date descending")
  void testGetApprovedTestimonials_ReturnsApprovedTestimonials() {
    // Arrange: Setup mocks
    List<Testimonial> approvedTestimonials = Arrays.asList(testTestimonial);
    when(testimonialRepository.findByStatusOrderByCreatedAtDesc(TestimonialStatus.APPROVED))
        .thenReturn(approvedTestimonials);
    when(testimonialMapper.toDTO(testTestimonial)).thenReturn(testTestimonialDTO);

    // Act: Call the service method
    List<TestimonialDTO> result = testimonialService.getApprovedTestimonials();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(TestimonialStatus.APPROVED, result.get(0).getStatus());
    verify(testimonialRepository, times(1))
        .findByStatusOrderByCreatedAtDesc(TestimonialStatus.APPROVED);
  }

  @Test
  @DisplayName("Should return pending testimonials ordered by creation date descending")
  void testGetPendingTestimonials_ReturnsPendingTestimonials() {
    // Arrange: Setup mocks
    testTestimonial.setStatus(TestimonialStatus.PENDING);
    testTestimonialDTO.setStatus(TestimonialStatus.PENDING);
    List<Testimonial> pendingTestimonials = Arrays.asList(testTestimonial);
    when(testimonialRepository.findByStatusOrderByCreatedAtDesc(TestimonialStatus.PENDING))
        .thenReturn(pendingTestimonials);
    when(testimonialMapper.toDTO(testTestimonial)).thenReturn(testTestimonialDTO);

    // Act: Call the service method
    List<TestimonialDTO> result = testimonialService.getPendingTestimonials();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(TestimonialStatus.PENDING, result.get(0).getStatus());
    verify(testimonialRepository, times(1))
        .findByStatusOrderByCreatedAtDesc(TestimonialStatus.PENDING);
  }

  @Test
  @DisplayName("Should retrieve testimonial by ID successfully")
  void testGetTestimonialById_WithValidId_ReturnsTestimonial() {
    // Arrange: Setup mocks
    when(testimonialRepository.findById(1L)).thenReturn(Optional.of(testTestimonial));
    when(testimonialMapper.toDTO(testTestimonial)).thenReturn(testTestimonialDTO);

    // Act: Call the service method
    TestimonialDTO result = testimonialService.getTestimonialById(1L);

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(testTestimonialDTO, result);
    verify(testimonialRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Should throw exception when testimonial ID does not exist")
  void testGetTestimonialById_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock to return empty Optional
    when(testimonialRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception is thrown
    assertThrows(
        ResourceNotFoundException.class,
        () -> testimonialService.getTestimonialById(999L),
        "Testimonial not found with id: 999");
    verify(testimonialRepository, times(1)).findById(999L);
  }

  @Test
  @DisplayName("Should create new testimonial with PENDING status")
  void testCreateTestimonial_WithValidDTO_CreatesTestimonialWithPendingStatus() {
    // Arrange: Setup mocks
    TestimonialDTO newTestimonialDTO =
        TestimonialDTO.builder()
            .clientName("Jane Smith")
            .clientPosition("Project Manager")
            .clientCompany("Tech Corp")
            .testimonialTextEn("Amazing work")
            .testimonialTextFr("Travail incroyable")
            .testimonialTextEs("Trabajo increíble")
            .rating(4)
            .clientImageUrl("https://example.com/jane.jpg")
            .build();

    Testimonial newTestimonial =
        Testimonial.builder()
            .clientName(newTestimonialDTO.getClientName())
            .clientPosition(newTestimonialDTO.getClientPosition())
            .clientCompany(newTestimonialDTO.getClientCompany())
            .testimonialTextEn(newTestimonialDTO.getTestimonialTextEn())
            .testimonialTextFr(newTestimonialDTO.getTestimonialTextFr())
            .testimonialTextEs(newTestimonialDTO.getTestimonialTextEs())
            .rating(newTestimonialDTO.getRating())
            .clientImageUrl(newTestimonialDTO.getClientImageUrl())
            .status(TestimonialStatus.PENDING)
            .build();

    Testimonial savedTestimonial =
        Testimonial.builder()
            .id(2L)
            .clientName(newTestimonialDTO.getClientName())
            .clientPosition(newTestimonialDTO.getClientPosition())
            .clientCompany(newTestimonialDTO.getClientCompany())
            .testimonialTextEn(newTestimonialDTO.getTestimonialTextEn())
            .testimonialTextFr(newTestimonialDTO.getTestimonialTextFr())
            .testimonialTextEs(newTestimonialDTO.getTestimonialTextEs())
            .rating(newTestimonialDTO.getRating())
            .clientImageUrl(newTestimonialDTO.getClientImageUrl())
            .status(TestimonialStatus.PENDING)
            .build();

    when(testimonialMapper.toEntity(newTestimonialDTO)).thenReturn(newTestimonial);
    when(testimonialRepository.save(newTestimonial)).thenReturn(savedTestimonial);
    TestimonialDTO savedDTO = new TestimonialDTO();
    savedDTO.setId(2L);
    savedDTO.setStatus(TestimonialStatus.PENDING);
    savedDTO.setClientName("Jane Smith");
    when(testimonialMapper.toDTO(savedTestimonial)).thenReturn(savedDTO);

    // Act: Call the service method
    TestimonialDTO result = testimonialService.createTestimonial(newTestimonialDTO);

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(TestimonialStatus.PENDING, result.getStatus());
    assertEquals("Jane Smith", result.getClientName());
    verify(testimonialRepository, times(1)).save(any(Testimonial.class));
  }

  @Test
  @DisplayName("Should update testimonial successfully")
  void testUpdateTestimonial_WithValidIdAndDTO_UpdatesTestimonial() {
    // Arrange: Setup mocks
    TestimonialDTO updateDTO =
        TestimonialDTO.builder()
            .clientName("Updated Name")
            .clientPosition("Updated Position")
            .clientCompany("Updated Company")
            .testimonialTextEn("Updated text")
            .testimonialTextFr("Texte mis à jour")
            .testimonialTextEs("Texto actualizado")
            .rating(4)
            .clientImageUrl("https://example.com/updated.jpg")
            .build();

    when(testimonialRepository.findById(1L)).thenReturn(Optional.of(testTestimonial));
    when(testimonialRepository.save(any(Testimonial.class))).thenReturn(testTestimonial);
    when(testimonialMapper.toDTO(testTestimonial)).thenReturn(testTestimonialDTO);

    // Act: Call the service method
    TestimonialDTO result = testimonialService.updateTestimonial(1L, updateDTO);

    // Assert: Verify results
    assertNotNull(result);
    verify(testimonialRepository, times(1)).findById(1L);
    verify(testimonialRepository, times(1)).save(any(Testimonial.class));
  }

  @Test
  @DisplayName("Should throw exception when updating non-existent testimonial")
  void testUpdateTestimonial_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    TestimonialDTO updateDTO = new TestimonialDTO();
    when(testimonialRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception is thrown
    assertThrows(
        ResourceNotFoundException.class,
        () -> testimonialService.updateTestimonial(999L, updateDTO));
    verify(testimonialRepository, times(1)).findById(999L);
    verify(testimonialRepository, never()).save(any());
  }

  @Test
  @DisplayName("Should approve testimonial successfully")
  void testApproveTestimonial_WithValidId_ChangesStatusToApproved() {
    // Arrange: Setup mocks
    testTestimonial.setStatus(TestimonialStatus.PENDING);
    when(testimonialRepository.findById(1L)).thenReturn(Optional.of(testTestimonial));
    when(testimonialRepository.save(any(Testimonial.class))).thenReturn(testTestimonial);
    testTestimonialDTO.setStatus(TestimonialStatus.APPROVED);
    when(testimonialMapper.toDTO(testTestimonial)).thenReturn(testTestimonialDTO);

    // Act: Call the service method
    TestimonialDTO result = testimonialService.approveTestimonial(1L);

    // Assert: Verify results
    assertNotNull(result);
    verify(testimonialRepository, times(1)).findById(1L);
    verify(testimonialRepository, times(1)).save(any(Testimonial.class));
  }

  @Test
  @DisplayName("Should reject testimonial successfully")
  void testRejectTestimonial_WithValidId_ChangesStatusToRejected() {
    // Arrange: Setup mocks
    testTestimonial.setStatus(TestimonialStatus.PENDING);
    when(testimonialRepository.findById(1L)).thenReturn(Optional.of(testTestimonial));
    when(testimonialRepository.save(any(Testimonial.class))).thenReturn(testTestimonial);
    testTestimonialDTO.setStatus(TestimonialStatus.REJECTED);
    when(testimonialMapper.toDTO(testTestimonial)).thenReturn(testTestimonialDTO);

    // Act: Call the service method
    TestimonialDTO result = testimonialService.rejectTestimonial(1L);

    // Assert: Verify results
    assertNotNull(result);
    verify(testimonialRepository, times(1)).findById(1L);
    verify(testimonialRepository, times(1)).save(any(Testimonial.class));
  }

  @Test
  @DisplayName("Should delete testimonial successfully")
  void testDeleteTestimonial_WithValidId_DeletesTestimonial() {
    // Arrange: Setup mocks
    when(testimonialRepository.existsById(1L)).thenReturn(true);
    doNothing().when(testimonialRepository).deleteById(1L);

    // Act: Call the service method
    testimonialService.deleteTestimonial(1L);

    // Assert: Verify deletion
    verify(testimonialRepository, times(1)).existsById(1L);
    verify(testimonialRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Should throw exception when deleting non-existent testimonial")
  void testDeleteTestimonial_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    when(testimonialRepository.existsById(999L)).thenReturn(false);

    // Act & Assert: Verify exception is thrown
    assertThrows(ResourceNotFoundException.class, () -> testimonialService.deleteTestimonial(999L));
    verify(testimonialRepository, times(1)).existsById(999L);
    verify(testimonialRepository, never()).deleteById(anyLong());
  }

  @Test
  @DisplayName("Should return empty list when no approved testimonials exist")
  void testGetApprovedTestimonials_WithNoApprovedTestimonials_ReturnsEmptyList() {
    // Arrange: Setup mock
    when(testimonialRepository.findByStatusOrderByCreatedAtDesc(TestimonialStatus.APPROVED))
        .thenReturn(Arrays.asList());

    // Act: Call the service method
    List<TestimonialDTO> result = testimonialService.getApprovedTestimonials();

    // Assert: Verify empty list
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(testimonialRepository, times(1))
        .findByStatusOrderByCreatedAtDesc(TestimonialStatus.APPROVED);
  }
}
