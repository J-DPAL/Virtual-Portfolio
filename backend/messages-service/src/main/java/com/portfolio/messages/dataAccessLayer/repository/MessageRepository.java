package com.portfolio.messages.dataAccessLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.messages.dataAccessLayer.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  List<Message> findByIsRead(Boolean isRead);

  List<Message> findByOrderByCreatedAtDesc();
}
