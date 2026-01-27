package com.portfolio.messages.dataAccessLayer.repository;

import com.portfolio.messages.dataAccessLayer.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByIsRead(Boolean isRead);
    List<Message> findByOrderByCreatedAtDesc();
}
