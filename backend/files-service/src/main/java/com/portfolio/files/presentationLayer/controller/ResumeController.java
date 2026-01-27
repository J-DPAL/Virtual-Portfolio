package com.portfolio.files.presentationLayer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.files.businessLayer.service.FileStorageService;
import com.portfolio.files.dataAccessLayer.entity.Resume;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "*")
public class ResumeController {

  private final FileStorageService fileStorageService;

  public ResumeController(FileStorageService fileStorageService) {
    this.fileStorageService = fileStorageService;
  }

  @PostMapping("/upload")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Map<String, Object>> uploadResume(
      @RequestParam("file") MultipartFile file) {
    Resume resume = fileStorageService.storeFile(file);

    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", "File uploaded successfully");
    response.put("fileName", resume.getFileName());
    response.put("fileSize", resume.getFileSize());
    response.put("uploadedAt", resume.getUploadedAt());

    return ResponseEntity.ok(response);
  }

  @GetMapping("/download")
  public ResponseEntity<Resource> downloadResume(HttpServletRequest request) {
    // Get current resume
    Resume resume = fileStorageService.getCurrentResume();

    // Load file as Resource
    Resource resource = fileStorageService.loadFileAsResource(resume.getFilePath());

    // Try to determine file's content type
    String contentType = resume.getContentType();
    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resume.getFileName() + "\"")
        .body(resource);
  }

  @GetMapping("/current")
  public ResponseEntity<Map<String, Object>> getCurrentResume() {
    Resume resume = fileStorageService.getCurrentResume();

    Map<String, Object> response = new HashMap<>();
    response.put("id", resume.getId());
    response.put("fileName", resume.getFileName());
    response.put("fileSize", resume.getFileSize());
    response.put("contentType", resume.getContentType());
    response.put("uploadedAt", resume.getUploadedAt());

    return ResponseEntity.ok(response);
  }
}
