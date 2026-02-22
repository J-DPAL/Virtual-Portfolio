package com.portfolio.monolith.dto;

public class FileUploadResponse {
  public String message;
  public String path;

  public FileUploadResponse() {}

  public FileUploadResponse(String message, String path) {
    this.message = message;
    this.path = path;
  }
}
