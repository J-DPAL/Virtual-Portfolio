package com.portfolio.projects.utils;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.portfolio.projects.dataAccessLayer.entity.Project;
import com.portfolio.projects.dataAccessLayer.repository.ProjectRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProjectDataLoader implements CommandLineRunner {

  private final ProjectRepository projectRepository;

  public ProjectDataLoader(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void run(String... args) {
    if (projectRepository.count() == 0) {
      log.info("Loading initial project data...");
      loadProjects();
    } else {
      log.info("Project data already exists, skipping initialization");
    }
  }

  private void loadProjects() {
    projectRepository.save(
        Project.builder()
            .titleEn("OpenHand MANA Mobile App")
            .titleAr("تطبيق OpenHand MANA للجوال")
            .descriptionEn(
                "Development of a mobile application for assistive technology using Kotlin for Android. Focused on creating an accessible and user-friendly interface for individuals with physical disabilities.")
            .descriptionAr(
                "تطوير تطبيق جوال لتكنولوجيا مساعدة باستخدام Kotlin لنظام Android. التركيز على إنشاء واجهة سهلة الاستخدام وآمنة للأشخاص ذوي الإعاقات الجسدية.")
            .technologies("Kotlin, Android, Material Design, REST APIs")
            .projectUrl("https://github.com/openhand")
            .githubUrl("https://github.com/openhand/mana-mobile")
            .startDate(LocalDate.of(2026, 2, 1))
            .endDate(null)
            .status("In Progress")
            .build());

    projectRepository.save(
        Project.builder()
            .titleEn("Champlain PetClinic Scrum")
            .titleAr("نظام إدارة عيادة الحيوانات الأليفة Champlain PetClinic")
            .descriptionEn(
                "Pet clinic management system developed using Spring Boot microservices architecture. Implemented features for appointment scheduling, pet health records, and veterinary service management using Scrum/Agile methodology.")
            .descriptionAr(
                "نظام إدارة عيادة الحيوانات الأليفة تم تطويره باستخدام بنية الخدمات الدقيقة Spring Boot. تم تنفيذ ميزات جدولة المواعيد وسجلات صحة الحيوانات الأليفة وإدارة خدمات الطب البيطري باستخدام منهجية Scrum/Agile.")
            .technologies("Java, Spring Boot, React, PostgreSQL, Docker, Microservices")
            .projectUrl("https://champlain-petclinic.com")
            .githubUrl("https://github.com/champlain/petclinic")
            .startDate(LocalDate.of(2025, 10, 1))
            .endDate(LocalDate.of(2025, 12, 31))
            .status("Completed")
            .build());

    log.info("Completed loading {} projects", projectRepository.count());
  }
}
