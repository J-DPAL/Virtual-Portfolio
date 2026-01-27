package com.portfolio.projects.mappingLayer.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.portfolio.projects.dataAccessLayer.entity.Project;
import com.portfolio.projects.mappingLayer.dto.ProjectDTO;

@Component
public class ProjectMapper {

  private final ModelMapper modelMapper;

  public ProjectMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public ProjectDTO toDTO(Project project) {
    return modelMapper.map(project, ProjectDTO.class);
  }

  public Project toEntity(ProjectDTO projectDTO) {
    return modelMapper.map(projectDTO, Project.class);
  }
}
