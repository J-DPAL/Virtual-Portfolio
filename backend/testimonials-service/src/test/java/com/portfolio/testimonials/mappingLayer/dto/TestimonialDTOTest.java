package com.portfolio.testimonials.mappingLayer.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial.TestimonialStatus;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class TestimonialDTOTest {

  private Validator validator;
  private TestimonialDTO testimonialDTO;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

    testimonialDTO =
        TestimonialDTO.builder()
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
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testNoArgsConstructor() {
    TestimonialDTO newDTO = new TestimonialDTO();
    assertNull(newDTO.getId());
    assertNull(newDTO.getClientName());
    assertNull(newDTO.getClientPosition());
    assertNull(newDTO.getClientCompany());
    assertNull(newDTO.getTestimonialTextEn());
    assertNull(newDTO.getTestimonialTextFr());
    assertNull(newDTO.getTestimonialTextEs());
    assertNull(newDTO.getRating());
    assertNull(newDTO.getClientImageUrl());
    assertNull(newDTO.getStatus());
    assertNull(newDTO.getCreatedAt());
    assertNull(newDTO.getUpdatedAt());
  }

  @Test
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    TestimonialDTO testDTO =
        new TestimonialDTO(
            1L,
            "Jane Smith",
            "Manager",
            "Company Inc",
            "Wonderful service",
            "Service merveilleux",
            "Servicio maravilloso",
            4,
            "https://example.com/jane.jpg",
            TestimonialStatus.APPROVED,
            now,
            now);

    assertEquals(1L, testDTO.getId());
    assertEquals("Jane Smith", testDTO.getClientName());
    assertEquals("Manager", testDTO.getClientPosition());
    assertEquals("Company Inc", testDTO.getClientCompany());
    assertEquals("Wonderful service", testDTO.getTestimonialTextEn());
    assertEquals("Service merveilleux", testDTO.getTestimonialTextFr());
    assertEquals("Servicio maravilloso", testDTO.getTestimonialTextEs());
    assertEquals(4, testDTO.getRating());
    assertEquals("https://example.com/jane.jpg", testDTO.getClientImageUrl());
    assertEquals(TestimonialStatus.APPROVED, testDTO.getStatus());
    assertEquals(now, testDTO.getCreatedAt());
    assertEquals(now, testDTO.getUpdatedAt());
  }

  @Test
  void testBuilder() {
    TestimonialDTO builtDTO =
        TestimonialDTO.builder()
            .id(2L)
            .clientName("Alice Johnson")
            .clientPosition("Designer")
            .clientCompany("Design Studio")
            .testimonialTextEn("Perfect design")
            .testimonialTextFr("Design parfait")
            .testimonialTextEs("Diseño perfecto")
            .rating(5)
            .clientImageUrl("https://example.com/alice.jpg")
            .status(TestimonialStatus.APPROVED)
            .build();

    assertEquals(2L, builtDTO.getId());
    assertEquals("Alice Johnson", builtDTO.getClientName());
    assertEquals("Designer", builtDTO.getClientPosition());
    assertEquals("Design Studio", builtDTO.getClientCompany());
  }

  @Test
  void testGettersAndSetters() {
    TestimonialDTO testDTO = new TestimonialDTO();
    testDTO.setId(3L);
    testDTO.setClientName("Bob Wilson");
    testDTO.setClientPosition("Developer");
    testDTO.setClientCompany("Tech Solutions");
    testDTO.setTestimonialTextEn("Amazing project");
    testDTO.setTestimonialTextFr("Projet incroyable");
    testDTO.setTestimonialTextEs("Proyecto asombroso");
    testDTO.setRating(5);
    testDTO.setClientImageUrl("https://example.com/bob.jpg");
    testDTO.setStatus(TestimonialStatus.APPROVED);
    testDTO.setCreatedAt(LocalDateTime.now());
    testDTO.setUpdatedAt(LocalDateTime.now());

    assertEquals(3L, testDTO.getId());
    assertEquals("Bob Wilson", testDTO.getClientName());
    assertEquals("Developer", testDTO.getClientPosition());
    assertEquals("Tech Solutions", testDTO.getClientCompany());
  }

  @Test
  void testValidDTO() {
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertTrue(violations.isEmpty(), "Valid DTO should have no violations");
  }

  @Test
  void testClientNameNotBlank() {
    testimonialDTO.setClientName("");
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> v.getMessage().contains("Client name is required")));
  }

  @Test
  void testClientNameNull() {
    testimonialDTO.setClientName(null);
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertFalse(violations.isEmpty());
  }

  @Test
  void testClientPositionNotBlank() {
    testimonialDTO.setClientPosition("");
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> v.getMessage().contains("Client position is required")));
  }

  @Test
  void testClientCompanyNotBlank() {
    testimonialDTO.setClientCompany("");
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertFalse(violations.isEmpty());
  }

  @Test
  void testTestimonialTextEnNotBlank() {
    testimonialDTO.setTestimonialTextEn("   ");
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertFalse(violations.isEmpty());
  }

  @Test
  void testTestimonialTextFrNotBlank() {
    testimonialDTO.setTestimonialTextFr("");
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertFalse(violations.isEmpty());
  }

  @Test
  void testTestimonialTextEsNotBlank() {
    testimonialDTO.setTestimonialTextEs(null);
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertFalse(violations.isEmpty());
  }

  @Test
  void testRatingNull() {
    testimonialDTO.setRating(null);
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Rating is required")));
  }

  @Test
  void testRatingMinValue() {
    testimonialDTO.setRating(0);
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> v.getMessage().contains("Rating must be at least 1")));
  }

  @Test
  void testRatingMaxValue() {
    testimonialDTO.setRating(6);
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream().anyMatch(v -> v.getMessage().contains("Rating must be at most 5")));
  }

  @Test
  void testRatingBoundaryMinValid() {
    testimonialDTO.setRating(1);
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertTrue(
        violations.isEmpty()
            || violations.stream().noneMatch(v -> v.getMessage().contains("Rating")));
  }

  @Test
  void testRatingBoundaryMaxValid() {
    testimonialDTO.setRating(5);
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertTrue(
        violations.isEmpty()
            || violations.stream().noneMatch(v -> v.getMessage().contains("Rating")));
  }

  @Test
  void testClientImageUrlCanBeNull() {
    testimonialDTO.setClientImageUrl(null);
    Set<ConstraintViolation<TestimonialDTO>> violations = validator.validate(testimonialDTO);
    assertTrue(
        violations.isEmpty()
            || violations.stream()
                .noneMatch(v -> v.getPropertyPath().toString().contains("clientImageUrl")));
  }

  @Test
  void testEqualsHashCodeToString() {
    TestimonialDTO another =
        TestimonialDTO.builder()
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
            .createdAt(testimonialDTO.getCreatedAt())
            .updatedAt(testimonialDTO.getUpdatedAt())
            .build();

    assertEquals(testimonialDTO, another);
    assertEquals(testimonialDTO.hashCode(), another.hashCode());
    assertNotNull(testimonialDTO.toString());
    assertTrue(testimonialDTO.toString().contains("John Doe"));
  }

  @Test
  void testEqualsWithFieldDifferences() {
    TestimonialDTO different =
        TestimonialDTO.builder()
            .id(2L)
            .clientName("Different Name")
            .clientPosition("Software Engineer")
            .clientCompany("Tech Corp")
            .testimonialTextEn("Great work")
            .testimonialTextFr("Excellent travail")
            .testimonialTextEs("Excelente trabajo")
            .rating(5)
            .clientImageUrl("https://example.com/image.jpg")
            .status(TestimonialStatus.APPROVED)
            .build();

    assertNotEquals(testimonialDTO, different);
  }

  @Test
  void testEqualsWithNullClientImageUrl() {
    TestimonialDTO testDTO1 =
        TestimonialDTO.builder()
            .id(1L)
            .clientName("John Doe")
            .clientPosition("Software Engineer")
            .clientCompany("Tech Corp")
            .testimonialTextEn("Great work")
            .testimonialTextFr("Excellent travail")
            .testimonialTextEs("Excelente trabajo")
            .rating(5)
            .clientImageUrl(null)
            .status(TestimonialStatus.APPROVED)
            .build();

    TestimonialDTO testDTO2 =
        TestimonialDTO.builder()
            .id(1L)
            .clientName("John Doe")
            .clientPosition("Software Engineer")
            .clientCompany("Tech Corp")
            .testimonialTextEn("Great work")
            .testimonialTextFr("Excellent travail")
            .testimonialTextEs("Excelente trabajo")
            .rating(5)
            .clientImageUrl(null)
            .status(TestimonialStatus.APPROVED)
            .build();

    assertEquals(testDTO1, testDTO2);
  }

  @ParameterizedTest
  @MethodSource("provideTestimonialStatusVariations")
  void testEqualsWithDifferentStatus(TestimonialStatus status) {
    LocalDateTime now = LocalDateTime.now();
    TestimonialDTO testDTO =
        TestimonialDTO.builder()
            .id(1L)
            .clientName("John Doe")
            .clientPosition("Software Engineer")
            .clientCompany("Tech Corp")
            .testimonialTextEn("Great work")
            .testimonialTextFr("Excellent travail")
            .testimonialTextEs("Excelente trabajo")
            .rating(5)
            .clientImageUrl("https://example.com/image.jpg")
            .status(status)
            .createdAt(now)
            .updatedAt(now)
            .build();

    TestimonialDTO baseDTO =
        TestimonialDTO.builder()
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

    if (!status.equals(TestimonialStatus.APPROVED)) {
      assertNotEquals(baseDTO, testDTO);
    } else {
      assertEquals(baseDTO, testDTO);
    }
  }

  @ParameterizedTest
  @MethodSource("provideRatingValues")
  void testEqualsWithDifferentRating(int rating) {
    LocalDateTime now = LocalDateTime.now();
    TestimonialDTO testDTO =
        TestimonialDTO.builder()
            .id(1L)
            .clientName("John Doe")
            .clientPosition("Software Engineer")
            .clientCompany("Tech Corp")
            .testimonialTextEn("Great work")
            .testimonialTextFr("Excellent travail")
            .testimonialTextEs("Excelente trabajo")
            .rating(rating)
            .clientImageUrl("https://example.com/image.jpg")
            .status(TestimonialStatus.APPROVED)
            .createdAt(now)
            .updatedAt(now)
            .build();

    TestimonialDTO baseDTO =
        TestimonialDTO.builder()
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

    if (rating != 5) {
      assertNotEquals(baseDTO, testDTO);
    } else {
      assertEquals(baseDTO, testDTO);
    }
  }

  private static Stream<TestimonialStatus> provideTestimonialStatusVariations() {
    return Stream.of(
        TestimonialStatus.PENDING, TestimonialStatus.APPROVED, TestimonialStatus.REJECTED);
  }

  private static Stream<Integer> provideRatingValues() {
    return Stream.of(1, 2, 3, 4, 5);
  }
}
