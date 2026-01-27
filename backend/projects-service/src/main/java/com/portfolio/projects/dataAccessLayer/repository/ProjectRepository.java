package com.portfolio.projects.dataAccessLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.projects.dataAccessLayer.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
  List<Project> findByStatus(String status);

  List<Project> findByOrderByStartDateDesc();
}
