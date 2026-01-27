package com.portfolio.experience.businessLayer.service;

import com.portfolio.experience.dataAccessLayer.entity.WorkExperience;
import com.portfolio.experience.dataAccessLayer.repository.WorkExperienceRepository;
import com.portfolio.experience.mappingLayer.dto.WorkExperienceDTO;
import com.portfolio.experience.mappingLayer.mapper.WorkExperienceMapper;
import com.portfolio.experience.utils.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceMapper workExperienceMapper;

    public WorkExperienceService(WorkExperienceRepository workExperienceRepository, 
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

    public WorkExperienceDTO getExperienceById(Long id) {
        WorkExperience workExperience = workExperienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Work experience not found with id: " + id));
        return workExperienceMapper.toDTO(workExperience);
    }

    public List<WorkExperienceDTO> getCurrentExperiences() {
        return workExperienceRepository.findByIsCurrent(true).stream()
                .map(workExperienceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public WorkExperienceDTO createExperience(WorkExperienceDTO workExperienceDTO) {
        WorkExperience workExperience = workExperienceMapper.toEntity(workExperienceDTO);
        WorkExperience savedExperience = workExperienceRepository.save(workExperience);
        return workExperienceMapper.toDTO(savedExperience);
    }

    public WorkExperienceDTO updateExperience(Long id, WorkExperienceDTO workExperienceDTO) {
        WorkExperience existingExperience = workExperienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Work experience not found with id: " + id));

        existingExperience.setCompanyNameEn(workExperienceDTO.getCompanyNameEn());
        existingExperience.setCompanyNameAr(workExperienceDTO.getCompanyNameAr());
        existingExperience.setPositionEn(workExperienceDTO.getPositionEn());
        existingExperience.setPositionAr(workExperienceDTO.getPositionAr());
        existingExperience.setDescriptionEn(workExperienceDTO.getDescriptionEn());
        existingExperience.setDescriptionAr(workExperienceDTO.getDescriptionAr());
        existingExperience.setLocationEn(workExperienceDTO.getLocationEn());
        existingExperience.setLocationAr(workExperienceDTO.getLocationAr());
        existingExperience.setStartDate(workExperienceDTO.getStartDate());
        existingExperience.setEndDate(workExperienceDTO.getEndDate());
        existingExperience.setIsCurrent(workExperienceDTO.getIsCurrent());

        WorkExperience updatedExperience = workExperienceRepository.save(existingExperience);
        return workExperienceMapper.toDTO(updatedExperience);
    }

    public void deleteExperience(Long id) {
        if (!workExperienceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Work experience not found with id: " + id);
        }
        workExperienceRepository.deleteById(id);
    }
}
