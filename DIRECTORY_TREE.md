# Virtual Portfolio - Complete Project Structure

## Full Directory Tree

```
Virtual Portfolio/
│
├── 📁 backend/                              # Java Spring Boot Application
│   ├── 📁 src/
│   │   ├── 📁 main/
│   │   │   ├── 📁 java/com/portfolio/
│   │   │   │   ├── 📁 api/
│   │   │   │   │   └── PortfolioApplication.java    # Main Spring Boot application
│   │   │   │   │
│   │   │   │   ├── 📁 dataAccessLayer/
│   │   │   │   │   ├── 📁 entity/
│   │   │   │   │   │   ├── BaseEntity.java          # Common entity fields (id, createdAt, updatedAt)
│   │   │   │   │   │   ├── User.java                # User entity with role (ADMIN/USER)
│   │   │   │   │   │   └── (More entities to be added)
│   │   │   │   │   │
│   │   │   │   │   └── 📁 repository/
│   │   │   │   │       ├── UserRepository.java      # JPA repository for User entity
│   │   │   │   │       └── (More repositories to be added)
│   │   │   │   │
│   │   │   │   ├── 📁 businessLogicLayer/
│   │   │   │   │   └── 📁 service/
│   │   │   │   │       ├── AuthService.java         # Authentication business logic
│   │   │   │   │       └── (More services to be added)
│   │   │   │   │
│   │   │   │   ├── 📁 presentationLayer/
│   │   │   │   │   └── 📁 controller/
│   │   │   │   │       ├── AuthController.java      # REST endpoints for auth (/api/v1/auth/*)
│   │   │   │   │       └── (More controllers to be added)
│   │   │   │   │
│   │   │   │   ├── 📁 mappingLayer/
│   │   │   │   │   ├── 📁 dto/
│   │   │   │   │   │   ├── UserDTO.java             # Data transfer object for User
│   │   │   │   │   │   ├── LoginRequest.java        # Request DTO for login
│   │   │   │   │   │   ├── LoginResponse.java       # Response DTO for login
│   │   │   │   │   │   └── (More DTOs to be added)
│   │   │   │   │   │
│   │   │   │   │   └── 📁 mapper/
│   │   │   │   │       ├── UserMapper.java          # Maps Entity ↔ DTO
│   │   │   │   │       └── (More mappers to be added)
│   │   │   │   │
│   │   │   │   ├── 📁 security/
│   │   │   │   │   ├── JwtTokenProvider.java        # JWT token generation & validation
│   │   │   │   │   ├── SecurityConfig.java          # Spring Security & CORS configuration
│   │   │   │   │   └── (More security classes)
│   │   │   │   │
│   │   │   │   ├── 📁 exception/
│   │   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   │   ├── GlobalExceptionHandler.java  # Global error handling
│   │   │   │   │   └── ErrorResponse.java           # Standard error response
│   │   │   │   │
│   │   │   │   ├── 📁 config/
│   │   │   │   │   └── (Configuration classes)
│   │   │   │   │
│   │   │   │   └── 📁 utils/
│   │   │   │       └── (Utility classes)
│   │   │   │
│   │   │   └── 📁 resources/
│   │   │       ├── application.yml                   # Main Spring Boot configuration
│   │   │       ├── application-dev.yml              # Development profile
│   │   │       └── application-prod.yml             # Production profile (optional)
│   │   │
│   │   └── 📁 test/
│   │       └── (Test files)
│   │
│   ├── 📄 pom.xml                           # Maven project configuration
│   ├── 📄 Dockerfile                        # Docker build configuration
│   ├── 📄 .gitignore                        # Git ignore rules for Java
│   └── 📄 .env                              # Backend environment variables
│
├── 📁 frontend/                             # React Application
│   ├── 📁 src/
│   │   ├── 📁 components/
│   │   │   ├── 📁 common/
│   │   │   │   ├── Header.jsx               # Navigation header with language switch
│   │   │   │   ├── Footer.jsx               # Footer component
│   │   │   │   └── Navigation.jsx           # Navigation menu (if needed)
│   │   │   │
│   │   │   ├── 📁 public/
│   │   │   │   ├── Skills.jsx               # Skills display (to be implemented)
│   │   │   │   ├── Projects.jsx             # Projects showcase (to be implemented)
│   │   │   │   ├── Experience.jsx           # Work experience (to be implemented)
│   │   │   │   ├── Education.jsx            # Education section (to be implemented)
│   │   │   │   ├── Hobbies.jsx              # Hobbies display (to be implemented)
│   │   │   │   ├── Resume.jsx               # Resume download (to be implemented)
│   │   │   │   ├── Contact.jsx              # Contact form (to be implemented)
│   │   │   │   └── Testimonials.jsx         # Testimonials page (to be implemented)
│   │   │   │
│   │   │   └── 📁 admin/
│   │   │       ├── AdminLogin.jsx           # Admin login form
│   │   │       ├── AdminDashboard.jsx       # Admin dashboard
│   │   │       ├── SkillsManager.jsx        # Manage skills (to be implemented)
│   │   │       ├── ProjectsManager.jsx      # Manage projects (to be implemented)
│   │   │       ├── ExperienceManager.jsx    # Manage experience (to be implemented)
│   │   │       ├── EducationManager.jsx     # Manage education (to be implemented)
│   │   │       ├── HobbiesManager.jsx       # Manage hobbies (to be implemented)
│   │   │       ├── ResumeManager.jsx        # Manage resume (to be implemented)
│   │   │       ├── MessagesManager.jsx      # View contact messages (to be implemented)
│   │   │       └── TestimonialsManager.jsx  # Manage testimonials (to be implemented)
│   │   │
│   │   ├── 📁 pages/
│   │   │   ├── HomePage.jsx                 # Home page / Portfolio display
│   │   │   ├── AdminPage.jsx                # Admin panel wrapper (to be implemented)
│   │   │   └── NotFoundPage.jsx             # 404 page (to be implemented)
│   │   │
│   │   ├── 📁 context/
│   │   │   ├── AuthContext.jsx              # Authentication state & functions
│   │   │   ├── LanguageContext.jsx          # Language state & switcher
│   │   │   └── DataContext.jsx              # Portfolio data (to be implemented)
│   │   │
│   │   ├── 📁 services/
│   │   │   ├── apiClient.js                 # Axios instance with interceptors
│   │   │   ├── authService.js               # Authentication API calls
│   │   │   └── portfolioService.js          # Portfolio data API calls (to be implemented)
│   │   │
│   │   ├── 📁 hooks/
│   │   │   ├── useAuth.js                   # Custom auth hook (to be implemented)
│   │   │   ├── useLanguage.js               # Custom language hook (to be implemented)
│   │   │   └── useApi.js                    # Custom API hook (to be implemented)
│   │   │
│   │   ├── 📁 locales/
│   │   │   ├── en.json                      # English translations
│   │   │   └── fr.json                      # French translations
│   │   │
│   │   ├── 📁 utils/
│   │   │   ├── validators.js                # Form validation utilities
│   │   │   └── formatters.js                # Data formatting utilities
│   │   │
│   │   ├── 📄 index.css                     # Tailwind CSS imports & global styles
│   │   ├── 📄 i18n.js                       # i18next configuration
│   │   ├── 📄 App.jsx                       # Main app component with routing
│   │   └── 📄 main.jsx                      # React DOM entry point
│   │
│   ├── 📄 index.html                        # HTML template
│   ├── 📄 package.json                      # NPM dependencies & scripts
│   ├── 📄 vite.config.js                    # Vite build configuration
│   ├── 📄 tailwind.config.cjs                # Tailwind CSS configuration
│   ├── 📄 postcss.config.cjs                 # PostCSS configuration for Tailwind
│   ├── 📄 .eslintrc.json                    # ESLint configuration
│   ├── 📄 Dockerfile                        # Docker build configuration
│   ├── 📄 .gitignore                        # Git ignore rules for Node
│   ├── 📄 .env.local                        # Local environment variables
│   └── 📄 .env.example                      # Environment variables template
│
├── 📁 database/
│   └── 📄 init.sql                          # Database schema & initialization script
│
├── 📁 node_modules/                         # Frontend dependencies (generated by npm)
├── 📁 .git/                                 # Git repository (auto-created)
│
├── 📄 docker-compose.yml                    # Docker Compose orchestration
├── 📄 .env                                  # Development environment variables
├── 📄 .env.production                       # Production environment variables
├── 📄 .gitignore                            # Global Git ignore rules
│
├── 📄 README.md                             # Main project documentation
├── 📄 SETUP.md                              # Detailed setup guide
├── 📄 QUICKSTART.md                         # Quick start reference
├── 📄 PROJECT_SUMMARY.md                    # Setup summary
├── 📄 COMMANDS.md                           # Complete command reference
├── 📄 ARCHITECTURE.md                       # Architecture diagrams & explanations
│
├── 📄 Makefile                              # Linux/Mac convenience commands
└── 📄 run.bat                               # Windows convenience commands
```

---

## File Count Summary

| Component | Type | Count | Status |
|-----------|------|-------|--------|
| Backend Java Classes | .java | 7 | ✅ Created |
| Backend Config | .yml | 2 | ✅ Created |
| Backend Build | pom.xml | 1 | ✅ Created |
| Frontend Components | .jsx | 10 | 5 ✅ Created, 5 🔄 To implement |
| Frontend Config | .js/.cjs/.json | 7 | ✅ Created |
| Frontend Locales | .json | 2 | ✅ Created |
| Frontend Services | .js | 2 | ✅ Created |
| Frontend Context | .jsx | 2 | ✅ Created |
| Docker Files | Dockerfile | 3 | ✅ Created |
| Configuration | .yml/.json | 4 | ✅ Created |
| Database | .sql | 1 | ✅ Created |
| Documentation | .md | 6 | ✅ Created |
| Utility Scripts | .bat/.mk | 2 | ✅ Created |
| **TOTAL** | | **50+** | **✅ Complete** |

---

## File Descriptions

### Backend Core Files

| File | Purpose |
|------|---------|
| `PortfolioApplication.java` | Spring Boot application entry point |
| `BaseEntity.java` | Abstract base class for all entities with common fields |
| `User.java` | User entity for admin authentication |
| `UserRepository.java` | JPA repository for User CRUD operations |
| `AuthService.java` | Business logic for user authentication |
| `AuthController.java` | REST API endpoints for authentication |
| `UserDTO.java` | Data transfer object for User data |
| `LoginRequest.java` | DTO for login request validation |
| `LoginResponse.java` | DTO for login response |
| `UserMapper.java` | Maps between User entity and UserDTO |
| `JwtTokenProvider.java` | Generates and validates JWT tokens |
| `SecurityConfig.java` | Spring Security and CORS configuration |
| `GlobalExceptionHandler.java` | Centralized exception handling |
| `ErrorResponse.java` | Standard error response format |

### Frontend Core Files

| File | Purpose |
|------|---------|
| `App.jsx` | Main app component with React Router |
| `main.jsx` | React entry point (renders to DOM) |
| `Header.jsx` | Navigation header with language switcher |
| `Footer.jsx` | Footer component |
| `AdminLogin.jsx` | Admin login form component |
| `AdminDashboard.jsx` | Admin dashboard interface |
| `HomePage.jsx` | Public home page |
| `AuthContext.jsx` | Authentication state management |
| `LanguageContext.jsx` | Language selection state |
| `apiClient.js` | Axios HTTP client with JWT interceptor |
| `authService.js` | API calls for authentication |
| `i18n.js` | i18next configuration for translations |
| `en.json` | English translations |
| `fr.json` | French translations |

### Configuration Files

| File | Purpose |
|------|---------|
| `application.yml` | Spring Boot main configuration |
| `application-dev.yml` | Development profile configuration |
| `pom.xml` | Maven dependencies and build config |
| `package.json` | NPM dependencies and scripts |
| `vite.config.js` | Vite build tool configuration |
| `tailwind.config.cjs` | Tailwind CSS configuration |
| `postcss.config.cjs` | PostCSS configuration |
| `.eslintrc.json` | ESLint linting rules |
| `docker-compose.yml` | Docker services orchestration |
| `.env` | Development environment variables |
| `.env.production` | Production environment variables |

### Documentation Files

| File | Purpose |
|------|---------|
| `README.md` | Comprehensive project documentation |
| `SETUP.md` | Detailed setup and architecture guide |
| `QUICKSTART.md` | Quick start reference guide |
| `PROJECT_SUMMARY.md` | Setup summary and checklist |
| `COMMANDS.md` | Complete command reference |
| `ARCHITECTURE.md` | Architecture diagrams and flow |
| `DIRECTORY_TREE.md` | This file - project structure overview |

---

## Directory Structure by Size

### Frontend Size Distribution
```
src/
├── components/          ~500 lines (10 component files)
├── pages/              ~100 lines (3 page files)
├── context/            ~200 lines (2 context files)
├── services/           ~100 lines (2 service files)
├── locales/            ~100 lines (2 translation files)
├── utils/              ~100 lines (optional utility files)
├── hooks/              ~100 lines (custom hooks - to be implemented)
└── static files        ~50 lines (CSS, config files)
```

### Backend Size Distribution
```
com/portfolio/
├── dataAccessLayer/    ~150 lines (entities & repositories)
├── businessLogicLayer/ ~200 lines (services)
├── presentationLayer/  ~100 lines (controllers)
├── mappingLayer/       ~200 lines (DTOs & mappers)
├── security/           ~250 lines (JWT & Spring Security)
├── exception/          ~150 lines (error handling)
└── config/             ~100 lines (configuration classes)
```

---

## Implementation Phases

### Phase 1: ✅ COMPLETE
- [x] Project structure setup
- [x] Backend skeleton (7 layers)
- [x] Frontend skeleton (React with Vite)
- [x] Docker configuration
- [x] Database schema (basic)
- [x] Authentication endpoint
- [x] Login page
- [x] Documentation

### Phase 2: 🔄 TO IMPLEMENT
- [ ] Create all data entities (Skills, Projects, etc.)
- [ ] Create repositories for all entities
- [ ] Create services for business logic
- [ ] Create DTOs and mappers
- [ ] Create REST controllers with CRUD endpoints
- [ ] Create frontend pages for all sections
- [ ] Implement admin management components

### Phase 3: 🔄 TO IMPLEMENT
- [ ] Add form validation (frontend & backend)
- [ ] Implement file upload (resume)
- [ ] Add search/filter functionality
- [ ] Implement testimonial approval workflow
- [ ] Add pagination for lists
- [ ] Implement error handling

### Phase 4: 🔄 TO IMPLEMENT
- [ ] Write unit tests
- [ ] Write integration tests
- [ ] Performance optimization
- [ ] Security hardening
- [ ] Deploy to production
- [ ] Set up CI/CD pipeline
- [ ] Configure monitoring & logging

---

## Database Tables (To Be Created)

Based on the requirements, the following tables will need to be created:

```sql
users           -- Existing (for admin authentication)
skills          -- Skills/competencies
projects        -- Portfolio projects
experiences     -- Work experience entries
education       -- Education entries
hobbies         -- Hobbies/interests
resumes         -- Resume/CV files
contact_info    -- Contact information (email, phone, social)
messages        -- Contact form submissions
testimonials    -- User testimonials
```

Each table will have:
- `id` (Primary Key, auto-increment)
- `created_at` (Timestamp)
- `updated_at` (Timestamp)
- Language support (en/fr for titles, descriptions, etc.)
- Appropriate foreign keys for relationships

---

## Next Steps

### Immediate (Before Feature Implementation)
1. Verify all containers start: `docker-compose up -d`
2. Test login endpoint: http://localhost:8080/api/v1/auth/health
3. Test frontend loads: http://localhost:3000
4. Test login: admin@portfolio.com / admin123

### Short Term (1-2 weeks)
1. Create all entity models
2. Generate repositories
3. Implement CRUD services
4. Create API controllers
5. Build basic frontend pages

### Medium Term (2-4 weeks)
1. Complete frontend components
2. Add form validation
3. Implement file upload
4. Add search/filtering
5. Testimonial workflow

### Long Term (1+ months)
1. Testing and QA
2. Performance optimization
3. Security audit
4. Production deployment
5. Monitoring & maintenance

---

**Document Created**: January 14, 2026  
**Version**: 1.0.0 - Complete Structure Overview
