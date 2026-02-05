package com.portfolio.testimonials.dataAccessLayer.entity;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial.TestimonialStatus;

import static org.junit.jupiter.api.Assertions.*;

class TestimonialEntityTest {

  private Testimonial testimonial;

  @BeforeEach
  void setUp() {
    testimonial =
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
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testNoArgsConstructor() {
    Testimonial newTestimonial = new Testimonial();
    assertNull(newTestimonial.getId());
    assertNull(newTestimonial.getClientName());
    assertNull(newTestimonial.getClientPosition());
    assertNull(newTestimonial.getClientCompany());
    assertNull(newTestimonial.getTestimonialTextEn());
    assertNull(newTestimonial.getTestimonialTextFr());
    assertNull(newTestimonial.getTestimonialTextEs());
    assertNull(newTestimonial.getRating());
    assertNull(newTestimonial.getClientImageUrl());
    assertNull(newTestimonial.getStatus());
    assertNull(newTestimonial.getCreatedAt());
    assertNull(newTestimonial.getUpdatedAt());
  }

  @Test
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    Testimonial testTestimonial =
        new Testimonial(
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

    assertEquals(1L, testTestimonial.getId());
    assertEquals("Jane Smith", testTestimonial.getClientName());
    assertEquals("Manager", testTestimonial.getClientPosition());
    assertEquals("Company Inc", testTestimonial.getClientCompany());
    assertEquals("Wonderful service", testTestimonial.getTestimonialTextEn());
    assertEquals("Service merveilleux", testTestimonial.getTestimonialTextFr());
    assertEquals("Servicio maravilloso", testTestimonial.getTestimonialTextEs());
    assertEquals(4, testTestimonial.getRating());
    assertEquals("https://example.com/jane.jpg", testTestimonial.getClientImageUrl());
    assertEquals(TestimonialStatus.APPROVED, testTestimonial.getStatus());
    assertEquals(now, testTestimonial.getCreatedAt());
    assertEquals(now, testTestimonial.getUpdatedAt());
  }

  @Test
  void testBuilder() {
    Testimonial builtTestimonial =
        Testimonial.builder()
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

    assertEquals(2L, builtTestimonial.getId());
    assertEquals("Alice Johnson", builtTestimonial.getClientName());
    assertEquals("Designer", builtTestimonial.getClientPosition());
    assertEquals("Design Studio", builtTestimonial.getClientCompany());
    assertEquals("Perfect design", builtTestimonial.getTestimonialTextEn());
    assertEquals("Design parfait", builtTestimonial.getTestimonialTextFr());
    assertEquals("Diseño perfecto", builtTestimonial.getTestimonialTextEs());
    assertEquals(5, builtTestimonial.getRating());
    assertEquals("https://example.com/alice.jpg", builtTestimonial.getClientImageUrl());
    assertEquals(TestimonialStatus.APPROVED, builtTestimonial.getStatus());
  }

  @Test
  void testGettersAndSetters() {
    Testimonial testTestimonial = new Testimonial();
    testTestimonial.setId(3L);
    testTestimonial.setClientName("Bob Wilson");
    testTestimonial.setClientPosition("Developer");
    testTestimonial.setClientCompany("Tech Solutions");
    testTestimonial.setTestimonialTextEn("Amazing project");
    testTestimonial.setTestimonialTextFr("Projet incroyable");
    testTestimonial.setTestimonialTextEs("Proyecto asombroso");
    testTestimonial.setRating(5);
    testTestimonial.setClientImageUrl("https://example.com/bob.jpg");
    testTestimonial.setStatus(TestimonialStatus.APPROVED);
    testTestimonial.setCreatedAt(LocalDateTime.now());
    testTestimonial.setUpdatedAt(LocalDateTime.now());

    assertEquals(3L, testTestimonial.getId());
    assertEquals("Bob Wilson", testTestimonial.getClientName());
    assertEquals("Developer", testTestimonial.getClientPosition());
    assertEquals("Tech Solutions", testTestimonial.getClientCompany());
    assertEquals("Amazing project", testTestimonial.getTestimonialTextEn());
    assertEquals("Projet incroyable", testTestimonial.getTestimonialTextFr());
    assertEquals("Proyecto asombroso", testTestimonial.getTestimonialTextEs());
    assertEquals(5, testTestimonial.getRating());
    assertEquals("https://example.com/bob.jpg", testTestimonial.getClientImageUrl());
    assertEquals(TestimonialStatus.APPROVED, testTestimonial.getStatus());
    assertNotNull(testTestimonial.getCreatedAt());
    assertNotNull(testTestimonial.getUpdatedAt());
  }

  @Test
  void testOnCreate() {
    Testimonial newTestimonial = new Testimonial();
    newTestimonial.setClientName("Test User");
    newTestimonial.onCreate();

    assertNotNull(newTestimonial.getCreatedAt());
    assertNotNull(newTestimonial.getUpdatedAt());
    assertEquals(TestimonialStatus.PENDING, newTestimonial.getStatus());
  }

  @Test
  void testOnCreateWithStatusSet() {
    Testimonial newTestimonial = new Testimonial();
    newTestimonial.setStatus(TestimonialStatus.APPROVED);
    newTestimonial.onCreate();

    assertEquals(TestimonialStatus.APPROVED, newTestimonial.getStatus());
  }

  @Test
  void testOnUpdate() {
    LocalDateTime originalTime = LocalDateTime.now().minusHours(1);
    testimonial.setUpdatedAt(originalTime);
    testimonial.onUpdate();

    assertNotEquals(originalTime, testimonial.getUpdatedAt());
    assertTrue(testimonial.getUpdatedAt().isAfter(originalTime));
  }

  @Test
  void testEqualsHashCodeToString() {
    Testimonial another =
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
            .createdAt(testimonial.getCreatedAt())
            .updatedAt(testimonial.getUpdatedAt())
            .build();

    assertEquals(testimonial, another);
    assertEquals(testimonial.hashCode(), another.hashCode());
    assertNotNull(testimonial.toString());
    assertTrue(testimonial.toString().contains("John Doe"));
  }

  @Test
  void testEqualsWithFieldDifferences() {
    Testimonial different =
        Testimonial.builder()
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

    assertNotEquals(testimonial, different);
  }

  @Test
  void testEqualsWithNullClientImageUrl() {
    Testimonial testTestimonial1 =
        Testimonial.builder()
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

    Testimonial testTestimonial2 =
        Testimonial.builder()
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

    assertEquals(testTestimonial1, testTestimonial2);
  }

  @ParameterizedTest
  @MethodSource("provideTestimonialStatusVariations")
  void testEqualsWithDifferentStatus(TestimonialStatus status) {
    LocalDateTime now = LocalDateTime.now();
    Testimonial testTestimonial =
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
            .status(status)
            .createdAt(now)
            .updatedAt(now)
            .build();

    Testimonial baseTestimonial =
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

    if (!status.equals(TestimonialStatus.APPROVED)) {
      assertNotEquals(baseTestimonial, testTestimonial);
    } else {
      assertEquals(baseTestimonial, testTestimonial);
    }
  }

  @ParameterizedTest
  @MethodSource("provideRatingValues")
  void testEqualsWithDifferentRating(int rating) {
    LocalDateTime now = LocalDateTime.now();
    Testimonial testTestimonial =
        Testimonial.builder()
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

    Testimonial baseTestimonial =
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

    if (rating != 5) {
      assertNotEquals(baseTestimonial, testTestimonial);
    } else {
      assertEquals(baseTestimonial, testTestimonial);
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
