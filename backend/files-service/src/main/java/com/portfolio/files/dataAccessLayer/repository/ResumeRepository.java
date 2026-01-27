package com.portfolio.files.dataAccessLayer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.files.dataAccessLayer.entity.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
  Optional<Resume> findTopByOrderByUploadedAtDesc();
}
