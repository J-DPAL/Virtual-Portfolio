package com.portfolio.hobbies.mappingLayer.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.portfolio.hobbies.dataAccessLayer.entity.Hobby;
import com.portfolio.hobbies.mappingLayer.dto.HobbyDTO;

@Component
public class HobbyMapper {

  private final ModelMapper modelMapper;

  public HobbyMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public HobbyDTO toDTO(Hobby hobby) {
    return modelMapper.map(hobby, HobbyDTO.class);
  }

  public Hobby toEntity(HobbyDTO hobbyDTO) {
    return modelMapper.map(hobbyDTO, Hobby.class);
  }
}
