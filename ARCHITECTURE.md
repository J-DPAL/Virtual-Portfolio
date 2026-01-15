# Virtual Portfolio - Architecture Diagrams

## System Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           CLIENT APPLICATIONS                               │
│  ┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐    │
│  │   Web Browser    │     │   Mobile App     │     │   Admin Panel    │    │
│  │   (http://      │     │   (optional)     │     │   (http://      │    │
│  │   localhost:3000)│     │                  │     │   localhost:3000)│    │
│  └────────┬─────────┘     └──────────────────┘     └──────┬───────────┘    │
└───────────┼──────────────────────────────────────────────┼─────────────────┘
            │                                              │
            └──────────────────┬───────────────────────────┘
                               │ HTTP/HTTPS
                               ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                    DOCKER COMPOSE NETWORK                                   │
│                      portfolio-network                                       │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │ FRONTEND CONTAINER (Node.js)                                         │   │
│  │  Port: 3000                                                          │   │
│  │  ┌────────────────────────────────────────────────────────────────┐  │   │
│  │  │  React Application (Vite)                                      │  │   │
│  │  │  ├── App.jsx (Router)                                         │  │   │
│  │  │  ├── components/                                              │  │   │
│  │  │  │   ├── Header, Footer, Navigation                          │  │   │
│  │  │  │   ├── Admin components (Login, Dashboard)                 │  │   │
│  │  │  │   └── Public components (Skills, Projects, etc.)          │  │   │
│  │  │  ├── context/ (Auth, Language)                               │  │   │
│  │  │  ├── services/ (API client)                                  │  │   │
│  │  │  └── locales/ (i18n translations)                            │  │   │
│  │  │                                                               │  │   │
│  │  │  Styling: Tailwind CSS                                       │  │   │
│  │  │  Auth: Context API + JWT                                     │  │   │
│  │  │  i18n: English & French                                      │  │   │
│  │  └────────────────────────────────────────────────────────────────┘  │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                  │                                            │
│                                  │ API Calls (axios)                          │
│                                  ▼                                            │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │ BACKEND CONTAINER (Java/Spring Boot)                                │   │
│  │  Port: 8080                                                         │   │
│  │  ┌────────────────────────────────────────────────────────────────┐ │   │
│  │  │  Presentation Layer (Controllers)                             │ │   │
│  │  │  ├── AuthController                                           │ │   │
│  │  │  └── /api/v1/auth/... endpoints                              │ │   │
│  │  │                        ▲                                       │ │   │
│  │  │                        │                                       │ │   │
│  │  │ ┌──────────────────────┴──────────────────────┐              │ │   │
│  │  │ │  Mapping Layer (DTOs & Mappers)            │              │ │   │
│  │  │ ├── UserDTO, LoginRequest, LoginResponse     │              │ │   │
│  │  │ └── UserMapper (Entity ↔ DTO conversion)     │              │ │   │
│  │  │                        ▲                       │              │ │   │
│  │  │                        │                       │              │ │   │
│  │  │ ┌──────────────────────┴──────────────────────┐              │ │   │
│  │  │ │  Business Logic Layer (Services)           │              │ │   │
│  │  │ └── AuthService (authenticate, validate)     │              │ │   │
│  │  │                        ▲                       │              │ │   │
│  │  │                        │                       │              │ │   │
│  │  │ ┌──────────────────────┴──────────────────────┐              │ │   │
│  │  │ │  Data Access Layer (Repositories)          │              │ │   │
│  │  │ └── UserRepository (CRUD operations)         │              │ │   │
│  │  │                        ▲                       │              │ │   │
│  │  │                        │                       │              │ │   │
│  │  │ ┌──────────────────────┴──────────────────────┐              │ │   │
│  │  │ │  Entities (JPA Models)                      │              │ │   │
│  │  │ └── User (@Entity, @Table)                   │              │ │   │
│  │  │                                               │              │ │   │
│  │  │                                               │              │ │   │
│  │  │ Cross-Cutting Concerns:                      │              │ │   │
│  │  │ ├── Security (JWT, BCrypt)                   │              │ │   │
│  │  │ ├── Exception Handling                       │              │ │   │
│  │  │ ├── Configuration                           │              │ │   │
│  │  │ └── Validation                              │              │ │   │
│  │  └────────────────────────────────────────────────┘ │           │ │   │
│  └──────────────────────────────────────────────────────┘           │ │   │
│                                  │                                    │ │   │
│                                  │ JDBC                               │ │   │
│                                  ▼                                    │ │   │
│  ┌──────────────────────────────────────────────────────────────────┐ │   │
│  │ DATABASE CONTAINER (PostgreSQL)                                 │ │   │
│  │  Port: 5432                                                     │ │   │
│  │  ┌────────────────────────────────────────────────────────────┐ │ │   │
│  │  │  virtual_portfolio Database                               │ │ │   │
│  │  │  Tables: (to be implemented)                              │ │ │   │
│  │  │  ├── users (admin user created at init)                  │ │ │   │
│  │  │  ├── skills                                              │ │ │   │
│  │  │  ├── projects                                            │ │ │   │
│  │  │  ├── experiences                                         │ │ │   │
│  │  │  ├── education                                           │ │ │   │
│  │  │  ├── hobbies                                             │ │ │   │
│  │  │  ├── messages (contact form submissions)                │ │ │   │
│  │  │  ├── testimonials                                        │ │ │   │
│  │  │  └── contact_info                                        │ │ │   │
│  │  │                                                           │ │ │   │
│  │  │  Volume: postgres_data (persistent storage)             │ │ │   │
│  │  └────────────────────────────────────────────────────────┘ │ │   │
│  └──────────────────────────────────────────────────────────────┘ │   │
│                                                                      │   │
└──────────────────────────────────────────────────────────────────────┘   │
```

---

## Backend Microservices Architecture

```
                        HTTP Request
                             │
                             ▼
                  ┌─────────────────────┐
                  │  CORS Configuration │ (Security Config)
                  └──────────┬──────────┘
                             │
                             ▼
              ┌──────────────────────────────┐
              │  @RestController             │
              │  AuthController              │
              │  - POST /v1/auth/login       │
              │  - GET /v1/auth/health       │
              └──────────────┬───────────────┘
                             │
                             ▼
                  ┌─────────────────────┐
                  │  Validation         │
                  │  (@Valid)           │
                  └──────────┬──────────┘
                             │
                             ▼
              ┌──────────────────────────────┐
              │  @Service                    │
              │  AuthService                 │
              │  - login()                   │
              │  - validateCredentials()     │
              └──────────────┬───────────────┘
                             │
              ┌──────────────┼──────────────┐
              │              │              │
              ▼              ▼              ▼
      ┌─────────────┐  ┌──────────────┐  ┌──────────────┐
      │  User       │  │ JWT Token    │  │ Password     │
      │  Repository │  │ Provider     │  │ Encoder      │
      │  (JPA)      │  │ (JWT Logic)  │  │ (BCrypt)     │
      └──────┬──────┘  └──────┬───────┘  └──────┬───────┘
             │                │                 │
             ▼                │                 │
      ┌─────────────┐         │                 │
      │  Database   │         │                 │
      │  Query      │         │                 │
      └──────┬──────┘         │                 │
             │        ┌────────┴─────────┐      │
             │        ▼                  ▼      │
             │   ┌────────────┐   ┌──────────┐ │
             │   │ Generate   │   │ Hash     │ │
             │   │ JWT Token  │   │ Password │ │
             │   └────────────┘   └──────────┘ │
             │                                 │
             └─────────────┬───────────────────┘
                           │
                           ▼
              ┌──────────────────────────────┐
              │  LoginResponse               │
              │  - token                     │
              │  - user (UserDTO)            │
              │  - message                   │
              └──────────────┬───────────────┘
                             │
                             ▼
                  ┌─────────────────────┐
                  │  Error Handler      │ (if error)
                  │  GlobalException    │
                  │  Handler            │
                  └──────────┬──────────┘
                             │
                             ▼
                       HTTP Response
```

---

## Data Flow: Login Process

```
Client (React)                Backend (Spring Boot)         Database (PostgreSQL)
    │                              │                              │
    │  1. Submit Login Form         │                              │
    │  (Email + Password)           │                              │
    ├─────────────────────────────>│                              │
    │                              │                              │
    │                              │  2. Validate Input           │
    │                              │  (@Valid annotation)        │
    │                              │                              │
    │                              │  3. Find User by Email      │
    │                              ├─────────────────────────────>│
    │                              │                              │
    │                              │<─────────────────────────────┤
    │                              │  4. Return User Entity       │
    │                              │  (with hashed password)      │
    │                              │                              │
    │                              │  5. Compare Passwords       │
    │                              │  (BCrypt.matches)           │
    │                              │                              │
    │                              │  6. Generate JWT Token      │
    │                              │  (if credentials valid)     │
    │                              │                              │
    │  7. Return LoginResponse      │                              │
    │  (token + user data)          │                              │
    │<─────────────────────────────┤                              │
    │                              │                              │
    │  8. Store Token              │                              │
    │  (localStorage)              │                              │
    │                              │                              │
    │  9. Redirect to Dashboard    │                              │
    │                              │                              │
```

---

## File Organization

```
virtual-portfolio/
│
├── FRONTEND (React/Vite)
│   ├── src/
│   │   ├── components/
│   │   │   ├── common/
│   │   │   │   ├── Header.jsx      (Navigation & Language Switch)
│   │   │   │   └── Footer.jsx      (Footer with year)
│   │   │   │
│   │   │   ├── public/
│   │   │   │   ├── Skills.jsx      (To be implemented)
│   │   │   │   ├── Projects.jsx    (To be implemented)
│   │   │   │   └── ...
│   │   │   │
│   │   │   └── admin/
│   │   │       ├── AdminLogin.jsx     (Login form)
│   │   │       └── AdminDashboard.jsx (Dashboard)
│   │   │
│   │   ├── pages/
│   │   │   ├── HomePage.jsx
│   │   │   └── NotFoundPage.jsx    (To be implemented)
│   │   │
│   │   ├── context/
│   │   │   ├── AuthContext.jsx    (Authentication state)
│   │   │   ├── LanguageContext.jsx (Language state)
│   │   │   └── DataContext.jsx    (App data - To be implemented)
│   │   │
│   │   ├── services/
│   │   │   ├── apiClient.js       (Axios instance with interceptors)
│   │   │   ├── authService.js     (Auth API calls)
│   │   │   └── portfolioService.js (Portfolio API calls - To be implemented)
│   │   │
│   │   ├── hooks/
│   │   │   ├── useAuth.js         (Custom auth hook - To be implemented)
│   │   │   ├── useLanguage.js     (Custom language hook - To be implemented)
│   │   │   └── useApi.js          (Custom API hook - To be implemented)
│   │   │
│   │   ├── locales/
│   │   │   ├── en.json            (English translations)
│   │   │   └── fr.json            (French translations)
│   │   │
│   │   ├── utils/
│   │   │   ├── validators.js      (Form validators)
│   │   │   └── formatters.js      (Data formatters)
│   │   │
│   │   ├── index.css              (Tailwind CSS imports)
│   │   ├── i18n.js                (i18next configuration)
│   │   ├── App.jsx                (Main router component)
│   │   └── main.jsx               (React DOM render)
│   │
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js
│   ├── tailwind.config.cjs
│   ├── postcss.config.cjs
│   ├── .eslintrc.json
│   ├── .gitignore
│   └── Dockerfile
│
├── BACKEND (Java/Spring Boot)
│   ├── src/main/java/com/portfolio/
│   │   ├── api/
│   │   │   └── PortfolioApplication.java
│   │   │
│   │   ├── dataAccessLayer/
│   │   │   ├── entity/
│   │   │   │   ├── BaseEntity.java    (Common fields)
│   │   │   │   └── User.java          (User entity)
│   │   │   └── repository/
│   │   │       └── UserRepository.java (JPA repository)
│   │   │
│   │   ├── businessLogicLayer/
│   │   │   └── service/
│   │   │       ├── AuthService.java   (Authentication logic)
│   │   │       └── PortfolioService.java (To be implemented)
│   │   │
│   │   ├── presentationLayer/
│   │   │   └── controller/
│   │   │       ├── AuthController.java (Auth endpoints)
│   │   │       └── PortfolioController.java (To be implemented)
│   │   │
│   │   ├── mappingLayer/
│   │   │   ├── dto/
│   │   │   │   ├── UserDTO.java
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── LoginResponse.java
│   │   │   │   └── (More DTOs - To be implemented)
│   │   │   └── mapper/
│   │   │       ├── UserMapper.java
│   │   │       └── (More mappers - To be implemented)
│   │   │
│   │   ├── security/
│   │   │   ├── JwtTokenProvider.java   (JWT logic)
│   │   │   └── SecurityConfig.java     (Spring Security config)
│   │   │
│   │   ├── exception/
│   │   │   ├── ResourceNotFoundException.java
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   └── ErrorResponse.java
│   │   │
│   │   └── utils/
│   │       └── (Utility classes)
│   │
│   ├── src/main/resources/
│   │   ├── application.yml        (Main config)
│   │   └── application-dev.yml    (Dev config)
│   │
│   ├── pom.xml
│   ├── .gitignore
│   └── Dockerfile
│
├── DATABASE
│   └── init.sql                   (Schema & seed data)
│
├── docker-compose.yml             (Service orchestration)
├── .env                           (Dev environment variables)
├── .env.production                (Production variables)
├── .gitignore
├── README.md
├── SETUP.md
├── QUICKSTART.md
├── COMMANDS.md
├── PROJECT_SUMMARY.md
├── Makefile
└── run.bat
```

---

## Request-Response Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                    COMPLETE REQUEST FLOW                        │
└─────────────────────────────────────────────────────────────────┘

1. USER INTERACTION
   ┌──────────────────────────┐
   │ User opens Login Page     │
   │ (React Component)         │
   └──────────────┬────────────┘
                  │
                  ▼
2. USER INPUTS DATA
   ┌──────────────────────────┐
   │ Enters email & password  │
   │ Clicks Submit            │
   └──────────────┬────────────┘
                  │
                  ▼
3. FRONTEND VALIDATION
   ┌──────────────────────────┐
   │ Form validation          │
   │ (Email format, length)   │
   └──────────────┬────────────┘
                  │
                  ▼
4. API CALL
   ┌──────────────────────────┐
   │ axios.post()             │
   │ to backend endpoint       │
   │ with credentials         │
   └──────────────┬────────────┘
                  │
                  │ HTTP POST to
                  │ localhost:8080/api/v1/auth/login
                  │
                  ▼
5. BACKEND RECEIVES REQUEST
   ┌──────────────────────────┐
   │ AuthController catches   │
   │ the request              │
   └──────────────┬────────────┘
                  │
                  ▼
6. VALIDATION
   ┌──────────────────────────┐
   │ @Valid annotation        │
   │ validates DTOs           │
   └──────────────┬────────────┘
                  │
                  ▼
7. BUSINESS LOGIC
   ┌──────────────────────────┐
   │ AuthService.login()      │
   │ validates credentials    │
   └──────────────┬────────────┘
                  │
                  ▼
8. DATABASE QUERY
   ┌──────────────────────────┐
   │ UserRepository           │
   │ finds user by email      │
   └──────────────┬────────────┘
                  │
                  │ JDBC Query
                  ▼
   ┌──────────────────────────┐
   │ PostgreSQL Database      │
   │ returns User data        │
   └──────────────┬────────────┘
                  │
                  ▼
9. PASSWORD VERIFICATION
   ┌──────────────────────────┐
   │ BCrypt.matches()         │
   │ compares passwords       │
   └──────────────┬────────────┘
                  │
         ┌────────┴────────┐
         │                 │
    Valid ▼            Invalid ▼
   ┌──────────────┐  ┌──────────────┐
   │ Generate JWT │  │ Throw Error  │
   └──────┬───────┘  └──────┬───────┘
          │                 │
          ▼                 ▼
10. RETURN RESPONSE       ERROR RESPONSE
   ┌──────────────────┐  ┌──────────────────┐
   │ LoginResponse    │  │ Error            │
   │ - token          │  │ - status         │
   │ - user           │  │ - message        │
   │ - message        │  └────────┬─────────┘
   └────────┬─────────┘           │
            │                     │
            └────────────┬────────┘
                         │ HTTP Response
                         ▼
11. FRONTEND RECEIVES RESPONSE
    ┌──────────────────────────────┐
    │ Check for errors             │
    │ If error: show error message │
    │ If success: store token      │
    └──────────────┬───────────────┘
                   │
                   ▼
12. STORE TOKEN
    ┌──────────────────────────────┐
    │ localStorage.setItem(        │
    │   'authToken', token)        │
    └──────────────┬───────────────┘
                   │
                   ▼
13. UPDATE AUTH CONTEXT
    ┌──────────────────────────────┐
    │ AuthContext.login()          │
    │ sets user & token state      │
    └──────────────┬───────────────┘
                   │
                   ▼
14. REDIRECT TO DASHBOARD
    ┌──────────────────────────────┐
    │ useNavigate to               │
    │ /admin/dashboard             │
    └──────────────┬───────────────┘
                   │
                   ▼
15. DISPLAY DASHBOARD
    ┌──────────────────────────────┐
    │ User sees admin panel        │
    │ Dashboard loads successfully │
    └──────────────────────────────┘
```

---

## Technology Interaction Diagram

```
┌─────────────────────────────────────────────────────────┐
│              REACT FRONTEND (Vite)                      │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  ┌─────────────────────────────────────────────────┐   │
│  │  React Components & Pages                       │   │
│  │  - Rendered with JSX                            │   │
│  │  - State managed by Context API                 │   │
│  │  - Styled with Tailwind CSS                     │   │
│  └──────────────┬──────────────────────────────────┘   │
│                 │                                       │
│                 │  (axios)                              │
│                 ▼                                       │
│  ┌─────────────────────────────────────────────────┐   │
│  │  API Client                                     │   │
│  │  - Base URL: http://localhost:8080/api         │   │
│  │  - Auto-injects JWT token in headers           │   │
│  │  - Error handling & interceptors                │   │
│  └──────────────┬──────────────────────────────────┘   │
│                 │                                       │
└─────────────────┼───────────────────────────────────────┘
                  │ HTTP/HTTPS
                  │
                  ▼
┌─────────────────────────────────────────────────────────┐
│         SPRING BOOT BACKEND (Java)                      │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Spring Boot Application                                │
│  ├── Spring Web MVC (REST endpoints)                   │
│  ├── Spring Data JPA (ORM)                            │
│  ├── Spring Security (Authentication)                  │
│  ├── Hibernate (Database mapping)                      │
│  ├── Validation (Bean Validation)                      │
│  ├── JWT (Token generation & validation)              │
│  └── MapStruct/ModelMapper (Entity mapping)            │
│                                                         │
│  Architecture Layers:                                   │
│  1. Controllers (HTTP endpoints)                        │
│  2. Services (Business logic)                           │
│  3. Repositories (Database access)                      │
│  4. Entities (Database models)                          │
│  5. DTOs (Data transfer objects)                        │
│                                                         │
└──────────────┬──────────────────────────────────────────┘
               │ JDBC Driver
               │
               ▼
┌─────────────────────────────────────────────────────────┐
│          POSTGRESQL DATABASE                           │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Database: virtual_portfolio                            │
│  ├── users (authentication)                            │
│  ├── skills (portfolio data)                           │
│  ├── projects (portfolio data)                         │
│  ├── experiences (portfolio data)                      │
│  ├── education (portfolio data)                        │
│  ├── hobbies (portfolio data)                          │
│  ├── messages (contact form)                           │
│  └── testimonials (user feedback)                      │
│                                                         │
│  Persistence: Docker volume (postgres_data)            │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

**Architecture Diagrams Created**: January 14, 2026
