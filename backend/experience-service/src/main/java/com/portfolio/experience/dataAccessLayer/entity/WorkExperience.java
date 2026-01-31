package com.portfolio.experience.dataAccessLayer.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "work_experience")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkExperience {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String companyNameEn;

  @Column(nullable = false)
  private String companyNameFr;

  @Column(nullable = false)
  private String companyNameEs;

  @Column(nullable = false)
  private String positionEn;

  @Column(nullable = false)
  private String positionFr;

  @Column(nullable = false)
  private String positionEs;

  @Column(length = 2000)
  private String descriptionEn;

  @Column(length = 2000)
  private String descriptionFr;

  @Column(length = 2000)
  private String descriptionEs;

  private String locationEn;

  private String locationFr;

  private String locationEs;

  @Column(nullable = false)
  private LocalDate startDate;

  private LocalDate endDate;

  @Column(nullable = false)
  private Boolean isCurrent;

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
