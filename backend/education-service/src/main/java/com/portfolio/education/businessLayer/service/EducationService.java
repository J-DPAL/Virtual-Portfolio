package com.portfolio.education.businessLayer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.education.dataAccessLayer.entity.Education;
import com.portfolio.education.dataAccessLayer.repository.EducationRepository;
import com.portfolio.education.mappingLayer.dto.EducationDTO;
import com.portfolio.education.mappingLayer.mapper.EducationMapper;
import com.portfolio.education.utils.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class EducationService {

  private final EducationRepository educationRepository;
  private final EducationMapper educationMapper;

  public EducationService(
      EducationRepository educationRepository, EducationMapper educationMapper) {
    this.educationRepository = educationRepository;
    this.educationMapper = educationMapper;
  }

  public List<EducationDTO> getAllEducation() {
    return educationRepository.findAll().stream()
        .map(educationMapper::toDTO)
        .collect(Collectors.toList());
  }

  public List<EducationDTO> getAllEducationOrderedByDate() {
    return educationRepository.findByOrderByStartDateDesc().stream()
        .map(educationMapper::toDTO)
        .collect(Collectors.toList());
  }

  public EducationDTO getEducationById(@NonNull Long id) {
    Education education =
        educationRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Education not found with id: " + id));
    return educationMapper.toDTO(education);
  }

  public List<EducationDTO> getCurrentEducation() {
    return educationRepository.findByIsCurrent(true).stream()
        .map(educationMapper::toDTO)
        .collect(Collectors.toList());
  }

  @SuppressWarnings("null")
  public EducationDTO createEducation(@NonNull EducationDTO educationDTO) {
    Education education = educationMapper.toEntity(educationDTO);
    Education savedEducation = educationRepository.save(education);
    return educationMapper.toDTO(savedEducation);
  }

  public EducationDTO updateEducation(@NonNull Long id, @NonNull EducationDTO educationDTO) {
    Education existingEducation =
        educationRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Education not found with id: " + id));

    existingEducation.setInstitutionNameEn(educationDTO.getInstitutionNameEn());
    existingEducation.setInstitutionNameFr(educationDTO.getInstitutionNameFr());
    existingEducation.setInstitutionNameEs(educationDTO.getInstitutionNameEs());
    existingEducation.setDegreeEn(educationDTO.getDegreeEn());
    existingEducation.setDegreeFr(educationDTO.getDegreeFr());
    existingEducation.setDegreeEs(educationDTO.getDegreeEs());
    existingEducation.setFieldOfStudyEn(educationDTO.getFieldOfStudyEn());
    existingEducation.setFieldOfStudyFr(educationDTO.getFieldOfStudyFr());
    existingEducation.setFieldOfStudyEs(educationDTO.getFieldOfStudyEs());
    existingEducation.setDescriptionEn(educationDTO.getDescriptionEn());
    existingEducation.setDescriptionFr(educationDTO.getDescriptionFr());
    existingEducation.setDescriptionEs(educationDTO.getDescriptionEs());
    existingEducation.setStartDate(educationDTO.getStartDate());
    existingEducation.setEndDate(educationDTO.getEndDate());
    existingEducation.setIsCurrent(educationDTO.getIsCurrent());
    existingEducation.setGpa(educationDTO.getGpa());

    Education updatedEducation = educationRepository.save(existingEducation);
    return educationMapper.toDTO(updatedEducation);
  }

  public void deleteEducation(@NonNull Long id) {
    if (!educationRepository.existsById(id)) {
      throw new ResourceNotFoundException("Education not found with id: " + id);
    }
    educationRepository.deleteById(id);
  }
}
