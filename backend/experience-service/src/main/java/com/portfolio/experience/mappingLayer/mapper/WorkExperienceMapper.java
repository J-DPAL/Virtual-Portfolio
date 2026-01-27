package com.portfolio.experience.mappingLayer.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.portfolio.experience.dataAccessLayer.entity.WorkExperience;
import com.portfolio.experience.mappingLayer.dto.WorkExperienceDTO;

@Component
public class WorkExperienceMapper {

  private final ModelMapper modelMapper;

  public WorkExperienceMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public WorkExperienceDTO toDTO(WorkExperience workExperience) {
    return modelMapper.map(workExperience, WorkExperienceDTO.class);
  }

  public WorkExperience toEntity(WorkExperienceDTO workExperienceDTO) {
    return modelMapper.map(workExperienceDTO, WorkExperience.class);
  }
}
