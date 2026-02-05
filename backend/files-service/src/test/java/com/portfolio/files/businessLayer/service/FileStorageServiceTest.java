package com.portfolio.files.businessLayer.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import com.portfolio.files.dataAccessLayer.entity.Resume;
import com.portfolio.files.dataAccessLayer.repository.ResumeRepository;
import com.portfolio.files.utils.exceptions.FileStorageException;
import com.portfolio.files.utils.exceptions.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("FileStorageService Unit Tests")
class FileStorageServiceTest {

  @TempDir Path tempDir;

  @Mock private ResumeRepository resumeRepository;

  private FileStorageService fileStorageService;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize service with temp directories
    MockitoAnnotations.openMocks(this);
    fileStorageService =
        new FileStorageService(tempDir.toString(), tempDir.toString(), resumeRepository);
  }

  @Test
  @DisplayName("Should store file successfully")
  void testStoreFile_WithValidFile_StoresFile() throws IOException {
    // Arrange: Create mock multipart file
    MockMultipartFile file =
        new MockMultipartFile("file", "resume.pdf", "application/pdf", "data".getBytes());

    ArgumentCaptor<Resume> resumeCaptor = ArgumentCaptor.forClass(Resume.class);
    when(resumeRepository.save(any(Resume.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // Act: Store file
    Resume savedResume = fileStorageService.storeFile(file);

    // Assert: Verify file was saved
    verify(resumeRepository, times(1)).save(resumeCaptor.capture());
    Resume captured = resumeCaptor.getValue();
    assertEquals("resume.pdf", captured.getFileName());
    assertEquals("application/pdf", captured.getContentType());
    assertEquals(file.getSize(), captured.getFileSize());
    assertNotNull(savedResume.getUploadedAt());

    Path storedPath = tempDir.resolve(captured.getFilePath());
    assertTrue(Files.exists(storedPath));
  }

  @Test
  @DisplayName("Should throw exception for invalid file name")
  void testStoreFile_WithInvalidFileName_ThrowsException() {
    // Arrange: Create file with invalid name
    MockMultipartFile file =
        new MockMultipartFile("file", "../evil.pdf", "application/pdf", "data".getBytes());

    // Act & Assert: Verify exception
    assertThrows(FileStorageException.class, () -> fileStorageService.storeFile(file));
  }

  @Test
  @DisplayName("Should load file as resource when it exists")
  void testLoadFileAsResource_WithExistingFile_ReturnsResource() throws IOException {
    // Arrange: Create a file on disk
    Path filePath = tempDir.resolve("test.txt");
    Files.writeString(filePath, "content");

    // Act: Load file
    Resource resource = fileStorageService.loadFileAsResource("test.txt");

    // Assert: Verify resource
    assertNotNull(resource);
    assertTrue(resource.exists());
  }

  @Test
  @DisplayName("Should throw exception when file does not exist")
  void testLoadFileAsResource_WithMissingFile_ThrowsException() {
    // Act & Assert: Verify exception
    assertThrows(
        ResourceNotFoundException.class,
        () -> fileStorageService.loadFileAsResource("missing.txt"));
  }

  @Test
  @DisplayName("Should load CV as resource when it exists")
  void testLoadCvAsResource_WithExistingFile_ReturnsResource() throws IOException {
    // Arrange: Create a file on disk
    Path filePath = tempDir.resolve("CV_JD_EN.pdf");
    Files.writeString(filePath, "content");

    // Act: Load CV file
    Resource resource = fileStorageService.loadCvAsResource("CV_JD_EN.pdf");

    // Assert: Verify resource
    assertNotNull(resource);
    assertTrue(resource.exists());
  }

  @Test
  @DisplayName("Should throw exception when CV file does not exist")
  void testLoadCvAsResource_WithMissingFile_ThrowsException() {
    // Act & Assert: Verify exception
    assertThrows(
        ResourceNotFoundException.class, () -> fileStorageService.loadCvAsResource("missing.pdf"));
  }

  @Test
  @DisplayName("Should return latest resume")
  void testGetCurrentResume_WithExistingResume_ReturnsResume() {
    // Arrange: Setup mock
    Resume resume =
        new Resume(1L, "resume.pdf", "resume.pdf", LocalDateTime.now(), 10L, "application/pdf");
    when(resumeRepository.findTopByOrderByUploadedAtDesc()).thenReturn(Optional.of(resume));

    // Act: Get current resume
    Resume result = fileStorageService.getCurrentResume();

    // Assert: Verify result
    assertNotNull(result);
    assertEquals("resume.pdf", result.getFileName());
  }

  @Test
  @DisplayName("Should throw exception when no resume exists")
  void testGetCurrentResume_WithNoResume_ThrowsException() {
    // Arrange: Setup mock
    when(resumeRepository.findTopByOrderByUploadedAtDesc()).thenReturn(Optional.empty());

    // Act & Assert: Verify exception
    assertThrows(ResourceNotFoundException.class, () -> fileStorageService.getCurrentResume());
  }
}
