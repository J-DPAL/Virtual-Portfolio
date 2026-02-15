# Project Guidelines

## Mandatory Feature/Bug Workflow (ALWAYS FOLLOW – NO EXCEPTIONS)

Every feature implementation or bug fix MUST follow these exact steps in order and all steps MUST be completed before considering the work done. Skipping any step is NOT allowed:

1. **Analyse the Code First**
   - Carefully read and understand the existing code.
   - Do NOT assume anything.
   - Verify how the feature currently works.
   - Identify dependencies, side effects, and integration points.
   - Check related backend, frontend, database, gateway, and security logic.

2. **Update the Code**
   - Apply the required changes.
   - Follow existing architecture and patterns.
   - Maintain consistency with project structure and conventions.

3. **Re-Analyse the Code**
   - Review all modified files.
   - Check for logical errors.
   - Check for integration issues.
   - Ensure consistency with architecture and conventions.
   - Verify imports, dependencies, types, DTOs, mappings, and configuration.

4. **Fix Issues If Needed**
   - If any issue is found, fix it immediately.
   - Re-analyse again after every fix.
   - Repeat until there are no errors, inconsistencies, or architectural violations.

5. **Test Everything Using Commands (NOT Playwright)**
   - Run backend using Maven commands.
   - Run frontend using npm commands.
   - Use build commands.
   - Use Docker if necessary.
   - Verify there are ZERO errors.
   - Every time a command is used:
     - WAIT for the output.
     - Analyse the output.
     - Only then proceed to the next command.
   - Do NOT rely on assumptions.
   - Do NOT skip testing steps.
   - Ensure every command runs successfully without errors.
   - If any command fails, fix the issue immediately and re-test until all commands run successfully.

6. **Final Verification**
   - Perform a complete final review of:
     - Backend logic
     - Frontend integration
     - API Gateway routing
     - Database schema usage
     - Security configuration
     - JWT authentication
     - Environment variables
   - Ensure everything works together correctly.

7. **Linting & Formatting (MANDATORY FINAL STEP)**
   - Run ESLint for frontend.
   - Run Prettier if configured.
   - Run Spotless for backend if configured.
   - Fix all formatting and lint issues.
   - Ensure zero linting errors before completion.
   - Fix all formatting issues to maintain code quality and consistency.

These steps MUST be followed for EVERY feature and EVERY bug fix without exception.

---

## Code Style

- **Java (Backend)**:
  - Follow standard Spring Boot conventions.
  - Each microservice uses a 5-layer structure:
    - Data Access
    - Business Logic
    - Presentation
    - Mapping
    - Utils/Exceptions
  - Reference structure:
    - `backend/users-service/src/main/java/com/portfolio/users/`

- **React (Frontend)**:
  - Use functional components.
  - Use hooks and context.
  - Styling via Tailwind CSS.
  - Reference:
    - `frontend/src/components/`
    - `frontend/src/context/`

- **Formatting**:
  - Follow `.editorconfig` if present.
  - Otherwise match existing indentation and style.
  - Frontend linting:
    - `frontend/eslint.config.js`

---

## Architecture

- **Microservices**
  - Each backend service is independent.
  - Communication via REST through API Gateway:
    - `backend/api-gateway`

- **API Gateway**
  - Central entry point for backend APIs.
  - Handles routing and load balancing.

- **Database**
  - Postgres.
  - Each service has its own database/schema.
  - Schemas located in:
    - `backend/database/schemas/`

- **Frontend**
  - React app communicates ONLY with API Gateway.
  - Never communicate directly with microservices.

---

## Build and Test Commands

### Backend

- Build all backend (dev):
  ```
  cd backend && mvn clean install
  ```

- Build all backend (prod):
  ```
  mvn clean package -DskipTests
  ```

- Run a backend service:
  ```
  mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
  ```

- Run backend tests (per service):
  ```
  ./mvnw test
  ```

### Frontend

- Install & run dev server:
  ```
  cd frontend && npm install && npm run dev
  ```

- Build frontend:
  ```
  cd frontend && npm run build
  ```

- Lint frontend:
  ```
  npm run lint
  ```

### Docker

- Run all services:
  ```
  docker-compose up --build
  ```

---

## Project Conventions

- **Internationalization**
  - All user-facing text must be i18n-enabled (EN/FR/ES).
  - Backend models use multilingual fields:
    - `*_en`
    - `*_fr`
    - `*_es`

- **Admin/Public Separation**
  - Admin UI and routes protected by JWT auth.
  - See:
    - `frontend/src/context/AuthContext.jsx`
    - `users-service`

- **Environment Variables**
  - `.env` for development.
  - `.env.production` for production.
  - NEVER commit secrets.

- **Service Structure**
  - Every microservice follows the same directory and layer pattern.

---

## Integration Points

- **Database**
  - Postgres.
  - Per-service schemas in:
    - `backend/database/schemas/`

- **Auth**
  - JWT-based.
  - Managed by `users-service`.

- **API Gateway**
  - All frontend/backend communication goes through:
    - `backend/api-gateway`

- **Docker**
  - Orchestrated via:
    - `docker-compose.yml`

---

## Security

- **Secrets**
  - Never commit real secrets.
  - Use environment variables.
  - `.env.production` must be gitignored.

- **JWT Authentication**
  - Required for all admin routes.
  - See `users-service` and `AuthContext.jsx`.

- **CORS**
  - Configured per service in `SecurityConfig.java`.

- **Database Access**
  - Each service has:
    - Its own DB user
    - Its own schema
