package com.portfolio.projects.businessLayer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.projects.dataAccessLayer.entity.Project;
import com.portfolio.projects.dataAccessLayer.repository.ProjectRepository;
import com.portfolio.projects.mappingLayer.dto.ProjectDTO;
import com.portfolio.projects.mappingLayer.mapper.ProjectMapper;
import com.portfolio.projects.utils.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;

  public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
    this.projectRepository = projectRepository;
    this.projectMapper = projectMapper;
  }

  public List<ProjectDTO> getAllProjects() {
    return projectRepository.findAll().stream()
        .map(projectMapper::toDTO)
        .collect(Collectors.toList());
  }

  @SuppressWarnings("null")
  public ProjectDTO getProjectById(Long id) {
    Project project =
        projectRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    return projectMapper.toDTO(project);
  }

  public List<ProjectDTO> getProjectsByStatus(String status) {
    return projectRepository.findByStatus(status).stream()
        .map(projectMapper::toDTO)
        .collect(Collectors.toList());
  }

  @SuppressWarnings("null")
  public ProjectDTO createProject(ProjectDTO projectDTO) {
    Project project = projectMapper.toEntity(projectDTO);
    Project savedProject = projectRepository.save(project);
    return projectMapper.toDTO(savedProject);
  }

  @SuppressWarnings("null")
  public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
    Project existingProject =
        projectRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

    existingProject.setTitleEn(projectDTO.getTitleEn());
    existingProject.setTitleAr(projectDTO.getTitleAr());
    existingProject.setDescriptionEn(projectDTO.getDescriptionEn());
    existingProject.setDescriptionAr(projectDTO.getDescriptionAr());
    existingProject.setTechnologies(projectDTO.getTechnologies());
    existingProject.setProjectUrl(projectDTO.getProjectUrl());
    existingProject.setGithubUrl(projectDTO.getGithubUrl());
    existingProject.setImageUrl(projectDTO.getImageUrl());
    existingProject.setStartDate(projectDTO.getStartDate());
    existingProject.setEndDate(projectDTO.getEndDate());
    existingProject.setStatus(projectDTO.getStatus());

    Project updatedProject = projectRepository.save(existingProject);
    return projectMapper.toDTO(updatedProject);
  }

  @SuppressWarnings("null")
  public void deleteProject(Long id) {
    if (!projectRepository.existsById(id)) {
      throw new ResourceNotFoundException("Project not found with id: " + id);
    }
    projectRepository.deleteById(id);
  }
}
