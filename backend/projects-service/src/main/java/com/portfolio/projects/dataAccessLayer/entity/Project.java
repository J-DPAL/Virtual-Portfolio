package com.portfolio.projects.dataAccessLayer.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String titleEn;

  @Column(nullable = false)
  private String titleFr;

  @Column(nullable = false)
  private String titleEs;

  @Column(length = 2000)
  private String descriptionEn;

  @Column(length = 2000)
  private String descriptionFr;

  @Column(length = 2000)
  private String descriptionEs;

  @Column(length = 1000)
  private String technologies; // Comma-separated or JSON string

  private String projectUrl;

  private String githubUrl;

  private String imageUrl;

  private LocalDate startDate;

  private LocalDate endDate;

  @Column(nullable = false)
  private String status; // Active, Completed, In Progress, Archived

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
