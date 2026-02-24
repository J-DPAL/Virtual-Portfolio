package com.portfolio.monolith.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.portfolio.monolith.dto.MessageDto;
import com.portfolio.monolith.exception.NotFoundException;

@Service
public class MessageDataService {

  private final NamedParameterJdbcTemplate jdbc;

  public MessageDataService(NamedParameterJdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<MessageDto> getAllMessages() {
    String sql = "select * from public.messages order by created_at desc";
    return jdbc.query(sql, rowMapper());
  }

  public MessageDto getMessageById(Long id) {
    String sql = "select * from public.messages where id = :id";
    return queryOne(sql, new MapSqlParameterSource("id", id), "Message not found");
  }

  public List<MessageDto> getMessagesByReadStatus(Boolean isRead) {
    String sql = "select * from public.messages where is_read = :isRead order by created_at desc";
    return jdbc.query(sql, new MapSqlParameterSource("isRead", isRead), rowMapper());
  }

  public MessageDto createMessage(MessageDto dto) {
    String sql =
        "insert into public.messages (sender_name, sender_email, subject, message, is_read) "
            + "values (:senderName, :senderEmail, :subject, :message, false) returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("senderName", dto.senderName)
            .addValue("senderEmail", dto.senderEmail)
            .addValue("subject", dto.subject)
            .addValue("message", dto.message);

    return jdbc.queryForObject(sql, p, rowMapper());
  }

  public MessageDto updateMessage(Long id, MessageDto dto) {
    String sql =
        "update public.messages set sender_name = :senderName, sender_email = :senderEmail, "
            + "subject = :subject, message = :message, is_read = :isRead, updated_at = now() "
            + "where id = :id returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("senderName", dto.senderName)
            .addValue("senderEmail", dto.senderEmail)
            .addValue("subject", dto.subject)
            .addValue("message", dto.message)
            .addValue("isRead", dto.isRead == null ? false : dto.isRead);

    return queryOne(sql, p, "Message not found");
  }

  public MessageDto markMessageAsRead(Long id) {
    String sql = "update public.messages set is_read = true, updated_at = now() where id = :id returning *";
    return queryOne(sql, new MapSqlParameterSource("id", id), "Message not found");
  }

  public void deleteMessage(Long id) {
    String sql = "delete from public.messages where id = :id";
    int rows = jdbc.update(sql, new MapSqlParameterSource("id", id));
    if (rows == 0) {
      throw new NotFoundException("Message not found");
    }
  }

  public int deleteReadMessagesOlderThanDays(int days) {
    String sql =
        "delete from public.messages "
            + "where is_read = true "
            + "and updated_at < (now() - make_interval(days => :days))";
    return jdbc.update(sql, new MapSqlParameterSource("days", days));
  }

  private MessageDto queryOne(String sql, SqlParameterSource params, String notFoundMessage) {
    try {
      return jdbc.queryForObject(sql, params, rowMapper());
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundException(notFoundMessage);
    }
  }

  private RowMapper<MessageDto> rowMapper() {
    return (rs, rowNum) -> {
      MessageDto dto = new MessageDto();
      dto.id = rs.getLong("id");
      dto.senderName = rs.getString("sender_name");
      dto.senderEmail = rs.getString("sender_email");
      dto.subject = rs.getString("subject");
      dto.message = rs.getString("message");
      dto.isRead = (Boolean) rs.getObject("is_read");
      dto.createdAt = toLocalDateTime(rs.getTimestamp("created_at"));
      dto.updatedAt = toLocalDateTime(rs.getTimestamp("updated_at"));

      dto.name = dto.senderName;
      dto.email = dto.senderEmail;
      dto.content = dto.message;
      dto.date = dto.createdAt != null ? dto.createdAt.toString() : null;
      return dto;
    };
  }

  private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
    return timestamp == null ? null : timestamp.toLocalDateTime();
  }
}
