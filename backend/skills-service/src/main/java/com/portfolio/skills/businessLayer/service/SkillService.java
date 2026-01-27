package com.portfolio.skills.businessLayer.service;

import com.portfolio.skills.dataAccessLayer.entity.Skill;
import com.portfolio.skills.dataAccessLayer.repository.SkillRepository;
import com.portfolio.skills.mappingLayer.dto.SkillDTO;
import com.portfolio.skills.mappingLayer.mapper.SkillMapper;
import com.portfolio.skills.utils.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        return skillRepository.findAll().stream()
                .map(skillMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SkillDTO getSkillById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));
        return skillMapper.toDTO(skill);
    }

    public List<SkillDTO> getSkillsByCategory(String category) {
        return skillRepository.findByCategory(category).stream()
                .map(skillMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SkillDTO createSkill(SkillDTO skillDTO) {
        Skill skill = skillMapper.toEntity(skillDTO);
        Skill savedSkill = skillRepository.save(skill);
        return skillMapper.toDTO(savedSkill);
    }

    public SkillDTO updateSkill(Long id, SkillDTO skillDTO) {
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));

        existingSkill.setNameEn(skillDTO.getNameEn());
        existingSkill.setNameAr(skillDTO.getNameAr());
        existingSkill.setDescriptionEn(skillDTO.getDescriptionEn());
        existingSkill.setDescriptionAr(skillDTO.getDescriptionAr());
        existingSkill.setProficiencyLevel(skillDTO.getProficiencyLevel());
        existingSkill.setCategory(skillDTO.getCategory());
        existingSkill.setYearsOfExperience(skillDTO.getYearsOfExperience());

        Skill updatedSkill = skillRepository.save(existingSkill);
        return skillMapper.toDTO(updatedSkill);
    }

    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skill not found with id: " + id);
        }
        skillRepository.deleteById(id);
    }
}
