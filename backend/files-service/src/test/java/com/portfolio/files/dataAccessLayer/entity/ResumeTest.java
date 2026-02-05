package com.portfolio.files.dataAccessLayer.entity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Resume Entity Tests")
class ResumeTest {

  @Test
  @DisplayName("Should create Resume with no-args constructor")
  void testNoArgsConstructor() {
    Resume resume = new Resume();
    assertNotNull(resume);
  }

  @Test
  @DisplayName("Should create Resume with all-args constructor")
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    Resume resume = new Resume(1L, "resume.pdf", "/path/resume.pdf", now, 100L, "application/pdf");

    assertEquals(1L, resume.getId());
    assertEquals("resume.pdf", resume.getFileName());
    assertEquals("/path/resume.pdf", resume.getFilePath());
    assertEquals(now, resume.getUploadedAt());
    assertEquals(100L, resume.getFileSize());
    assertEquals("application/pdf", resume.getContentType());
  }

  @Test
  @DisplayName("Should set and get fields correctly")
  void testSettersAndGetters() {
    Resume resume = new Resume();
    LocalDateTime now = LocalDateTime.now();

    resume.setId(2L);
    resume.setFileName("file.docx");
    resume.setFilePath("/tmp/file.docx");
    resume.setUploadedAt(now);
    resume.setFileSize(250L);
    resume.setContentType(
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    assertEquals(2L, resume.getId());
    assertEquals("file.docx", resume.getFileName());
    assertEquals("/tmp/file.docx", resume.getFilePath());
    assertEquals(now, resume.getUploadedAt());
    assertEquals(250L, resume.getFileSize());
    assertEquals(
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        resume.getContentType());
  }

  @Test
  @DisplayName("Should set uploadedAt on create")
  void testOnCreateSetsUploadedAt() {
    Resume resume = new Resume();
    LocalDateTime before = LocalDateTime.now();

    resume.onCreate();

    assertNotNull(resume.getUploadedAt());
    assertFalse(resume.getUploadedAt().isBefore(before));
  }

  @Test
  @DisplayName("Should support equals and hashCode")
  void testEqualsAndHashCode() {
    LocalDateTime now = LocalDateTime.now();
    Resume resume1 = new Resume(1L, "resume.pdf", "/path/resume.pdf", now, 100L, "application/pdf");
    Resume resume2 = new Resume(1L, "resume.pdf", "/path/resume.pdf", now, 100L, "application/pdf");

    assertEquals(resume1, resume2);
    assertEquals(resume1.hashCode(), resume2.hashCode());
  }

  @Test
  @DisplayName("Should have non-empty toString")
  void testToStringNotEmpty() {
    Resume resume = new Resume();
    String text = resume.toString();

    assertNotNull(text);
    assertFalse(text.isBlank());
  }
}
