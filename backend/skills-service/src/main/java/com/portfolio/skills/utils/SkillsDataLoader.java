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
            .nameEs("Java")
            .descriptionEn("Java - Object-oriented programming language")
            .descriptionFr("Java - Langage de programmation orienté objet")
            .descriptionEs("Java - Lenguaje de programación orientado a objetos")
            .proficiencyLevel("Advanced")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Python")
            .nameFr("Python")
            .nameEs("Python")
            .descriptionEn("Python - High-level programming language")
            .descriptionFr("Python - Langage de programmation haut niveau")
            .descriptionEs("Python - Lenguaje de programación de alto nivel")
            .proficiencyLevel("Advanced")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("JavaScript")
            .nameFr("JavaScript")
            .nameEs("JavaScript")
            .descriptionEn("JavaScript - Dynamic programming language for web")
            .descriptionFr("JavaScript - Langage de programmation dynamique pour le web")
            .descriptionEs("JavaScript - Lenguaje de programación dinámico para web")
            .proficiencyLevel("Advanced")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("SQL")
            .nameFr("SQL")
            .nameEs("SQL")
            .descriptionEn("SQL - Structured Query Language for databases")
            .descriptionFr("SQL - Langage de requête structuré pour les bases de données")
            .descriptionEs("SQL - Lenguaje de consulta estructurado para bases de datos")
            .proficiencyLevel("Advanced")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("HTML/CSS")
            .nameFr("HTML/CSS")
            .nameEs("HTML/CSS")
            .descriptionEn("HTML/CSS - Markup and styling for web pages")
            .descriptionFr("HTML/CSS - Balisage et style pour les pages web")
            .descriptionEs("HTML/CSS - Marcado y estilo para páginas web")
            .proficiencyLevel("Advanced")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("ReactJS")
            .nameFr("ReactJS")
            .nameEs("ReactJS")
            .descriptionEn("ReactJS - JavaScript library for building user interfaces")
            .descriptionFr(
                "ReactJS - Bibliothèque JavaScript pour la construction d'interfaces utilisateur")
            .descriptionEs("ReactJS - Biblioteca JavaScript para construir interfaces de usuario")
            .proficiencyLevel("Advanced")
            .category("Programming")
            .build());
    skillRepository.save(
        Skill.builder()
            .nameEn("AngularJS")
            .nameFr("AngularJS")
            .nameEs("AngularJS")
            .descriptionEn("AngularJS - JavaScript framework for dynamic web applications")
            .descriptionFr("AngularJS - Framework JavaScript pour les applications web dynamiques")
            .descriptionEs("AngularJS - Framework JavaScript para aplicaciones web dinámicas")
            .proficiencyLevel("Intermediate")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("PHP")
            .nameFr("PHP")
            .nameEs("PHP")
            .descriptionEn("PHP - Server-side scripting language used with Laravel")
            .descriptionFr("PHP - Langage de script côté serveur utilisé avec Laravel")
            .descriptionEs("PHP - Lenguaje de script del lado del servidor usado con Laravel")
            .proficiencyLevel("Intermediate")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Kotlin")
            .nameFr("Kotlin")
            .nameEs("Kotlin")
            .descriptionEn("Kotlin - Modern Android development language")
            .descriptionFr("Kotlin - Langage moderne de développement Android")
            .descriptionEs("Kotlin - Lenguaje moderno de desarrollo Android")
            .proficiencyLevel("Beginner")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("C#")
            .nameFr("C#")
            .nameEs("C#")
            .descriptionEn("C# - Object-oriented programming language for .NET")
            .descriptionFr("C# - Langage de programmation orienté objet pour .NET")
            .descriptionEs("C# - Lenguaje de programación orientado a objetos para .NET")
            .proficiencyLevel("Intermediate")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn(".NET")
            .nameFr(".NET")
            .nameEs(".NET")
            .descriptionEn(".NET Framework - Cross-platform application development")
            .descriptionFr("Framework .NET - Développement d'applications multiplateforme")
            .descriptionEs(".NET Framework - Desarrollo de aplicaciones multiplataforma")
            .proficiencyLevel("Intermediate")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Swift")
            .nameFr("Swift")
            .nameEs("Swift")
            .descriptionEn("Swift - Programming language for iOS and macOS development")
            .descriptionFr("Swift - Langage de programmation pour le développement iOS et macOS")
            .descriptionEs("Swift - Lenguaje de programación para desarrollo iOS y macOS")
            .proficiencyLevel("Beginner")
            .category("Programming")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("FastAPI")
            .nameFr("FastAPI")
            .nameEs("FastAPI")
            .descriptionEn("FastAPI - Modern Python web framework for building APIs")
            .descriptionFr("FastAPI - Framework web Python moderne pour la construction d'API")
            .descriptionEs("FastAPI - Framework web Python moderno para construir APIs")
            .proficiencyLevel("Intermediate")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Laravel")
            .nameFr("Laravel")
            .nameEs("Laravel")
            .descriptionEn("Laravel - PHP web application framework")
            .descriptionFr("Laravel - Framework d'application web PHP")
            .descriptionEs("Laravel - Framework de aplicaciones web PHP")
            .proficiencyLevel("Intermediate")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Bootstrap")
            .nameFr("Bootstrap")
            .nameEs("Bootstrap")
            .descriptionEn("Bootstrap - CSS framework for responsive design")
            .descriptionFr("Bootstrap - Framework CSS pour la conception réactive")
            .descriptionEs("Bootstrap - Framework CSS para diseño responsivo")
            .proficiencyLevel("Intermediate")
            .category("Development Tools")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("JUnit5")
            .nameFr("JUnit5")
            .nameEs("JUnit5")
            .descriptionEn("JUnit5 - Java testing framework for unit testing")
            .descriptionFr("JUnit5 - Framework de test Java pour les tests unitaires")
            .descriptionEs("JUnit5 - Framework de pruebas Java para pruebas unitarias")
            .proficiencyLevel("Intermediate")
            .category("Development Tools")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Xcode")
            .nameFr("Xcode")
            .nameEs("Xcode")
            .descriptionEn("Xcode - Apple IDE for iOS and macOS development")
            .descriptionFr("Xcode - IDE Apple pour le développement iOS et macOS")
            .descriptionEs("Xcode - IDE de Apple para desarrollo iOS y macOS")
            .proficiencyLevel("Beginner")
            .category("Development Tools")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Vite")
            .nameFr("Vite")
            .nameEs("Vite")
            .descriptionEn("Vite - Fast frontend build tool and dev server")
            .descriptionFr(
                "Vite - Outil de construction frontend rapide et serveur de développement")
            .descriptionEs("Vite - Herramienta de construcción frontend rápida y servidor de desarrollo")
            .proficiencyLevel("Intermediate")
            .category("Development Tools")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Tailwind CSS")
            .nameFr("Tailwind CSS")
            .nameEs("Tailwind CSS")
            .descriptionEn("Tailwind CSS - Utility-first CSS framework")
            .descriptionFr("Tailwind CSS - Framework CSS utilitaire-first")
            .descriptionEs("Tailwind CSS - Framework CSS de utilidades")
            .proficiencyLevel("Intermediate")
            .category("Development Tools")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("REST APIs")
            .nameFr("REST APIs")
            .nameEs("REST APIs")
            .descriptionEn("REST APIs - Representational State Transfer architecture")
            .descriptionFr("REST APIs - Architecture de transfert d'état représentationnel")
            .descriptionEs("REST APIs - Arquitectura de transferencia de estado representacional")
            .proficiencyLevel("Advanced")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Distributed Systems")
            .nameFr("Distributed Systems")
            .nameEs("Sistemas Distribuidos")
            .descriptionEn(
                "Distributed Systems - Design and architecture of distributed applications")
            .descriptionFr(
                "Systèmes Distribués - Conception et architecture d'applications distribuées")
            .descriptionEs("Sistemas Distribuidos - Diseño y arquitectura de aplicaciones distribuidas")
            .proficiencyLevel("Intermediate")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Docker")
            .nameFr("Docker")
            .nameEs("Docker")
            .descriptionEn("Docker - Containerization platform for application deployment")
            .descriptionFr(
                "Docker - Plateforme de conteneurisation pour le déploiement d'applications")
            .descriptionEs("Docker - Plataforma de contenedorización para el despliegue de aplicaciones")
            .proficiencyLevel("Advanced")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Docker Compose")
            .nameFr("Docker Compose")
            .nameEs("Docker Compose")
            .descriptionEn("Docker Compose - Tool for defining multi-container Docker applications")
            .descriptionFr(
                "Docker Compose - Outil de définition d'applications Docker multi-conteneurs")
            .descriptionEs("Docker Compose - Herramienta para definir aplicaciones Docker multi-contenedor")
            .proficiencyLevel("Advanced")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Spring Boot")
            .nameFr("Spring Boot")
            .nameEs("Spring Boot")
            .descriptionEn("Spring Boot - Framework for building Java applications")
            .descriptionFr("Spring Boot - Framework pour la construction d'applications Java")
            .descriptionEs("Spring Boot - Framework para construir aplicaciones Java")
            .proficiencyLevel("Advanced")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Git/GitHub")
            .nameFr("Git/GitHub")
            .nameEs("Git/GitHub")
            .descriptionEn("Git and GitHub - Version control and collaborative development")
            .descriptionFr("Git et GitHub - Contrôle de version et développement collaboratif")
            .descriptionEs("Git y GitHub - Control de versiones y desarrollo colaborativo")
            .proficiencyLevel("Advanced")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("PostgreSQL")
            .nameFr("PostgreSQL")
            .nameEs("PostgreSQL")
            .descriptionEn("PostgreSQL - Advanced open-source relational database")
            .descriptionFr("PostgreSQL - Base de données relationnelle open-source avancée")
            .descriptionEs("PostgreSQL - Base de datos relacional de código abierto avanzada")
            .proficiencyLevel("Advanced")
            .category("Web & Cloud")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Scrum/Agile")
            .nameFr("Scrum/Agile")
            .nameEs("Scrum/Agile")
            .descriptionEn("Scrum and Agile - Software development methodologies")
            .descriptionFr("Scrum et Agile - Méthodologies de développement logiciel")
            .descriptionEs("Scrum y Agile - Metodologías de desarrollo de software")
            .proficiencyLevel("Advanced")
            .category("Work Methods")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Time Management")
            .nameFr("Gestion du Temps")
            .nameEs("Gestión del Tiempo")
            .descriptionEn("Time Management - Organizing and prioritizing work effectively")
            .descriptionFr("Gestion du Temps - Organisation et priorisation du travail efficace")
            .descriptionEs("Gestión del Tiempo - Organización y priorización efectiva del trabajo")
            .proficiencyLevel("Advanced")
            .category("Work Methods")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Problem Solving")
            .nameFr("Résolution de Problèmes")
            .nameEs("Resolución de Problemas")
            .descriptionEn("Problem Solving - Analytical thinking and creative solutions")
            .descriptionFr("Résolution de Problèmes - Pensée analytique et solutions créatives")
            .descriptionEs("Resolución de Problemas - Pensamiento analítico y soluciones creativas")
            .proficiencyLevel("Advanced")
            .category("Work Methods")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Teamwork")
            .nameFr("Travail d'Équipe")
            .nameEs("Trabajo en Equipo")
            .descriptionEn("Teamwork - Collaborating effectively in team environments")
            .descriptionFr(
                "Travail d'Équipe - Collaboration efficace dans les environnements d'équipe")
            .descriptionEs("Trabajo en Equipo - Colaboración efectiva en entornos de equipo")
            .proficiencyLevel("Advanced")
            .category("Work Methods")
            .build());

    skillRepository.save(
        Skill.builder()
            .nameEn("Unity")
            .nameFr("Unity")
            .nameEs("Unity")
            .descriptionEn("Unity - Game development engine")
            .descriptionFr("Unity - Moteur de développement de jeux")
            .descriptionEs("Unity - Motor de desarrollo de juegos")
            .proficiencyLevel("Intermediate")
            .category("Development Tools")
            .build());

    log.info("Completed loading {} skills", skillRepository.count());
  }
}
