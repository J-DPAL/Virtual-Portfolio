package com.portfolio.skills.mappingLayer.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.portfolio.skills.dataAccessLayer.entity.Skill;
import com.portfolio.skills.mappingLayer.dto.SkillDTO;

@Component
public class SkillMapper {

  private final ModelMapper modelMapper;

  public SkillMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public SkillDTO toDTO(Skill skill) {
    return modelMapper.map(skill, SkillDTO.class);
  }

  public Skill toEntity(SkillDTO skillDTO) {
    return modelMapper.map(skillDTO, Skill.class);
  }
}
