package com.portfolio.users.mappingLayer.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.portfolio.users.dataAccessLayer.entity.User;
import com.portfolio.users.mappingLayer.dto.UserDTO;

@Component
public class UserMapper {

  private final ModelMapper modelMapper;

  public UserMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public UserDTO toDTO(User user) {
    return modelMapper.map(user, UserDTO.class);
  }

  public User toEntity(UserDTO userDTO) {
    return modelMapper.map(userDTO, User.class);
  }
}
