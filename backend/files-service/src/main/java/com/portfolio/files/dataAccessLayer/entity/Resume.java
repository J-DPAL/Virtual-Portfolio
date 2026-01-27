package com.portfolio.files.dataAccessLayer.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resumes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resume {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String fileName;

  @Column(nullable = false)
  private String filePath;

  @Column(nullable = false)
  private LocalDateTime uploadedAt;

  @Column(nullable = false)
  private Long fileSize;

  @Column(nullable = false)
  private String contentType;

  @PrePersist
  protected void onCreate() {
    uploadedAt = LocalDateTime.now();
  }
}
