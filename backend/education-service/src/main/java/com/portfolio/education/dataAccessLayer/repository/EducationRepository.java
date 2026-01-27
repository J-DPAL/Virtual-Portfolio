package com.portfolio.education.dataAccessLayer.repository;

import com.portfolio.education.dataAccessLayer.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByIsCurrent(Boolean isCurrent);
    List<Education> findByOrderByStartDateDesc();
}
