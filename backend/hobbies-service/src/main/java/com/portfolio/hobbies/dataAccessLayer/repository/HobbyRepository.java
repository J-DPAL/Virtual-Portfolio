package com.portfolio.hobbies.dataAccessLayer.repository;

import com.portfolio.hobbies.dataAccessLayer.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {
}
