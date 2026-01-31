package com.portfolio.skills.dataAccessLayer.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nameEn;

  @Column(nullable = false)
  private String nameFr;

  @Column(nullable = false)
  private String nameEs;

  @Column(length = 1000)
  private String descriptionEn;

  @Column(length = 1000)
  private String descriptionFr;

  @Column(length = 1000)
  private String descriptionEs;

  @Column(nullable = false)
  private String proficiencyLevel; // Beginner, Intermediate, Advanced, Expert

  @Column(nullable = false)
  private String category; // Frontend, Backend, Database, DevOps, etc.

  private Integer yearsOfExperience;

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
