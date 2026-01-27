package com.portfolio.files.dataAccessLayer.repository;

import com.portfolio.files.dataAccessLayer.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findTopByOrderByUploadedAtDesc();
}
