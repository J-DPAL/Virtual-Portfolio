package com.portfolio.messages.mappingLayer.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.portfolio.messages.dataAccessLayer.entity.Message;
import com.portfolio.messages.mappingLayer.dto.MessageDTO;

@Component
public class MessageMapper {

  private final ModelMapper modelMapper;

  public MessageMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public MessageDTO toDTO(Message message) {
    return modelMapper.map(message, MessageDTO.class);
  }

  public Message toEntity(MessageDTO messageDTO) {
    return modelMapper.map(messageDTO, Message.class);
  }
}
