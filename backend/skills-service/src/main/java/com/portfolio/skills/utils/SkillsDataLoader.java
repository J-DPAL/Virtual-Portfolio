package com.portfolio.skills.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.portfolio.skills.dataAccessLayer.entity.Skill;
import com.portfolio.skills.dataAccessLayer.repository.SkillRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SkillsDataLoader implements CommandLineRunner {

  private final SkillRepository skillRepository;

  public SkillsDataLoader(SkillRepository skillRepository) {
    this.skillRepository = skillRepository;
  }

  @Override
  public void run(String... args) {
    if (skillRepository.count() == 0) {
      log.info("Loading initial skills data...");
      loadSkills();
    } else {
      log.info("Skills data already exists, skipping initialization");
    }
  }

  private void loadSkills() {
    skillRepository.save(
        Skill.builder()
            .nameEn("Java")
            .nameFr("Java")
            .descriptionEn("Java - Object-oriented programming language")
            .descriptionFr("Java - Langage de programmation orienté objet")
            .proficiencyLevel("Advanced")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Python")
            .nameFr("Python")
            .descriptionEn("Python - High-level programming language")
            .descriptionFr("Python - Langage de programmation haut niveau")
            .proficiencyLevel("Advanced")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("JavaScript")
            .nameFr("JavaScript")
            .descriptionEn("JavaScript - Dynamic programming language for web")
            .descriptionFr("JavaScript - Langage de programmation dynamique pour le web")
            .proficiencyLevel("Advanced")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("SQL")
            .nameFr("SQL")
            .descriptionEn("SQL - Structured Query Language for databases")
            .descriptionFr("SQL - Langage de requête structuré pour les bases de données")
            .proficiencyLevel("Advanced")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("HTML/CSS")
            .nameFr("HTML/CSS")
            .descriptionEn("HTML/CSS - Markup and styling for web pages")
            .descriptionFr("HTML/CSS - Balisage et style pour les pages web")
            .proficiencyLevel("Advanced")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("ReactJS")
            .nameFr("ReactJS")
            .descriptionEn("ReactJS - JavaScript library for building user interfaces")
            .descriptionFr(
                "ReactJS - Bibliothèque JavaScript pour la construction d'interfaces utilisateur")
            .proficiencyLevel("Advanced")
            .category("Programming")
            .build());
    skillRepository.save(
        Skill.builder()
            .nameEn("AngularJS")
            .nameFr("AngularJS")
            .descriptionEn("AngularJS - JavaScript framework for dynamic web applications")
            .descriptionFr("AngularJS - Framework JavaScript pour les applications web dynamiques")
            .proficiencyLevel("Intermediate")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("PHP")
            .nameFr("PHP")
            .descriptionEn("PHP - Server-side scripting language used with Laravel")
            .descriptionFr("PHP - Langage de script côté serveur utilisé avec Laravel")
            .proficiencyLevel("Intermediate")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Kotlin")
            .nameFr("Kotlin")
            .descriptionEn("Kotlin - Modern Android development language")
            .descriptionFr("Kotlin - Langage moderne de développement Android")
            .proficiencyLevel("Beginner")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("C#")
            .nameFr("C#")
            .descriptionEn("C# - Object-oriented programming language for .NET")
            .descriptionFr("C# - Langage de programmation orienté objet pour .NET")
            .proficiencyLevel("Intermediate")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn(".NET")
            .nameFr(".NET")
            .descriptionEn(".NET Framework - Cross-platform application development")
            .descriptionFr("Framework .NET - Développement d'applications multiplateforme")
            .proficiencyLevel("Intermediate")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Swift")
            .nameFr("Swift")
            .descriptionEn("Swift - Programming language for iOS and macOS development")
            .descriptionFr("Swift - Langage de programmation pour le développement iOS et macOS")
            .proficiencyLevel("Beginner")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("FastAPI")
            .nameFr("FastAPI")
            .descriptionEn("FastAPI - Modern Python web framework for building APIs")
            .descriptionFr("FastAPI - Framework web Python moderne pour la construction d'API")
            .proficiencyLevel("Intermediate")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Laravel")
            .nameFr("Laravel")
            .descriptionEn("Laravel - PHP web application framework")
            .descriptionFr("Laravel - Framework d'application web PHP")
            .proficiencyLevel("Intermediate")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Bootstrap")
            .nameFr("Bootstrap")
            .descriptionEn("Bootstrap - CSS framework for responsive design")
            .descriptionFr("Bootstrap - Framework CSS pour la conception réactive")
            .proficiencyLevel("Intermediate")
            .category("Development Tools")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("JUnit5")
            .nameFr("JUnit5")
            .descriptionEn("JUnit5 - Java testing framework for unit testing")
            .descriptionFr("JUnit5 - Framework de test Java pour les tests unitaires")
            .proficiencyLevel("Intermediate")
            .category("Development Tools")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Xcode")
            .nameFr("Xcode")
            .descriptionEn("Xcode - Apple IDE for iOS and macOS development")
            .descriptionFr("Xcode - IDE Apple pour le développement iOS et macOS")
            .proficiencyLevel("Beginner")
            .category("Development Tools")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Vite")
            .nameFr("Vite")
            .descriptionEn("Vite - Fast frontend build tool and dev server")
            .descriptionFr(
                "Vite - Outil de construction frontend rapide et serveur de développement")
            .proficiencyLevel("Intermediate")
            .category("Development Tools")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Tailwind CSS")
            .nameFr("Tailwind CSS")
            .descriptionEn("Tailwind CSS - Utility-first CSS framework")
            .descriptionFr("Tailwind CSS - Framework CSS utilitaire-first")
            .proficiencyLevel("Intermediate")
            .category("Development Tools")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("REST APIs")
            .nameFr("REST APIs")
            .descriptionEn("REST APIs - Representational State Transfer architecture")
            .descriptionFr("REST APIs - Architecture de transfert d'état représentationnel")
            .proficiencyLevel("Advanced")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Distributed Systems")
            .nameFr("Distributed Systems")
            .descriptionEn(
                "Distributed Systems - Design and architecture of distributed applications")
            .descriptionFr(
                "Systèmes Distribués - Conception et architecture d'applications distribuées")
            .proficiencyLevel("Intermediate")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Docker")
            .nameFr("Docker")
            .descriptionEn("Docker - Containerization platform for application deployment")
            .descriptionFr(
                "Docker - Plateforme de conteneurisation pour le déploiement d'applications")
            .proficiencyLevel("Advanced")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Docker Compose")
            .nameFr("Docker Compose")
            .descriptionEn("Docker Compose - Tool for defining multi-container Docker applications")
            .descriptionFr(
                "Docker Compose - Outil de définition d'applications Docker multi-conteneurs")
            .proficiencyLevel("Advanced")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Spring Boot")
            .nameFr("Spring Boot")
            .descriptionEn("Spring Boot - Framework for building Java applications")
            .descriptionFr("Spring Boot - Framework pour la construction d'applications Java")
            .proficiencyLevel("Advanced")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Git/GitHub")
            .nameFr("Git/GitHub")
            .descriptionEn("Git and GitHub - Version control and collaborative development")
            .descriptionFr("Git et GitHub - Contrôle de version et développement collaboratif")
            .proficiencyLevel("Advanced")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("PostgreSQL")
            .nameFr("PostgreSQL")
            .descriptionEn("PostgreSQL - Advanced open-source relational database")
            .descriptionFr("PostgreSQL - Base de données relationnelle open-source avancée")
            .proficiencyLevel("Advanced")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Scrum/Agile")
            .nameFr("Scrum/Agile")
            .descriptionEn("Scrum and Agile - Software development methodologies")
            .descriptionFr("Scrum et Agile - Méthodologies de développement logiciel")
            .proficiencyLevel("Advanced")
            .category("Work Methods")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Time Management")
            .nameFr("Gestion du Temps")
            .descriptionEn("Time Management - Organizing and prioritizing work effectively")
            .descriptionFr("Gestion du Temps - Organisation et priorisation du travail efficace")
            .proficiencyLevel("Advanced")
            .category("Work Methods")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Problem Solving")
            .nameFr("Résolution de Problèmes")
            .descriptionEn("Problem Solving - Analytical thinking and creative solutions")
            .descriptionFr("Résolution de Problèmes - Pensée analytique et solutions créatives")
            .proficiencyLevel("Advanced")
            .category("Work Methods")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Teamwork")
            .nameFr("Travail d'Équipe")
            .descriptionEn("Teamwork - Collaborating effectively in team environments")
            .descriptionFr(
                "Travail d'Équipe - Collaboration efficace dans les environnements d'équipe")
            .proficiencyLevel("Advanced")
            .category("Work Methods")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Unity")
            .nameFr("Unity")
            .descriptionEn("Unity - Game development engine")
            .descriptionFr("Unity - Moteur de développement de jeux")
            .proficiencyLevel("Intermediate")
            .category("Development Tools")
            .build());

    log.info("Completed loading {} skills", skillRepository.count());
  }
}
