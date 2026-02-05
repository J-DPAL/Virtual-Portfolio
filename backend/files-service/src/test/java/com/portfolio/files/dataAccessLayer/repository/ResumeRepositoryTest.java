package com.portfolio.files.dataAccessLayer.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.files.dataAccessLayer.entity.Resume;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("ResumeRepository Integration Tests")
class ResumeRepositoryTest {

  @Autowired private ResumeRepository resumeRepository;

  @BeforeEach
  void setUp() {
    // Arrange: Clear repository
    resumeRepository.deleteAll();
  }

  @Test
  @DisplayName("Should save a new resume successfully")
  void testSaveResume_WithValidResume_ReturnsSavedResume() {
    // Arrange: Create resume
    Resume resume =
        new Resume(null, "resume.pdf", "resume.pdf", LocalDateTime.now(), 10L, "application/pdf");

    // Act: Save resume
    Resume saved = resumeRepository.save(resume);

    // Assert: Verify saved
    assertNotNull(saved.getId());
    assertEquals("resume.pdf", saved.getFileName());
  }

  @Test
  @DisplayName("Should find latest resume by uploadedAt")
  void testFindTopByOrderByUploadedAtDesc_ReturnsLatestResume() throws InterruptedException {
    // Arrange: Save two resumes
    Resume first =
        new Resume(
            null,
            "first.pdf",
            "first.pdf",
            LocalDateTime.now().minusDays(1),
            10L,
            "application/pdf");
    Resume second =
        new Resume(null, "second.pdf", "second.pdf", LocalDateTime.now(), 12L, "application/pdf");
    resumeRepository.save(first);
    resumeRepository.save(second);

    // Act: Find latest resume
    Optional<Resume> latest = resumeRepository.findTopByOrderByUploadedAtDesc();

    // Assert: Verify latest
    assertTrue(latest.isPresent());
    assertEquals("second.pdf", latest.get().getFileName());
  }
}
