package com.portfolio.education.mappingLayer.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.portfolio.education.dataAccessLayer.entity.Education;
import com.portfolio.education.mappingLayer.dto.EducationDTO;

@Component
public class EducationMapper {

  private final ModelMapper modelMapper;

  public EducationMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public EducationDTO toDTO(Education education) {
    return modelMapper.map(education, EducationDTO.class);
  }

  public Education toEntity(EducationDTO educationDTO) {
    return modelMapper.map(educationDTO, Education.class);
  }
}
