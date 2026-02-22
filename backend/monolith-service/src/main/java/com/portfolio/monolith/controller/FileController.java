package com.portfolio.monolith.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.monolith.dto.FileUploadResponse;
import com.portfolio.monolith.service.SupabaseStorageService;

@RestController
@RequestMapping("/v1/files")
@Validated
public class FileController {

  private final SupabaseStorageService storageService;

  public FileController(SupabaseStorageService storageService) {
    this.storageService = storageService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping(value = "/resume/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<FileUploadResponse> uploadResume(
      @RequestParam("file") MultipartFile file,
      @RequestParam(value = "language", defaultValue = "en") String language) {
    String path = storageService.uploadResume(file, language);
    return new ResponseEntity<>(new FileUploadResponse("Upload successful", path), HttpStatus.OK);
  }

  @GetMapping("/resume/download")
  public ResponseEntity<byte[]> downloadResume(
      @RequestParam(value = "language", defaultValue = "en") String language) {
    SupabaseStorageService.DownloadResult result = storageService.downloadResume(language);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(
        ContentDisposition.attachment().filename(result.fileName()).build());

    return new ResponseEntity<>(result.bytes(), headers, HttpStatus.OK);
  }

  @GetMapping("/resume/current")
  public ResponseEntity<Map<String, Object>> getCurrentResume(
      @RequestParam(value = "language", defaultValue = "en") String language) {
    SupabaseStorageService.ResumeMetadata metadata = storageService.getCurrentResume(language);

    Map<String, Object> response = new HashMap<>();
    response.put("id", null);
    response.put("fileName", metadata.fileName());
    response.put("filePath", metadata.path());
    response.put("fileSize", metadata.fileSize());
    response.put("contentType", metadata.contentType());
    response.put("uploadedAt", null);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Files service is running");
  }
}
