package com.portfolio.education.dataAccessLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.education.dataAccessLayer.entity.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
  List<Education> findByIsCurrent(Boolean isCurrent);

  List<Education> findByOrderByStartDateDesc();
}
