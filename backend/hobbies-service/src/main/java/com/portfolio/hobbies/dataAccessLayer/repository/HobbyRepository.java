package com.portfolio.hobbies.dataAccessLayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.hobbies.dataAccessLayer.entity.Hobby;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {}
