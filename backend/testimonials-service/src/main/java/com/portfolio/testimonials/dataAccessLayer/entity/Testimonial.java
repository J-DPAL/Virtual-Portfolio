package com.portfolio.testimonials.dataAccessLayer.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "testimonials")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Testimonial {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String clientName;

  @Column(nullable = false)
  private String clientPosition;

  @Column(nullable = false)
  private String clientCompany;

  @Column(nullable = false, length = 2000)
  private String testimonialTextEn;

  @Column(nullable = false, length = 2000)
  private String testimonialTextFr;

  @Column(nullable = false, length = 2000)
  private String testimonialTextEs;

  @Column(nullable = false)
  private Integer rating;

  private String clientImageUrl;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TestimonialStatus status;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
    if (status == null) {
      status = TestimonialStatus.PENDING;
    }
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public enum TestimonialStatus {
    PENDING,
    APPROVED,
    REJECTED
  }
}
