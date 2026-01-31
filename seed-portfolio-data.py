#!/usr/bin/env python3
"""
Portfolio Data Seeding Script
Populates the portfolio database with CV information for Jean-David Pallares
"""

import requests
import json
from datetime import datetime

# Configuration
BASE_URL = "http://localhost:8080/api"
HEADERS = {"Content-Type": "application/json"}

# Authentication token (would need to be obtained via login)
# For now, this assumes public endpoints or that auth is bypassed for localhost

def seed_experiences():
    """Add work experiences to the database"""
    experiences = [
        {
            "companyNameEn": "Juliette & Chocolat",
            "companyNameFr": "Juliette & Chocolat",
            "positionEn": "Cook",
            "positionFr": "Cuisinier",
            "descriptionEn": "Prepared and assembled dishes in a fast-paced, demanding environment. Developed time management skills, adherence to quality standards, and teamwork.",
            "descriptionFr": "Préparation et assemblage de plats dans un environnement rapide et exigeant. Développement des compétences en gestion du temps, respect des normes de qualité et travail en équipe.",
            "locationEn": "Montreal, QC",
            "locationFr": "Montréal, QC",
            "startDate": "2023-06-01",
            "endDate": None,
            "isCurrent": False
        },
        {
            "companyNameEn": "Élections Québec",
            "companyNameFr": "Élections Québec",
            "positionEn": "Voter Attendant",
            "positionFr": "Préposé aux électeurs",
            "descriptionEn": "Welcomed voters and verified identification, ensuring confidentiality and adherence to procedures. Demonstrated reliability and professionalism in a high-pressure environment.",
            "descriptionFr": "Accueil des électeurs et vérification de l'identification, garantissant la confidentialité et le respect des procédures. Démonstration de fiabilité et de professionnalisme dans un environnement haute pression.",
            "locationEn": "Quebec, QC",
            "locationFr": "Québec, QC",
            "startDate": "2022-10-01",
            "endDate": "2022-10-31",
            "isCurrent": False
        }
    ]
    
    print("Seeding Work Experiences...")
    for exp in experiences:
        try:
            response = requests.post(f"{BASE_URL}/v1/experience", json=exp, headers=HEADERS)
            if response.status_code in [200, 201]:
                print(f"✓ Added experience: {exp['positionEn']}")
            else:
                print(f"✗ Failed to add {exp['positionEn']}: {response.status_code} - {response.text}")
        except Exception as e:
            print(f"✗ Error adding {exp['positionEn']}: {str(e)}")

def seed_education():
    """Add education entries to the database"""
    education_entries = [
        {
            "institutionNameEn": "Champlain College",
            "institutionNameFr": "Champlain College",
            "degreeEn": "DEC in Computer Science Technology",
            "degreeFr": "DEC en Technologie de l'informatique",
            "fieldOfStudyEn": "Computer Science Technology",
            "fieldOfStudyFr": "Technologie de l'informatique",
            "descriptionEn": "Passionate about cybersecurity, software development, and emerging technologies. Strengthening cloud-native development, backend architecture, and secure application deployment skills through hands-on projects.",
            "descriptionFr": "Passionné par la cybersécurité, le développement logiciel et les technologies émergentes. Renforcement des compétences en développement cloud-native, architecture backend et déploiement sécurisé d'applications.",
            "startDate": "2023-09-01",
            "endDate": "2026-05-31"
        },
        {
            "institutionNameEn": "Collège Charles-Lemoyne",
            "institutionNameFr": "Collège Charles-Lemoyne",
            "degreeEn": "High School Diploma",
            "degreeFr": "Diplôme d'études secondaires",
            "fieldOfStudyEn": "General Studies",
            "fieldOfStudyFr": "Études générales",
            "descriptionEn": "High school diploma completed in 2023",
            "descriptionFr": "Diplôme d'études secondaires obtenu en 2023",
            "startDate": "2019-09-01",
            "endDate": "2023-06-30"
        }
    ]
    
    print("\nSeeding Education...")
    for edu in education_entries:
        try:
            response = requests.post(f"{BASE_URL}/v1/education", json=edu, headers=HEADERS)
            if response.status_code in [200, 201]:
                print(f"✓ Added education: {edu['degreeEn']}")
            else:
                print(f"✗ Failed to add {edu['degreeEn']}: {response.status_code}")
        except Exception as e:
            print(f"✗ Error adding {edu['degreeEn']}: {str(e)}")

def seed_projects():
    """Add projects to the database"""
    projects = [
        {
            "titleEn": "OpenHand – MANA Mobile Application",
            "titleFr": "OpenHand – Application Mobile MANA",
            "descriptionEn": "Mobile application developed for Maison d'Accueil des Nouveaux Arrivants (MANA) as part of the Champlain College Systems Development final project. Implemented a home page with MANA branding, banners, announcements, and service information. Built navigation using Expo Router with screens for events, authentication, and language settings. Designed a responsive UI supporting both iOS and Android platforms. Configured a Docker-based environment to run the mobile app, backend, and PostgreSQL database. Integrated a WhatsApp contact button with automatic deep-linking for direct communication. Applied a modular folder structure to support scalability and long-term maintainability.",
            "descriptionFr": "Application mobile développée pour Maison d'Accueil des Nouveaux Arrivants (MANA) dans le cadre du projet final Développement de systèmes du Collège Champlain. Mise en œuvre d'une page d'accueil avec image de marque MANA, bannières, annonces et informations sur les services. Navigation construite à l'aide d'Expo Router avec des écrans pour événements, authentification et paramètres de langue. Interface utilisateur réactive conçue pour prendre en charge les plates-formes iOS et Android. Configuration d'un environnement Docker pour exécuter l'application mobile, le backend et la base de données PostgreSQL. Intégration d'un bouton de contact WhatsApp avec deep-linking automatique pour la communication directe. Structure de dossier modulaire appliquée pour prendre en charge la scalabilité et la maintenabilité à long terme.",
            "technologies": "React Native,Expo,TypeScript,Expo Router,Docker,Docker Compose,PostgreSQL,Spring Boot,Node.js",
            "startDate": "2026-02-01",
            "endDate": None,
            "status": "In Progress"
        },
        {
            "titleEn": "Champlain PetClinic (CPC)",
            "titleFr": "Champlain PetClinic (CPC)",
            "descriptionEn": "Agile 6-week simulation of a real IT company environment while developing a web application. Contributed as Scrum team member and product owner; implemented archive functionality, undo actions, and UX improvements.",
            "descriptionFr": "Simulation Agile sur 6 semaines d'un environnement réel d'entreprise informatique lors du développement d'une application Web. Contribution en tant que membre d'équipe Scrum et propriétaire de produit ; implémentation de la fonctionnalité d'archivage, des actions d'annulation et des améliorations UX.",
            "technologies": "Java,Spring Boot,JUnit5,React,Jira",
            "startDate": "2025-10-01",
            "endDate": "2025-12-15",
            "status": "Completed"
        }
    ]
    
    print("\nSeeding Projects...")
    for proj in projects:
        try:
            response = requests.post(f"{BASE_URL}/v1/projects", json=proj, headers=HEADERS)
            if response.status_code in [200, 201]:
                print(f"✓ Added project: {proj['titleEn']}")
            else:
                print(f"✗ Failed to add {proj['titleEn']}: {response.status_code}")
        except Exception as e:
            print(f"✗ Error adding {proj['titleEn']}: {str(e)}")

def seed_skills():
    """Add skills to the database"""
    skills = [
        # Programming Languages
        {
            "nameEn": "Java",
            "nameFr": "Java",
            "descriptionEn": "Object-oriented programming language used for backend development and enterprise applications",
            "descriptionFr": "Langage de programmation orienté objet utilisé pour le développement backend et les applications d'entreprise",
            "category": "Programming Languages",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 3
        },
        {
            "nameEn": "Python",
            "nameFr": "Python",
            "descriptionEn": "Versatile programming language for scripting, automation, and data processing",
            "descriptionFr": "Langage de programmation polyvalent pour les scripts, l'automatisation et le traitement des données",
            "category": "Programming Languages",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "JavaScript",
            "nameFr": "JavaScript",
            "descriptionEn": "Core language for web development, used for frontend and backend with Node.js",
            "descriptionFr": "Langage fondamental du développement web, utilisé pour le frontend et le backend avec Node.js",
            "category": "Programming Languages",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 3
        },
        {
            "nameEn": "TypeScript",
            "nameFr": "TypeScript",
            "descriptionEn": "Superset of JavaScript that adds static typing and enhanced tooling",
            "descriptionFr": "Sur-ensemble de JavaScript qui ajoute le typage statique et des outils améliorés",
            "category": "Programming Languages",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "SQL",
            "nameFr": "SQL",
            "descriptionEn": "Language for managing relational databases",
            "descriptionFr": "Langage pour gérer les bases de données relationnelles",
            "category": "Programming Languages",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 3
        },
        {
            "nameEn": "C#",
            "nameFr": "C#",
            "descriptionEn": "Modern programming language for .NET framework development",
            "descriptionFr": "Langage de programmation moderne pour le développement du framework .NET",
            "category": "Programming Languages",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 1
        },
        {
            "nameEn": "Kotlin",
            "nameFr": "Kotlin",
            "descriptionEn": "Modern programming language for Android development",
            "descriptionFr": "Langage de programmation moderne pour le développement Android",
            "category": "Programming Languages",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 1
        },
        {
            "nameEn": "Swift",
            "nameFr": "Swift",
            "descriptionEn": "Programming language for iOS and macOS application development",
            "descriptionFr": "Langage de programmation pour le développement d'applications iOS et macOS",
            "category": "Programming Languages",
            "proficiencyLevel": "Beginner",
            "yearsOfExperience": None
        },
        {
            "nameEn": "HTML/CSS",
            "nameFr": "HTML/CSS",
            "descriptionEn": "Markup and styling languages for web development",
            "descriptionFr": "Langages de balisage et de style pour le développement web",
            "category": "Programming Languages",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 3
        },
        {
            "nameEn": "PHP",
            "nameFr": "PHP",
            "descriptionEn": "Server-side scripting language, specifically Laravel framework",
            "descriptionFr": "Langage de script côté serveur, spécifiquement framework Laravel",
            "category": "Programming Languages",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 2
        },
        # Frameworks & Libraries
        {
            "nameEn": "React",
            "nameFr": "React",
            "descriptionEn": "JavaScript library for building user interfaces with component-based architecture",
            "descriptionFr": "Bibliothèque JavaScript pour créer des interfaces utilisateur avec architecture basée sur composants",
            "category": "Frontend Frameworks",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "React Native",
            "nameFr": "React Native",
            "descriptionEn": "Framework for building cross-platform mobile applications",
            "descriptionFr": "Framework pour créer des applications mobiles multiplateformes",
            "category": "Mobile Frameworks",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 1
        },
        {
            "nameEn": "Spring Boot",
            "nameFr": "Spring Boot",
            "descriptionEn": "Framework for building Java microservices and backend applications",
            "descriptionFr": "Framework pour créer des microservices Java et des applications backend",
            "category": "Backend Frameworks",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "Angular",
            "nameFr": "Angular",
            "descriptionEn": "TypeScript-based framework for building large-scale web applications",
            "descriptionFr": "Framework basé sur TypeScript pour créer des applications web à grande échelle",
            "category": "Frontend Frameworks",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 1
        },
        {
            "nameEn": "Laravel",
            "nameFr": "Laravel",
            "descriptionEn": "PHP framework for web application development",
            "descriptionFr": "Framework PHP pour le développement d'applications web",
            "category": "Backend Frameworks",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "Tailwind CSS",
            "nameFr": "Tailwind CSS",
            "descriptionEn": "Utility-first CSS framework for rapid UI development",
            "descriptionFr": "Framework CSS axé sur les utilitaires pour développement rapide d'interface utilisateur",
            "category": "Frontend Frameworks",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "Bootstrap",
            "nameFr": "Bootstrap",
            "descriptionEn": "Popular CSS framework for responsive web design",
            "descriptionFr": "Framework CSS populaire pour la conception web réactive",
            "category": "Frontend Frameworks",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "FastAPI",
            "nameFr": "FastAPI",
            "descriptionEn": "Modern Python framework for building fast APIs",
            "descriptionFr": "Framework Python moderne pour créer des API rapides",
            "category": "Backend Frameworks",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 1
        },
        # Tools & Technologies
        {
            "nameEn": "Docker",
            "nameFr": "Docker",
            "descriptionEn": "Container technology for packaging and deploying applications",
            "descriptionFr": "Technologie de conteneurisation pour l'empaquetage et le déploiement d'applications",
            "category": "DevOps",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "Docker Compose",
            "nameFr": "Docker Compose",
            "descriptionEn": "Tool for defining and running multi-container Docker applications",
            "descriptionFr": "Outil pour définir et exécuter des applications Docker multi-conteneurs",
            "category": "DevOps",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "PostgreSQL",
            "nameFr": "PostgreSQL",
            "descriptionEn": "Advanced open-source relational database system",
            "descriptionFr": "Système de gestion de base de données relationnel open-source avancé",
            "category": "Databases",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "Git/GitHub",
            "nameFr": "Git/GitHub",
            "descriptionEn": "Version control system and collaborative development platform",
            "descriptionFr": "Système de contrôle de version et plateforme de développement collaboratif",
            "category": "Development Tools",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 3
        },
        {
            "nameEn": "REST APIs",
            "nameFr": "REST APIs",
            "descriptionEn": "Architectural style for building networked applications",
            "descriptionFr": "Style architectural pour créer des applications en réseau",
            "category": "Web Services",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "IntelliJ IDEA",
            "nameFr": "IntelliJ IDEA",
            "descriptionEn": "Powerful IDE for Java and other JVM languages",
            "descriptionFr": "IDE puissant pour Java et autres langages JVM",
            "category": "Development Tools",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 3
        },
        {
            "nameEn": "Visual Studio Code",
            "nameFr": "Visual Studio Code",
            "descriptionEn": "Lightweight code editor for web development",
            "descriptionFr": "Éditeur de code léger pour le développement web",
            "category": "Development Tools",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 3
        },
        {
            "nameEn": "Postman",
            "nameFr": "Postman",
            "descriptionEn": "Tool for testing and documenting APIs",
            "descriptionFr": "Outil pour tester et documenter les API",
            "category": "Development Tools",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "JUnit5",
            "nameFr": "JUnit5",
            "descriptionEn": "Testing framework for Java applications",
            "descriptionFr": "Framework de test pour les applications Java",
            "category": "Testing",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "Scrum/Agile",
            "nameFr": "Scrum/Agile",
            "descriptionEn": "Agile methodology and Scrum framework for project management",
            "descriptionFr": "Méthodologie Agile et framework Scrum pour la gestion de projet",
            "category": "Methodologies",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 2
        },
        {
            "nameEn": "Jira",
            "nameFr": "Jira",
            "descriptionEn": "Project management and issue tracking tool",
            "descriptionFr": "Outil de gestion de projet et de suivi des problèmes",
            "category": "Development Tools",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 1
        },
        {
            "nameEn": "Android Studio",
            "nameFr": "Android Studio",
            "descriptionEn": "Official IDE for Android application development",
            "descriptionFr": "IDE officiel pour le développement d'applications Android",
            "category": "Development Tools",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 1
        },
        {
            "nameEn": "Xcode",
            "nameFr": "Xcode",
            "descriptionEn": "IDE for iOS and macOS application development",
            "descriptionFr": "IDE pour le développement d'applications iOS et macOS",
            "category": "Development Tools",
            "proficiencyLevel": "Beginner",
            "yearsOfExperience": None
        },
        {
            "nameEn": "Unity",
            "nameFr": "Unity",
            "descriptionEn": "Game engine for 2D and 3D game development",
            "descriptionFr": "Moteur de jeu pour le développement de jeux 2D et 3D",
            "category": "Game Development",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 1
        },
        {
            "nameEn": "Vite",
            "nameFr": "Vite",
            "descriptionEn": "Next generation frontend build tool",
            "descriptionFr": "Outil de construction frontend de nouvelle génération",
            "category": "Build Tools",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 1
        },
        {
            "nameEn": "Expo",
            "nameFr": "Expo",
            "descriptionEn": "Framework for building React Native applications",
            "descriptionFr": "Framework pour créer des applications React Native",
            "category": "Mobile Frameworks",
            "proficiencyLevel": "Advanced",
            "yearsOfExperience": 1
        },
        {
            "nameEn": ".NET",
            "nameFr": ".NET",
            "descriptionEn": "Cross-platform framework for building applications with C#",
            "descriptionFr": "Framework multi-plateforme pour créer des applications avec C#",
            "category": "Backend Frameworks",
            "proficiencyLevel": "Intermediate",
            "yearsOfExperience": 1
        }
    ]
    
    print("\nSeeding Skills...")
    for skill in skills:
        try:
            response = requests.post(f"{BASE_URL}/v1/skills", json=skill, headers=HEADERS)
            if response.status_code in [200, 201]:
                print(f"✓ Added skill: {skill['nameEn']}")
            else:
                print(f"✗ Failed to add {skill['nameEn']}: {response.status_code}")
        except Exception as e:
            print(f"✗ Error adding {skill['nameEn']}: {str(e)}")

def main():
    print("=" * 60)
    print("Portfolio Data Seeding Script")
    print("=" * 60)
    print(f"Base URL: {BASE_URL}\n")
    
    # Seed all data
    seed_experiences()
    seed_education()
    seed_projects()
    seed_skills()
    
    print("\n" + "=" * 60)
    print("Data seeding completed!")
    print("=" * 60)

if __name__ == "__main__":
    main()
