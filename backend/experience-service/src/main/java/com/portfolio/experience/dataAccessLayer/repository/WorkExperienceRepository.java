package com.portfolio.experience.dataAccessLayer.repository;

import com.portfolio.experience.dataAccessLayer.entity.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    List<WorkExperience> findByIsCurrent(Boolean isCurrent);
    List<WorkExperience> findByOrderByStartDateDesc();
}
