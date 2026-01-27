package com.portfolio.experience.dataAccessLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.experience.dataAccessLayer.entity.WorkExperience;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
  List<WorkExperience> findByIsCurrent(Boolean isCurrent);

  List<WorkExperience> findByOrderByStartDateDesc();
}
