package com.portfolio.monolith.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.portfolio.monolith.dto.SkillDto;
import com.portfolio.monolith.exception.NotFoundException;

@Service
public class SkillDataService {

  private final NamedParameterJdbcTemplate jdbc;

  public SkillDataService(NamedParameterJdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<SkillDto> getAllSkills() {
    String sql = "select * from public.skills order by category asc, name_en asc";
    return jdbc.query(sql, rowMapper());
  }

  public SkillDto getSkillById(Long id) {
    String sql = "select * from public.skills where id = :id";
    return queryOne(sql, new MapSqlParameterSource("id", id), "Skill not found");
  }

  public List<SkillDto> getSkillsByCategory(String category) {
    String sql = "select * from public.skills where category = :category order by name_en asc";
    return jdbc.query(sql, new MapSqlParameterSource("category", category), rowMapper());
  }

  public SkillDto createSkill(SkillDto dto) {
    String sql =
        "insert into public.skills ("
            + "name_en, name_fr, name_es, description_en, description_fr, description_es, "
            + "proficiency_level, category, years_of_experience"
            + ") values ("
            + ":nameEn, :nameFr, :nameEs, :descriptionEn, :descriptionFr, :descriptionEs, "
            + ":proficiencyLevel, :category, :yearsOfExperience"
            + ") returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("nameEn", dto.nameEn)
            .addValue("nameFr", dto.nameFr)
            .addValue("nameEs", dto.nameEs)
            .addValue("descriptionEn", dto.descriptionEn)
            .addValue("descriptionFr", dto.descriptionFr)
            .addValue("descriptionEs", dto.descriptionEs)
            .addValue("proficiencyLevel", dto.proficiencyLevel)
            .addValue("category", dto.category)
            .addValue("yearsOfExperience", dto.yearsOfExperience);

    return jdbc.queryForObject(sql, p, rowMapper());
  }

  public SkillDto updateSkill(Long id, SkillDto dto) {
    String sql =
        "update public.skills set "
            + "name_en = :nameEn, name_fr = :nameFr, name_es = :nameEs, "
            + "description_en = :descriptionEn, description_fr = :descriptionFr, description_es = :descriptionEs, "
            + "proficiency_level = :proficiencyLevel, category = :category, years_of_experience = :yearsOfExperience, "
            + "updated_at = now() where id = :id returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("nameEn", dto.nameEn)
            .addValue("nameFr", dto.nameFr)
            .addValue("nameEs", dto.nameEs)
            .addValue("descriptionEn", dto.descriptionEn)
            .addValue("descriptionFr", dto.descriptionFr)
            .addValue("descriptionEs", dto.descriptionEs)
            .addValue("proficiencyLevel", dto.proficiencyLevel)
            .addValue("category", dto.category)
            .addValue("yearsOfExperience", dto.yearsOfExperience);

    return queryOne(sql, p, "Skill not found");
  }

  public void deleteSkill(Long id) {
    String sql = "delete from public.skills where id = :id";
    int rows = jdbc.update(sql, new MapSqlParameterSource("id", id));
    if (rows == 0) {
      throw new NotFoundException("Skill not found");
    }
  }

  private SkillDto queryOne(String sql, SqlParameterSource params, String notFoundMessage) {
    try {
      return jdbc.queryForObject(sql, params, rowMapper());
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundException(notFoundMessage);
    }
  }

  private RowMapper<SkillDto> rowMapper() {
    return (rs, rowNum) -> {
      SkillDto dto = new SkillDto();
      dto.id = rs.getLong("id");
      dto.nameEn = rs.getString("name_en");
      dto.nameFr = rs.getString("name_fr");
      dto.nameEs = rs.getString("name_es");
      dto.descriptionEn = rs.getString("description_en");
      dto.descriptionFr = rs.getString("description_fr");
      dto.descriptionEs = rs.getString("description_es");
      dto.proficiencyLevel = rs.getString("proficiency_level");
      dto.category = rs.getString("category");
      dto.yearsOfExperience = (Integer) rs.getObject("years_of_experience");
      dto.createdAt = rs.getObject("created_at", LocalDateTime.class);
      dto.updatedAt = rs.getObject("updated_at", LocalDateTime.class);
      return dto;
    };
  }
}
