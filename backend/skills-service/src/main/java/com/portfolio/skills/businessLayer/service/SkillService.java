package com.portfolio.skills.businessLayer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.skills.dataAccessLayer.entity.Skill;
import com.portfolio.skills.dataAccessLayer.repository.SkillRepository;
import com.portfolio.skills.mappingLayer.dto.SkillDTO;
import com.portfolio.skills.mappingLayer.mapper.SkillMapper;
import com.portfolio.skills.utils.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class SkillService {

  private final SkillRepository skillRepository;
  private final SkillMapper skillMapper;

  public SkillService(SkillRepository skillRepository, SkillMapper skillMapper) {
    this.skillRepository = skillRepository;
    this.skillMapper = skillMapper;
  }

  public List<SkillDTO> getAllSkills() {
    return skillRepository.findAll().stream().map(skillMapper::toDTO).collect(Collectors.toList());
  }

  public SkillDTO getSkillById(Long id) {
    @SuppressWarnings("null")
    Skill skill =
        skillRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));
    return skillMapper.toDTO(skill);
  }

  public List<SkillDTO> getSkillsByCategory(String category) {
    return skillRepository.findByCategory(category).stream()
        .map(skillMapper::toDTO)
        .collect(Collectors.toList());
  }

  public SkillDTO createSkill(SkillDTO skillDTO) {
    @SuppressWarnings("null")
    Skill skill = skillMapper.toEntity(skillDTO);
    @SuppressWarnings("null")
    Skill savedSkill = skillRepository.save(skill);
    return skillMapper.toDTO(savedSkill);
  }

  public SkillDTO updateSkill(Long id, SkillDTO skillDTO) {
    @SuppressWarnings("null")
    Skill existingSkill =
        skillRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));

    existingSkill.setNameEn(skillDTO.getNameEn());
    existingSkill.setNameFr(skillDTO.getNameFr());
    existingSkill.setDescriptionEn(skillDTO.getDescriptionEn());
    existingSkill.setDescriptionFr(skillDTO.getDescriptionFr());
    existingSkill.setProficiencyLevel(skillDTO.getProficiencyLevel());
    existingSkill.setCategory(skillDTO.getCategory());
    existingSkill.setYearsOfExperience(skillDTO.getYearsOfExperience());

    @SuppressWarnings("null")
    Skill updatedSkill = skillRepository.save(existingSkill);
    return skillMapper.toDTO(updatedSkill);
  }

  @SuppressWarnings("null")
  public void deleteSkill(Long id) {
    if (!skillRepository.existsById(id)) {
      throw new ResourceNotFoundException("Skill not found with id: " + id);
    }
    skillRepository.deleteById(id);
  }
}
