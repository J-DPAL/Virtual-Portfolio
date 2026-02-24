package com.portfolio.monolith.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.sql.Timestamp;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.portfolio.monolith.dto.ProjectDto;
import com.portfolio.monolith.exception.NotFoundException;

@Service
public class ProjectDataService {

  private final NamedParameterJdbcTemplate jdbc;

  public ProjectDataService(NamedParameterJdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<ProjectDto> getAllProjects() {
    String sql = "select * from public.projects order by created_at desc";
    return jdbc.query(sql, rowMapper());
  }

  public ProjectDto getProjectById(Long id) {
    String sql = "select * from public.projects where id = :id";
    return queryOne(sql, new MapSqlParameterSource("id", id), "Project not found");
  }

  public List<ProjectDto> getProjectsByStatus(String status) {
    String sql = "select * from public.projects where status = :status order by created_at desc";
    return jdbc.query(sql, new MapSqlParameterSource("status", status), rowMapper());
  }

  public ProjectDto createProject(ProjectDto dto) {
    String sql =
        "insert into public.projects ("
            + "title_en, title_fr, title_es, description_en, description_fr, description_es, "
            + "technologies, project_url, github_url, image_url, start_date, end_date, status"
            + ") values ("
            + ":titleEn, :titleFr, :titleEs, :descriptionEn, :descriptionFr, :descriptionEs, "
            + ":technologies, :projectUrl, :githubUrl, :imageUrl, :startDate, :endDate, :status"
            + ") returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("titleEn", dto.titleEn)
            .addValue("titleFr", dto.titleFr)
            .addValue("titleEs", dto.titleEs)
            .addValue("descriptionEn", dto.descriptionEn)
            .addValue("descriptionFr", dto.descriptionFr)
            .addValue("descriptionEs", dto.descriptionEs)
            .addValue("technologies", dto.technologies)
            .addValue("projectUrl", dto.projectUrl)
            .addValue("githubUrl", dto.githubUrl)
            .addValue("imageUrl", dto.imageUrl)
            .addValue("startDate", dto.startDate)
            .addValue("endDate", dto.endDate)
            .addValue("status", dto.status == null ? "Completed" : dto.status);

    return jdbc.queryForObject(sql, p, rowMapper());
  }

  public ProjectDto updateProject(Long id, ProjectDto dto) {
    String sql =
        "update public.projects set "
            + "title_en = :titleEn, title_fr = :titleFr, title_es = :titleEs, "
            + "description_en = :descriptionEn, description_fr = :descriptionFr, description_es = :descriptionEs, "
            + "technologies = :technologies, project_url = :projectUrl, github_url = :githubUrl, image_url = :imageUrl, "
            + "start_date = :startDate, end_date = :endDate, status = :status, updated_at = now() "
            + "where id = :id returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("titleEn", dto.titleEn)
            .addValue("titleFr", dto.titleFr)
            .addValue("titleEs", dto.titleEs)
            .addValue("descriptionEn", dto.descriptionEn)
            .addValue("descriptionFr", dto.descriptionFr)
            .addValue("descriptionEs", dto.descriptionEs)
            .addValue("technologies", dto.technologies)
            .addValue("projectUrl", dto.projectUrl)
            .addValue("githubUrl", dto.githubUrl)
            .addValue("imageUrl", dto.imageUrl)
            .addValue("startDate", dto.startDate)
            .addValue("endDate", dto.endDate)
            .addValue("status", dto.status);

    return queryOne(sql, p, "Project not found");
  }

  public void deleteProject(Long id) {
    String sql = "delete from public.projects where id = :id";
    int rows = jdbc.update(sql, new MapSqlParameterSource("id", id));
    if (rows == 0) {
      throw new NotFoundException("Project not found");
    }
  }

  private ProjectDto queryOne(String sql, SqlParameterSource params, String notFoundMessage) {
    try {
      return jdbc.queryForObject(sql, params, rowMapper());
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundException(notFoundMessage);
    }
  }

  private RowMapper<ProjectDto> rowMapper() {
    return (rs, rowNum) -> {
      ProjectDto dto = new ProjectDto();
      dto.id = rs.getLong("id");
      dto.titleEn = rs.getString("title_en");
      dto.titleFr = rs.getString("title_fr");
      dto.titleEs = rs.getString("title_es");
      dto.descriptionEn = rs.getString("description_en");
      dto.descriptionFr = rs.getString("description_fr");
      dto.descriptionEs = rs.getString("description_es");
      dto.technologies = rs.getString("technologies");
      dto.projectUrl = rs.getString("project_url");
      dto.githubUrl = rs.getString("github_url");
      dto.imageUrl = rs.getString("image_url");
      dto.startDate = rs.getObject("start_date", LocalDate.class);
      dto.endDate = rs.getObject("end_date", LocalDate.class);
      dto.status = rs.getString("status");
      dto.createdAt = toLocalDateTime(rs.getTimestamp("created_at"));
      dto.updatedAt = toLocalDateTime(rs.getTimestamp("updated_at"));
      return dto;
    };
  }

  private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
    return timestamp == null ? null : timestamp.toLocalDateTime();
  }
}
