package com.portfolio.files.presentationLayer.controller;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.files.businessLayer.service.FileStorageService;
import com.portfolio.files.dataAccessLayer.entity.Resume;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("ResumeController Integration Tests")
class ResumeControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private FileStorageService fileStorageService;

  @Test
  @DisplayName("Should upload resume via POST /api/resume/upload for admin")
  @WithMockUser(roles = "ADMIN")
  void testUploadResume_WithValidFile_ReturnsSuccessResponse() throws Exception {
    // Arrange: Setup mock
    Resume resume =
        new Resume(1L, "resume.pdf", "resume.pdf", LocalDateTime.now(), 10L, "application/pdf");
    when(fileStorageService.storeFile(any())).thenReturn(resume);

    MockMultipartFile file =
        new MockMultipartFile("file", "resume.pdf", "application/pdf", "data".getBytes());

    // Act & Assert: Perform multipart upload and verify response
    mockMvc
        .perform(multipart("/api/resume/upload").file(file))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.fileName").value("resume.pdf"));

    verify(fileStorageService, times(1)).storeFile(any());
  }

  @Test
  @DisplayName("Should return 403 when non-admin uploads resume")
  @WithMockUser(roles = "USER")
  void testUploadResume_WithUserRole_ReturnsForbidden() throws Exception {
    // Arrange: Create mock file
    MockMultipartFile file =
        new MockMultipartFile("file", "resume.pdf", "application/pdf", "data".getBytes());

    // Act & Assert: Perform multipart upload
    mockMvc.perform(multipart("/api/resume/upload").file(file)).andExpect(status().isForbidden());

    verify(fileStorageService, never()).storeFile(any());
  }

  @Test
  @DisplayName("Should download resume via GET /api/resume/download")
  void testDownloadResume_ReturnsPdfFile() throws Exception {
    // Arrange: Setup mock
    Resource resource = new ByteArrayResource("pdf".getBytes(StandardCharsets.UTF_8));
    when(fileStorageService.loadCvAsResource("CV_JD_EN.pdf")).thenReturn(resource);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/api/resume/download?language=en"))
        .andExpect(status().isOk())
        .andExpect(header().string("Content-Disposition", "attachment; filename=\"CV_JD_EN.pdf\""))
        .andExpect(content().contentType(MediaType.APPLICATION_PDF));

    verify(fileStorageService, times(1)).loadCvAsResource("CV_JD_EN.pdf");
  }

  @Test
  @DisplayName("Should return current resume metadata via GET /api/resume/current")
  void testGetCurrentResume_ReturnsMetadata() throws Exception {
    // Arrange: Setup mock
    Resume resume =
        new Resume(1L, "resume.pdf", "resume.pdf", LocalDateTime.now(), 10L, "application/pdf");
    when(fileStorageService.getCurrentResume()).thenReturn(resume);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/api/resume/current"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.fileName").value("resume.pdf"))
        .andExpect(jsonPath("$.fileSize").value(10));

    verify(fileStorageService, times(1)).getCurrentResume();
  }
}
