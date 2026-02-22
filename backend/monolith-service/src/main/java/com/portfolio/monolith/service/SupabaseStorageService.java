package com.portfolio.monolith.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.monolith.exception.ApiException;
import com.portfolio.monolith.exception.BadRequestException;
import com.portfolio.monolith.exception.NotFoundException;

@Service
public class SupabaseStorageService {

  private final RestTemplate restTemplate;
  private final String supabaseUrl;
  private final String serviceRoleKey;
  private final String bucket;
  private final String fileEn;
  private final String fileFr;
  private final String fileEs;

  public SupabaseStorageService(
      RestTemplate restTemplate,
      @Value("${supabase.url}") String supabaseUrl,
      @Value("${supabase.service-role-key:}") String serviceRoleKey,
      @Value("${app.resume.bucket:resumes}") String bucket,
      @Value("${app.resume.file-en:resume_en.pdf}") String fileEn,
      @Value("${app.resume.file-fr:resume_fr.pdf}") String fileFr,
      @Value("${app.resume.file-es:resume_es.pdf}") String fileEs) {
    this.restTemplate = restTemplate;
    this.supabaseUrl = trimTrailingSlash(supabaseUrl);
    this.serviceRoleKey = serviceRoleKey;
    this.bucket = bucket;
    this.fileEn = fileEn;
    this.fileFr = fileFr;
    this.fileEs = fileEs;
  }

  public String uploadResume(MultipartFile file, String language) {
    ensureConfiguredForUpload();

    if (file == null || file.isEmpty()) {
      throw new BadRequestException("Please select a file");
    }

    String contentType = file.getContentType();
    if (contentType == null || !contentType.toLowerCase().contains("pdf")) {
      throw new BadRequestException("Only PDF files are allowed");
    }

    String lang = resolveLanguage(language);
    String path = configuredPath(lang);

    byte[] payload;
    try {
      payload = file.getBytes();
    } catch (IOException ex) {
      throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read uploaded file");
    }

    String url =
        supabaseUrl
            + "/storage/v1/object/"
            + encodePathSegment(bucket)
            + "/"
            + encodePath(path);

    HttpHeaders headers = new HttpHeaders();
    headers.set("apikey", serviceRoleKey);
    headers.setBearerAuth(serviceRoleKey);
    headers.set("x-upsert", "true");
    headers.setContentType(MediaType.APPLICATION_PDF);

    try {
      ResponseEntity<String> response =
          restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(payload, headers), String.class);
      if (!response.getStatusCode().is2xxSuccessful()) {
        throw new ApiException(HttpStatus.BAD_GATEWAY, "Failed to upload resume");
      }
      return path;
    } catch (HttpStatusCodeException ex) {
      throw new ApiException(HttpStatus.BAD_GATEWAY, "Failed to upload resume");
    }
  }

  public DownloadResult downloadResume(String language) {
    if (isBlank(supabaseUrl)) {
      throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Supabase URL is not configured");
    }

    String lang = resolveLanguage(language);

    for (String path : candidatePaths(lang)) {
      String url =
          supabaseUrl
              + "/storage/v1/object/public/"
              + encodePathSegment(bucket)
              + "/"
              + encodePath(path);

      try {
        ResponseEntity<byte[]> response =
            restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, byte[].class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
          return new DownloadResult(response.getBody(), fileName(path));
        }
      } catch (HttpStatusCodeException ex) {
        // Try next path.
      }
    }

    throw new NotFoundException("Resume file not found");
  }

  public ResumeMetadata getCurrentResume(String language) {
    if (isBlank(supabaseUrl)) {
      throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Supabase URL is not configured");
    }

    String lang = resolveLanguage(language);

    for (String path : candidatePaths(lang)) {
      String url =
          supabaseUrl
              + "/storage/v1/object/public/"
              + encodePathSegment(bucket)
              + "/"
              + encodePath(path);

      try {
        ResponseEntity<byte[]> response =
            restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, byte[].class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
          MediaType mediaType = response.getHeaders().getContentType();
          String contentType = mediaType != null ? mediaType.toString() : MediaType.APPLICATION_PDF_VALUE;
          return new ResumeMetadata(fileName(path), path, contentType, response.getBody().length);
        }
      } catch (HttpStatusCodeException ex) {
        // Try next path.
      }
    }

    throw new NotFoundException("Resume file not found");
  }

  private List<String> candidatePaths(String language) {
    Set<String> paths = new LinkedHashSet<>();
    paths.add(configuredPath(language));
    paths.add("resume_" + language + ".pdf");
    paths.add("CV_JD_" + language.toUpperCase() + ".pdf");
    return new ArrayList<>(paths);
  }

  private String configuredPath(String language) {
    return switch (language) {
      case "fr" -> fileFr;
      case "es" -> fileEs;
      default -> fileEn;
    };
  }

  private String resolveLanguage(String language) {
    if (language == null || language.isBlank()) {
      return "en";
    }
    String lang = language.trim().toLowerCase();
    if ("en".equals(lang) || "fr".equals(lang) || "es".equals(lang)) {
      return lang;
    }
    throw new BadRequestException("Unsupported language");
  }

  private void ensureConfiguredForUpload() {
    if (isBlank(supabaseUrl)) {
      throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Supabase URL is not configured");
    }
    if (isBlank(serviceRoleKey)) {
      throw new ApiException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Supabase service role key is not configured for file upload");
    }
  }

  private static String fileName(String path) {
    int index = path.lastIndexOf('/');
    return index >= 0 ? path.substring(index + 1) : path;
  }

  private static String encodePath(String path) {
    String[] segments = path.split("/");
    List<String> encoded = new ArrayList<>();
    for (String segment : segments) {
      encoded.add(encodePathSegment(segment));
    }
    return String.join("/", encoded);
  }

  private static String encodePathSegment(String segment) {
    return URLEncoder.encode(segment, StandardCharsets.UTF_8).replace("+", "%20");
  }

  private static boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }

  private static String trimTrailingSlash(String value) {
    if (value == null) {
      return null;
    }
    String result = value.trim();
    while (result.endsWith("/")) {
      result = result.substring(0, result.length() - 1);
    }
    return result;
  }

  public record ResumeMetadata(String fileName, String path, String contentType, long fileSize) {}

  public record DownloadResult(byte[] bytes, String fileName) {}
}
