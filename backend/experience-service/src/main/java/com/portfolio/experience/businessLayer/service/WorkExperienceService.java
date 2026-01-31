package com.portfolio.experience.businessLayer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.experience.dataAccessLayer.entity.WorkExperience;
import com.portfolio.experience.dataAccessLayer.repository.WorkExperienceRepository;
import com.portfolio.experience.mappingLayer.dto.WorkExperienceDTO;
import com.portfolio.experience.mappingLayer.mapper.WorkExperienceMapper;
import com.portfolio.experience.utils.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class WorkExperienceService {

  private final WorkExperienceRepository workExperienceRepository;
  private final WorkExperienceMapper workExperienceMapper;

  public WorkExperienceService(
      WorkExperienceRepository workExperienceRepository,
      WorkExperienceMapper workExperienceMapper) {
    this.workExperienceRepository = workExperienceRepository;
    this.workExperienceMapper = workExperienceMapper;
  }

  public List<WorkExperienceDTO> getAllExperiences() {
    return workExperienceRepository.findAll().stream()
        .map(workExperienceMapper::toDTO)
        .collect(Collectors.toList());
  }

  public List<WorkExperienceDTO> getAllExperiencesOrderedByDate() {
    return workExperienceRepository.findByOrderByStartDateDesc().stream()
        .map(workExperienceMapper::toDTO)
        .collect(Collectors.toList());
  }

  public WorkExperienceDTO getExperienceById(@NonNull Long id) {
    WorkExperience workExperience =
        workExperienceRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Work experience not found with id: " + id));
    return workExperienceMapper.toDTO(workExperience);
  }

  public List<WorkExperienceDTO> getCurrentExperiences() {
    return workExperienceRepository.findByIsCurrent(true).stream()
        .map(workExperienceMapper::toDTO)
        .collect(Collectors.toList());
  }

  @SuppressWarnings("null")
  public WorkExperienceDTO createExperience(@NonNull WorkExperienceDTO workExperienceDTO) {
    WorkExperience workExperience = workExperienceMapper.toEntity(workExperienceDTO);
    WorkExperience savedExperience = workExperienceRepository.save(workExperience);
    return workExperienceMapper.toDTO(savedExperience);
  }

  public WorkExperienceDTO updateExperience(
      @NonNull Long id, @NonNull WorkExperienceDTO workExperienceDTO) {
    WorkExperience existingExperience =
        workExperienceRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Work experience not found with id: " + id));

    existingExperience.setCompanyNameEn(workExperienceDTO.getCompanyNameEn());
    existingExperience.setCompanyNameFr(workExperienceDTO.getCompanyNameFr());
    existingExperience.setCompanyNameEs(workExperienceDTO.getCompanyNameEs());
    existingExperience.setPositionEn(workExperienceDTO.getPositionEn());
    existingExperience.setPositionFr(workExperienceDTO.getPositionFr());
    existingExperience.setPositionEs(workExperienceDTO.getPositionEs());
    existingExperience.setDescriptionEn(workExperienceDTO.getDescriptionEn());
    existingExperience.setDescriptionFr(workExperienceDTO.getDescriptionFr());
    existingExperience.setDescriptionEs(workExperienceDTO.getDescriptionEs());
    existingExperience.setLocationEn(workExperienceDTO.getLocationEn());
    existingExperience.setLocationFr(workExperienceDTO.getLocationFr());
    existingExperience.setLocationEs(workExperienceDTO.getLocationEs());
    existingExperience.setStartDate(workExperienceDTO.getStartDate());
    existingExperience.setEndDate(workExperienceDTO.getEndDate());
    existingExperience.setIsCurrent(workExperienceDTO.getIsCurrent());

    WorkExperience updatedExperience = workExperienceRepository.save(existingExperience);
    return workExperienceMapper.toDTO(updatedExperience);
  }

  public void deleteExperience(@NonNull Long id) {
    if (!workExperienceRepository.existsById(id)) {
      throw new ResourceNotFoundException("Work experience not found with id: " + id);
    }
    workExperienceRepository.deleteById(id);
  }
}
