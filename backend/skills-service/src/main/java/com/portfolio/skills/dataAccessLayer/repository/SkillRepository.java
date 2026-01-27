package com.portfolio.skills.dataAccessLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.skills.dataAccessLayer.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
  List<Skill> findByCategory(String category);

  List<Skill> findByProficiencyLevel(String proficiencyLevel);

  List<Skill> findByOrderByCreatedAtDesc();
}
