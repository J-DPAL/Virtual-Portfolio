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
            .titleEn("OpenHand - MANA Mobile Application")
            .titleFr("OpenHand - Application mobile MANA")
            .titleEs("OpenHand - Aplicacion movil MANA")
            .descriptionEn(
                "Mobile application developed for Maison d'Accueil des Nouveaux Arrivants (MANA) as part of the Champlain College Systems Development final project. Implemented a home page with MANA branding, banners, announcements, and service information. Built navigation using Expo Router with screens for events, authentication, and language settings. Designed a responsive UI for iOS and Android. Configured a Docker-based environment to run the mobile app, backend, and PostgreSQL database. Integrated a WhatsApp contact button with automatic deep-linking. Applied a modular folder structure to support scalability and maintainability.")
            .descriptionFr(
                "Application mobile developpee pour la Maison d'Accueil des Nouveaux Arrivants (MANA) dans le cadre du projet final de Developpement de systemes au College Champlain. Mise en place d'une page d'accueil avec l'image de marque MANA, des bannieres, des annonces et des informations sur les services. Navigation construite avec Expo Router pour les ecrans d'evenements, d'authentification et de langues. Interface reponsive pour iOS et Android. Environnement Docker configure pour l'application mobile, le backend et la base de donnees PostgreSQL. Integration d'un bouton WhatsApp avec lien direct automatique. Structure modulaire pour favoriser la scalabilite et la maintenance.")
            .descriptionEs(
                "Aplicacion movil desarrollada para Maison d'Accueil des Nouveaux Arrivants (MANA) como parte del proyecto final de Desarrollo de Sistemas en Champlain College. Se implemento una pagina de inicio con la marca MANA, banners, anuncios e informacion de servicios. Navegacion creada con Expo Router con pantallas de eventos, autenticacion y configuracion de idioma. Interfaz responsive para iOS y Android. Entorno Docker configurado para la app movil, el backend y la base de datos PostgreSQL. Integracion de un boton de WhatsApp con enlace directo automatico. Estructura modular para escalabilidad y mantenimiento.")
            .technologies(
                "React Native (Expo), TypeScript, Expo Router, Docker, Docker Compose, PostgreSQL, Spring Boot, Node.js")
            .githubUrl("https://github.com/Chronyyx/openhand-mobile-system")
            .startDate(LocalDate.of(2026, 2, 1))
            .endDate(null)
            .status("In Progress")
            .build());

    projectRepository.save(
        Project.builder()
            .titleEn("Champlain PetClinic (CPC) - Scrum Project")
            .titleFr("Champlain PetClinic (CPC) - Projet Scrum")
            .titleEs("Champlain PetClinic (CPC) - Proyecto Scrum")
            .descriptionEn(
                "Agile 6-week simulation of a real IT company environment while developing a web application. Contributed as a Scrum team member and product owner, implementing archive functionality, undo actions, and UX improvements.")
            .descriptionFr(
                "Simulation Agile de 6 semaines d'un environnement d'entreprise TI reel lors du developpement d'une application web. Contribution comme membre d'equipe Scrum et product owner, avec l'implementation d'une fonction d'archivage, d'actions d'annulation et d'ameliorations UX.")
            .descriptionEs(
                "Simulacion Agile de 6 semanas de un entorno real de empresa TI mientras se desarrollaba una aplicacion web. Participacion como miembro del equipo Scrum y product owner, implementando la funcion de archivo, acciones de deshacer y mejoras de UX.")
            .technologies("Java, Spring Boot, JUnit5, React, Jira")
            .githubUrl("https://github.com/cgerard321/champlain_petclinic")
            .startDate(LocalDate.of(2025, 10, 1))
            .endDate(LocalDate.of(2025, 10, 31))
            .status("Completed")
            .build());

    projectRepository.save(
        Project.builder()
            .titleEn("WeatherApp - Full-Stack Weather Application")
            .titleFr("WeatherApp - Application meteo full stack")
            .titleEs("WeatherApp - Aplicacion del clima full stack")
            .descriptionEn(
                "Modern full-stack weather microservices app offering real-time weather, 5-day forecasts, historical range queries, and full CRUD. Implemented Location, Weather, and Data services, integrated OpenCage and OpenWeather APIs, added interactive maps, date-range queries, multi-format export, and Dockerized deployment.")
            .descriptionFr(
                "Application meteo full stack en microservices avec meteo en temps reel, previsions a 5 jours, requetes historiques par plage et CRUD complet. Services Location, Weather et Data, integration des API OpenCage et OpenWeather, cartes interactives, requetes par dates, export multi-format et deploiement Docker.")
            .descriptionEs(
                "Aplicacion del clima full stack en microservicios con clima en tiempo real, pronostico a 5 dias, consultas historicas por rango y CRUD completo. Servicios Location, Weather y Data, integracion de OpenCage y OpenWeather, mapas interactivos, consultas por fechas, exportacion multi-formato y despliegue con Docker.")
            .technologies(
                "React (Vite), Tailwind CSS, FastAPI (Python), PostgreSQL, SQLAlchemy, Docker, Docker Compose")
            .githubUrl("https://github.com/J-DPAL/Weather-App")
            .startDate(LocalDate.of(2025, 10, 1))
            .endDate(LocalDate.of(2025, 10, 31))
            .status("Completed")
            .build());

    projectRepository.save(
        Project.builder()
            .titleEn("System Analysis & Design - Apartment Rental System")
            .titleFr("Analyse et conception - Systeme de location d'appartements")
            .titleEs("Analisis y diseno - Sistema de renta de apartamentos")
            .descriptionEn(
                "Designed a web-based unit rental system for a real-estate company. Produced requirements, UML diagrams, prototypes, and a database schema for a full architecture.")
            .descriptionFr(
                "Conception d'un systeme web de location d'unites pour une entreprise immobiliere. Production des exigences, diagrammes UML, prototypes et schema de base de donnees pour l'architecture complete.")
            .descriptionEs(
                "Diseno de un sistema web de renta de unidades para una empresa inmobiliaria. Se elaboraron requisitos, diagramas UML, prototipos y un esquema de base de datos para la arquitectura completa.")
            .technologies("UML, SQL, FURPS+, UI prototyping tools")
            .startDate(LocalDate.of(2025, 5, 1))
            .endDate(LocalDate.of(2025, 5, 31))
            .status("Completed")
            .build());

    projectRepository.save(
        Project.builder()
            .titleEn("Laravel-Powered Mini E-Commerce Platform")
            .titleFr("Mini plateforme e-commerce avec Laravel")
            .titleEs("Mini plataforma de e-commerce con Laravel")
            .descriptionEn(
                "Built a mini e-commerce web app with browsing, authentication, and order management. Developed CRUD operations, order placement, and an admin dashboard.")
            .descriptionFr(
                "Application web e-commerce miniature avec navigation, authentification et gestion des commandes. Developpement du CRUD, de la prise de commandes et d'un tableau de bord admin.")
            .descriptionEs(
                "Aplicacion web de mini e-commerce con navegacion, autenticacion y gestion de pedidos. Desarrollo de CRUD, registro de pedidos y panel de administracion.")
            .technologies("PHP (Laravel), Bootstrap, Tailwind, MySQL")
            .githubUrl("https://github.com/J-DPAL/Mini-E-Commerce-Platform")
            .startDate(LocalDate.of(2025, 5, 1))
            .endDate(LocalDate.of(2025, 5, 31))
            .status("Completed")
            .build());

    projectRepository.save(
        Project.builder()
            .titleEn("Rustbound - Tower Defense Game")
            .titleFr("Rustbound - Jeu tower defense")
            .titleEs("Rustbound - Juego tower defense")
            .descriptionEn(
                "Team project creating a 3D wave-based tower defense game. Implemented game mechanics including enemy waves, resource management, and currency systems.")
            .descriptionFr(
                "Projet d'equipe pour un jeu 3D de tower defense par vagues. Implementation des mecaniques de jeu incluant vagues d'ennemis, gestion des ressources et systemes de monnaie.")
            .descriptionEs(
                "Proyecto en equipo para un juego 3D de tower defense por oleadas. Implementacion de mecanicas de juego con oleadas enemigas, gestion de recursos y sistemas de moneda.")
            .technologies("Unity, C#, OOP, Game Physics")
            .githubUrl("https://github.com/J-DPAL/TowerDefenseGame")
            .startDate(LocalDate.of(2025, 5, 1))
            .endDate(LocalDate.of(2025, 5, 31))
            .status("Completed")
            .build());

    projectRepository.save(
        Project.builder()
            .titleEn("Parking Finder Mobile App (Kotlin)")
            .titleFr("Application mobile Parking Finder (Kotlin)")
            .titleEs("Aplicacion movil Parking Finder (Kotlin)")
            .descriptionEn(
                "Android app for finding, booking, and paying for parking spots. Designed dashboards, user roles, location and parking searches, and a parking history feature.")
            .descriptionFr(
                "Application Android pour trouver, reserver et payer des places de stationnement. Conception de tableaux de bord, roles utilisateurs, recherche de stationnement et historique.")
            .descriptionEs(
                "Aplicacion Android para encontrar, reservar y pagar estacionamientos. Diseno de paneles, roles de usuario, busquedas de ubicacion y estacionamiento, y un historial.")
            .technologies("Kotlin, Android Studio, Google Maps API, SQLite")
            .githubUrl("https://github.com/josevmorilla/smart-parking-system")
            .startDate(LocalDate.of(2025, 5, 1))
            .endDate(LocalDate.of(2025, 5, 31))
            .status("Completed")
            .build());

    projectRepository.save(
        Project.builder()
            .titleEn("RESTful Microservice Project - Game Asset Marketplace")
            .titleFr("Projet microservices REST - Marche d'actifs de jeux")
            .titleEs("Proyecto microservicios REST - Mercado de activos de juegos")
            .descriptionEn(
                "RESTful microservices system for managing digital game assets. Implemented services for users, assets, and payments with secure integrations.")
            .descriptionFr(
                "Systeme de microservices REST pour gerer des actifs numeriques de jeux. Services pour utilisateurs, actifs et paiements avec integrations securisees.")
            .descriptionEs(
                "Sistema de microservicios REST para gestionar activos digitales de juegos. Servicios de usuarios, activos y pagos con integraciones seguras.")
            .technologies("Java, Spring Boot, Docker, JUnit, REST APIs, Microservices Architecture")
            .githubUrl("https://github.com/J-DPAL/Game-Asset-Market-Place-MS")
            .startDate(LocalDate.of(2025, 5, 1))
            .endDate(LocalDate.of(2025, 5, 31))
            .status("Completed")
            .build());

    projectRepository.save(
        Project.builder()
            .titleEn("Missile Command Game - Arcade-style Defense Game")
            .titleFr("Missile Command - Jeu d'arcade de defense")
            .titleEs("Missile Command - Juego arcade de defensa")
            .descriptionEn(
                "Unity-based recreation of the classic Missile Command arcade game. Features dynamic missile spawning with increasing difficulty, bonus items, explosion and particle effects, defensive shields with limited durability, plus pause/resume and level/score UI.")
            .descriptionFr(
                "Recreation Unity du jeu arcade Missile Command. Lancement dynamique de missiles avec difficulte croissante, bonus, effets d'explosion et de particules, boucliers defensifs a durabilite limitee, et interface pause/reprise et niveau/score.")
            .descriptionEs(
                "Recreacion en Unity del clasico juego arcade Missile Command. Lanzamiento dinamico de misiles con dificultad creciente, bonus, efectos de explosion y particulas, escudos defensivos con durabilidad limitada, y UI de pausa/reanudar y nivel/puntaje.")
            .technologies("Unity Engine, C#, TextMesh Pro, Unity Input System")
            .githubUrl("https://github.com/J-DPAL/Missile-Command-Game")
            .startDate(LocalDate.of(2025, 4, 1))
            .endDate(LocalDate.of(2025, 4, 30))
            .status("Completed")
            .build());

    projectRepository.save(
        Project.builder()
            .titleEn("Plinko Game - Unity Plinko-style Game")
            .titleFr("Plinko - Jeu de type Plinko sous Unity")
            .titleEs("Plinko - Juego tipo Plinko en Unity")
            .descriptionEn(
                "Unity-based Plinko-style game where players drop disks onto a rotating peg board and score points based on the landing bin. Implements rotating board mechanics, disk physics, limited disk count, and scene transitions when disks run out.")
            .descriptionFr(
                "Jeu Unity de type Plinko ou les joueurs lachent des disques sur un plateau de chevilles rotatif et marquent des points selon la zone. Mecanique de rotation, physique des disques, nombre limite de disques et transitions de scene.")
            .descriptionEs(
                "Juego Unity tipo Plinko donde los jugadores sueltan discos en un tablero de clavijas rotativo y puntuan segun el compartimiento. Mecanica de rotacion, fisica de discos, limite de discos y transiciones de escena.")
            .technologies("Unity Engine, C#, TextMesh Pro, Unity 2D Physics (Physics2D)")
            .githubUrl("https://github.com/J-DPAL/Plinko-Game")
            .startDate(LocalDate.of(2025, 3, 1))
            .endDate(LocalDate.of(2025, 3, 31))
            .status("Completed")
            .build());

    projectRepository.save(
        Project.builder()
            .titleEn("LikeAHolic Social App - Full Stack Web Application")
            .titleFr("LikeAHolic - Application web full stack")
            .titleEs("LikeAHolic - Aplicacion web full stack")
            .descriptionEn(
                "Social media app with user-friendly post publishing and viewing. Developed backend REST services and integrated them with a responsive ReactJS frontend with teammates.")
            .descriptionFr(
                "Application reseau social avec publication et consultation conviviales. Developpement de services REST backend et integration avec un frontend ReactJS reactif en equipe.")
            .descriptionEs(
                "Aplicacion de red social con publicacion y visualizacion amigables. Desarrollo de servicios REST backend e integracion con un frontend ReactJS responsivo junto con el equipo.")
            .technologies("ReactJS, Spring Boot, SQL (H2/SQL DB), DTO/ResponseEntity")
            .startDate(LocalDate.of(2024, 12, 1))
            .endDate(LocalDate.of(2024, 12, 31))
            .status("Completed")
            .build());

    log.info("Completed loading {} projects", projectRepository.count());
  }
}
