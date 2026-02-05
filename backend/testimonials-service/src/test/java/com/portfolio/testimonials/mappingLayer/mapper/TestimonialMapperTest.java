package com.portfolio.testimonials.mappingLayer.mapper;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial;
import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial.TestimonialStatus;
import com.portfolio.testimonials.mappingLayer.dto.TestimonialDTO;

import static org.junit.jupiter.api.Assertions.*;

class TestimonialMapperTest {

  private TestimonialMapper testimonialMapper;
  private ModelMapper modelMapper;

  @BeforeEach
  void setUp() {
    modelMapper = new ModelMapper();
    testimonialMapper = new TestimonialMapper(modelMapper);
  }

  @Test
  void testToDTO() {
    LocalDateTime now = LocalDateTime.now();
    Testimonial testimonial =
        Testimonial.builder()
            .id(1L)
            .clientName("John Doe")
            .clientPosition("Software Engineer")
            .clientCompany("Tech Corp")
            .testimonialTextEn("Great work")
            .testimonialTextFr("Excellent travail")
            .testimonialTextEs("Excelente trabajo")
            .rating(5)
            .clientImageUrl("https://example.com/image.jpg")
            .status(TestimonialStatus.APPROVED)
            .createdAt(now)
            .updatedAt(now)
            .build();

    TestimonialDTO dto = testimonialMapper.toDTO(testimonial);

    assertNotNull(dto);
    assertEquals(1L, dto.getId());
    assertEquals("John Doe", dto.getClientName());
    assertEquals("Software Engineer", dto.getClientPosition());
    assertEquals("Tech Corp", dto.getClientCompany());
    assertEquals("Great work", dto.getTestimonialTextEn());
    assertEquals("Excellent travail", dto.getTestimonialTextFr());
    assertEquals("Excelente trabajo", dto.getTestimonialTextEs());
    assertEquals(5, dto.getRating());
    assertEquals("https://example.com/image.jpg", dto.getClientImageUrl());
    assertEquals(TestimonialStatus.APPROVED, dto.getStatus());
    assertEquals(now, dto.getCreatedAt());
    assertEquals(now, dto.getUpdatedAt());
  }

  @Test
  void testToEntity() {
    LocalDateTime now = LocalDateTime.now();
    TestimonialDTO dto =
        TestimonialDTO.builder()
            .id(1L)
            .clientName("Jane Smith")
            .clientPosition("Designer")
            .clientCompany("Design Studio")
            .testimonialTextEn("Perfect design")
            .testimonialTextFr("Design parfait")
            .testimonialTextEs("Diseño perfecto")
            .rating(4)
            .clientImageUrl("https://example.com/jane.jpg")
            .status(TestimonialStatus.APPROVED)
            .createdAt(now)
            .updatedAt(now)
            .build();

    Testimonial testimonial = testimonialMapper.toEntity(dto);

    assertNotNull(testimonial);
    assertEquals(1L, testimonial.getId());
    assertEquals("Jane Smith", testimonial.getClientName());
    assertEquals("Designer", testimonial.getClientPosition());
    assertEquals("Design Studio", testimonial.getClientCompany());
    assertEquals("Perfect design", testimonial.getTestimonialTextEn());
    assertEquals("Design parfait", testimonial.getTestimonialTextFr());
    assertEquals("Diseño perfecto", testimonial.getTestimonialTextEs());
    assertEquals(4, testimonial.getRating());
    assertEquals("https://example.com/jane.jpg", testimonial.getClientImageUrl());
    assertEquals(TestimonialStatus.APPROVED, testimonial.getStatus());
  }

  @Test
  void testNullOptionalFieldsMapping() {
    Testimonial testimonial =
        Testimonial.builder()
            .id(2L)
            .clientName("Bob Wilson")
            .clientPosition("Manager")
            .clientCompany("Tech Solutions")
            .testimonialTextEn("Amazing project")
            .testimonialTextFr("Projet incroyable")
            .testimonialTextEs("Proyecto asombroso")
            .rating(5)
            .clientImageUrl(null)
            .status(TestimonialStatus.PENDING)
            .build();

    TestimonialDTO dto = testimonialMapper.toDTO(testimonial);

    assertNotNull(dto);
    assertNull(dto.getClientImageUrl());
    assertEquals(TestimonialStatus.PENDING, dto.getStatus());
  }

  @Test
  void testRoundTripMapping() {
    LocalDateTime now = LocalDateTime.now();
    Testimonial originalTestimonial =
        Testimonial.builder()
            .id(3L)
            .clientName("Alice Johnson")
            .clientPosition("Developer")
            .clientCompany("Code Factory")
            .testimonialTextEn("Excellent code quality")
            .testimonialTextFr("Qualité de code excellente")
            .testimonialTextEs("Excelente calidad de código")
            .rating(5)
            .clientImageUrl("https://example.com/alice.jpg")
            .status(TestimonialStatus.APPROVED)
            .createdAt(now)
            .updatedAt(now)
            .build();

    TestimonialDTO dto = testimonialMapper.toDTO(originalTestimonial);
    Testimonial mappedTestimonial = testimonialMapper.toEntity(dto);

    assertEquals(originalTestimonial.getId(), mappedTestimonial.getId());
    assertEquals(originalTestimonial.getClientName(), mappedTestimonial.getClientName());
    assertEquals(originalTestimonial.getClientPosition(), mappedTestimonial.getClientPosition());
    assertEquals(originalTestimonial.getClientCompany(), mappedTestimonial.getClientCompany());
    assertEquals(
        originalTestimonial.getTestimonialTextEn(), mappedTestimonial.getTestimonialTextEn());
    assertEquals(originalTestimonial.getRating(), mappedTestimonial.getRating());
  }
}
