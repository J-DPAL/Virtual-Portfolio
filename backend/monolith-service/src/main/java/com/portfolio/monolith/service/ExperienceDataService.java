package com.portfolio.monolith.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.portfolio.monolith.dto.WorkExperienceDto;
import com.portfolio.monolith.exception.NotFoundException;

@Service
public class ExperienceDataService {

  private final NamedParameterJdbcTemplate jdbc;

  public ExperienceDataService(NamedParameterJdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<WorkExperienceDto> getAllExperiences() {
    String sql = "select * from public.work_experience order by start_date desc";
    return jdbc.query(sql, rowMapper());
  }

  public WorkExperienceDto getExperienceById(Long id) {
    String sql = "select * from public.work_experience where id = :id";
    return queryOne(sql, new MapSqlParameterSource("id", id), "Experience not found");
  }

  public List<WorkExperienceDto> getCurrentExperiences() {
    String sql = "select * from public.work_experience where is_current = true order by start_date desc";
    return jdbc.query(sql, rowMapper());
  }

  public WorkExperienceDto createExperience(WorkExperienceDto dto) {
    String sql =
        "insert into public.work_experience ("
            + "company_name_en, company_name_fr, company_name_es, "
            + "position_en, position_fr, position_es, "
            + "description_en, description_fr, description_es, "
            + "location_en, location_fr, location_es, "
            + "start_date, end_date, is_current, skills_used, icon"
            + ") values ("
            + ":companyNameEn, :companyNameFr, :companyNameEs, "
            + ":positionEn, :positionFr, :positionEs, "
            + ":descriptionEn, :descriptionFr, :descriptionEs, "
            + ":locationEn, :locationFr, :locationEs, "
            + ":startDate, :endDate, :isCurrent, :skillsUsed, :icon"
            + ") returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("companyNameEn", dto.companyNameEn)
            .addValue("companyNameFr", dto.companyNameFr)
            .addValue("companyNameEs", dto.companyNameEs)
            .addValue("positionEn", dto.positionEn)
            .addValue("positionFr", dto.positionFr)
            .addValue("positionEs", dto.positionEs)
            .addValue("descriptionEn", dto.descriptionEn)
            .addValue("descriptionFr", dto.descriptionFr)
            .addValue("descriptionEs", dto.descriptionEs)
            .addValue("locationEn", dto.locationEn)
            .addValue("locationFr", dto.locationFr)
            .addValue("locationEs", dto.locationEs)
            .addValue("startDate", dto.startDate)
            .addValue("endDate", dto.endDate)
            .addValue("isCurrent", dto.isCurrent)
            .addValue("skillsUsed", dto.skillsUsed)
            .addValue("icon", dto.icon);

    return jdbc.queryForObject(sql, p, rowMapper());
  }

  public WorkExperienceDto updateExperience(Long id, WorkExperienceDto dto) {
    String sql =
        "update public.work_experience set "
            + "company_name_en = :companyNameEn, company_name_fr = :companyNameFr, company_name_es = :companyNameEs, "
            + "position_en = :positionEn, position_fr = :positionFr, position_es = :positionEs, "
            + "description_en = :descriptionEn, description_fr = :descriptionFr, description_es = :descriptionEs, "
            + "location_en = :locationEn, location_fr = :locationFr, location_es = :locationEs, "
            + "start_date = :startDate, end_date = :endDate, is_current = :isCurrent, skills_used = :skillsUsed, icon = :icon, "
            + "updated_at = now() where id = :id returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("companyNameEn", dto.companyNameEn)
            .addValue("companyNameFr", dto.companyNameFr)
            .addValue("companyNameEs", dto.companyNameEs)
            .addValue("positionEn", dto.positionEn)
            .addValue("positionFr", dto.positionFr)
            .addValue("positionEs", dto.positionEs)
            .addValue("descriptionEn", dto.descriptionEn)
            .addValue("descriptionFr", dto.descriptionFr)
            .addValue("descriptionEs", dto.descriptionEs)
            .addValue("locationEn", dto.locationEn)
            .addValue("locationFr", dto.locationFr)
            .addValue("locationEs", dto.locationEs)
            .addValue("startDate", dto.startDate)
            .addValue("endDate", dto.endDate)
            .addValue("isCurrent", dto.isCurrent)
            .addValue("skillsUsed", dto.skillsUsed)
            .addValue("icon", dto.icon);

    return queryOne(sql, p, "Experience not found");
  }

  public void deleteExperience(Long id) {
    String sql = "delete from public.work_experience where id = :id";
    int rows = jdbc.update(sql, new MapSqlParameterSource("id", id));
    if (rows == 0) {
      throw new NotFoundException("Experience not found");
    }
  }

  private WorkExperienceDto queryOne(String sql, SqlParameterSource params, String notFoundMessage) {
    try {
      return jdbc.queryForObject(sql, params, rowMapper());
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundException(notFoundMessage);
    }
  }

  private RowMapper<WorkExperienceDto> rowMapper() {
    return (rs, rowNum) -> {
      WorkExperienceDto dto = new WorkExperienceDto();
      dto.id = rs.getLong("id");
      dto.companyNameEn = rs.getString("company_name_en");
      dto.companyNameFr = rs.getString("company_name_fr");
      dto.companyNameEs = rs.getString("company_name_es");
      dto.positionEn = rs.getString("position_en");
      dto.positionFr = rs.getString("position_fr");
      dto.positionEs = rs.getString("position_es");
      dto.descriptionEn = rs.getString("description_en");
      dto.descriptionFr = rs.getString("description_fr");
      dto.descriptionEs = rs.getString("description_es");
      dto.locationEn = rs.getString("location_en");
      dto.locationFr = rs.getString("location_fr");
      dto.locationEs = rs.getString("location_es");
      dto.startDate = rs.getObject("start_date", LocalDate.class);
      dto.endDate = rs.getObject("end_date", LocalDate.class);
      dto.isCurrent = (Boolean) rs.getObject("is_current");
      dto.skillsUsed = rs.getString("skills_used");
      dto.icon = rs.getString("icon");
      dto.createdAt = toLocalDateTime(rs.getTimestamp("created_at"));
      dto.updatedAt = toLocalDateTime(rs.getTimestamp("updated_at"));
      return dto;
    };
  }

  private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
    return timestamp == null ? null : timestamp.toLocalDateTime();
  }
}
