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
            .titleFr("Application Mobile OpenHand MANA")
            .titleEs("Aplicación Móvil OpenHand MANA")
            .descriptionEn(
                "Development of a mobile application for assistive technology using Kotlin for Android. Focused on creating an accessible and user-friendly interface for individuals with physical disabilities.")
            .descriptionFr(
                "Développement d'une application mobile pour la technologie d'assistance utilisant Kotlin pour Android. Axé sur la création d'une interface accessible et conviviale pour les personnes ayant des handicaps physiques.")
            .descriptionEs(
                "Desarrollo de una aplicación móvil para tecnología de asistencia usando Kotlin para Android. Enfocado en crear una interfaz accesible y fácil de usar para personas con discapacidades físicas.")
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
            .titleFr("Système de Gestion de Clinique Vétérinaire Champlain")
            .titleEs("Sistema de Gestión de Clínica Veterinaria Champlain")
            .descriptionEn(
                "Pet clinic management system developed using Spring Boot microservices architecture. Implemented features for appointment scheduling, pet health records, and veterinary service management using Scrum/Agile methodology.")
            .descriptionFr(
                "Système de gestion de clinique vétérinaire développé avec l'architecture de microservices Spring Boot. Fonctionnalités implémentées pour la planification des rendez-vous, les dossiers de santé des animaux et la gestion des services vétérinaires utilisant la méthodologie Scrum/Agile.")
            .descriptionEs(
                "Sistema de gestión de clínica veterinaria desarrollado usando arquitectura de microservicios Spring Boot. Implementación de funcionalidades para programación de citas, registros de salud de mascotas y gestión de servicios veterinarios usando metodología Scrum/Agile.")
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
