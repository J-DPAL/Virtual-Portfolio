package com.portfolio.hobbies.businessLayer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.hobbies.dataAccessLayer.entity.Hobby;
import com.portfolio.hobbies.dataAccessLayer.repository.HobbyRepository;
import com.portfolio.hobbies.mappingLayer.dto.HobbyDTO;
import com.portfolio.hobbies.mappingLayer.mapper.HobbyMapper;
import com.portfolio.hobbies.utils.exceptions.ResourceNotFoundException;

@Service
@Transactional
@SuppressWarnings("null")
public class HobbyService {

  private final HobbyRepository hobbyRepository;
  private final HobbyMapper hobbyMapper;

  public HobbyService(HobbyRepository hobbyRepository, HobbyMapper hobbyMapper) {
    this.hobbyRepository = hobbyRepository;
    this.hobbyMapper = hobbyMapper;
  }

  public List<HobbyDTO> getAllHobbies() {
    return hobbyRepository.findAll().stream().map(hobbyMapper::toDTO).collect(Collectors.toList());
  }

  public HobbyDTO getHobbyById(Long id) {
    Hobby hobby =
        hobbyRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Hobby not found with id: " + id));
    return hobbyMapper.toDTO(hobby);
  }

  public HobbyDTO createHobby(HobbyDTO hobbyDTO) {
    Hobby hobby = hobbyMapper.toEntity(hobbyDTO);
    Hobby savedHobby = hobbyRepository.save(hobby);
    return hobbyMapper.toDTO(savedHobby);
  }

  public HobbyDTO updateHobby(Long id, HobbyDTO hobbyDTO) {
    Hobby existingHobby =
        hobbyRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Hobby not found with id: " + id));

    existingHobby.setNameEn(hobbyDTO.getNameEn());
    existingHobby.setNameFr(hobbyDTO.getNameFr());
    existingHobby.setNameEs(hobbyDTO.getNameEs());
    existingHobby.setDescriptionEn(hobbyDTO.getDescriptionEn());
    existingHobby.setDescriptionFr(hobbyDTO.getDescriptionFr());
    existingHobby.setDescriptionEs(hobbyDTO.getDescriptionEs());
    existingHobby.setIcon(hobbyDTO.getIcon());

    Hobby updatedHobby = hobbyRepository.save(existingHobby);
    return hobbyMapper.toDTO(updatedHobby);
  }

  public void deleteHobby(Long id) {
    if (!hobbyRepository.existsById(id)) {
      throw new ResourceNotFoundException("Hobby not found with id: " + id);
    }
    hobbyRepository.deleteById(id);
  }
}
