package com.portfolio.education.dataAccessLayer.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "education")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String institutionNameEn;

  @Column(nullable = false)
  private String institutionNameFr;

  @Column(nullable = false)
  private String degreeEn;

  @Column(nullable = false)
  private String degreeFr;

  @Column(nullable = false)
  private String fieldOfStudyEn;

  @Column(nullable = false)
  private String fieldOfStudyFr;

  @Column(length = 2000)
  private String descriptionEn;

  @Column(length = 2000)
  private String descriptionFr;

  @Column(nullable = false)
  private LocalDate startDate;

  private LocalDate endDate;

  @Column(nullable = false)
  private Boolean isCurrent;

  @Column(precision = 3, scale = 2)
  private BigDecimal gpa;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
    if (isCurrent == null) {
      isCurrent = false;
    }
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
