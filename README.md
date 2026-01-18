# Virtual Portfolio - Bilingual Dynamic Portfolio Application

## Project Description
A fully dynamic, bilingual portfolio website built with Java microservices architecture and React frontend, featuring an API Gateway router and independent microservices for different portfolio components.

## Tech Stack
- **Backend**: Java 17, Spring Boot 3.2, PostgreSQL, JWT, Spring Cloud, Microservices Architecture
- **API Gateway**: Spring Boot with routing and load balancing
- **Frontend**: React 18, Tailwind CSS, i18n (Internationalization), Axios
- **Deployment**: Docker, Docker Compose
- **Architecture**: Multi-service microservices with 5-layer architecture per service (Data Access Layer, Business Logic Layer, Presentation Layer, Mapping Layer, Utils/Exceptions)

## Microservices Architecture

### Service Overview
The application is structured as independent microservices communicating through the API Gateway:

1. **API Gateway** (Port 8080) - Routes all requests to appropriate services
2. **Users Service** (Port 8081) - Authentication and user management
3. **Skills Service** (Port 8082) - Skills portfolio management
4. **Projects Service** (Port 8083) - Projects portfolio management
5. **Experience Service** (Port 8084) - Work experience management
6. **Education Service** (Port 8085) - Education management
7. **Hobbies Service** (Port 8086) - Hobbies management
8. **Testimonials Service** (Port 8087) - Testimonials management
9. **Messages Service** (Port 8088) - Contact messages management

### Project Structure
```
Virtual Portfolio/
├── backend/                          # Java Backend - Microservices
│   ├── api-gateway/                  # API Gateway Service
│   │   ├── src/main/java/com/portfolio/apigateway/
│   │   │   ├── businessLayer/        # Business logic for routing
│   │   │   ├── domainclientLayer/    # Clients for other services
│   │   │   ├── presentationLayer/    # Controllers for routing
│   │   │   ├── utils/exceptions/     # Exception handling
│   │   ├── pom.xml
│   │   └── Dockerfile
│   ├── users-service/                # Users Microservice
│   │   ├── src/main/java/com/portfolio/users/
│   │   │   ├── businessLayer/        # Authentication logic
│   │   │   ├── dataAccessLayer/      # User repositories
│   │   │   ├── mappingLayer/         # DTOs and mappers
│   │   │   ├── presentationLayer/    # User controllers
│   │   │   ├── utils/exceptions/     # Exception handling
│   │   ├── pom.xml
│   │   └── Dockerfile
│   ├── skills-service/               # Skills Microservice
│   │   ├── src/main/java/com/portfolio/skills/
│   │   └── [Similar structure as users-service]
│   ├── projects-service/             # Projects Microservice
│   │   └── [Similar structure]
│   ├── experience-service/           # Experience Microservice
│   │   └── [Similar structure]
│   ├── education-service/            # Education Microservice
│   │   └── [Similar structure]
│   ├── hobbies-service/              # Hobbies Microservice
│   │   └── [Similar structure]
│   ├── testimonials-service/         # Testimonials Microservice
│   │   └── [Similar structure]
│   ├── messages-service/             # Messages Microservice
│   │   └── [Similar structure]
│   ├── pom.xml                       # Parent POM (Multi-module project)
│   └── database/
│       └── init.sql                  # Database initialization
├── frontend/                         # React Frontend
│   ├── src/
│   │   ├── components/               # React components
│   │   ├── pages/                    # Page components
│   │   ├── context/                  # React context (Auth, Language)
│   │   ├── services/                 # API services (calls to API Gateway)
│   │   └── locales/                  # i18n translations
│   ├── package.json
│   ├── vite.config.js
│   └── Dockerfile
├── docker-compose.yml                # Orchestrates all services
├── .env                              # Development environment variables
└── .env.production                   # Production environment variables
```

## Docker Deployment

### Start all microservices:
```bash
docker-compose up --build
```

This will start all 9 services:
- PostgreSQL database on port 5432
- API Gateway on port 8080
- Users Service on port 8081
- Skills Service on port 8082
- Projects Service on port 8083
- Experience Service on port 8084
- Education Service on port 8085
- Hobbies Service on port 8086
- Testimonials Service on port 8087
- Messages Service on port 8088
- React frontend on port 3000

### Access the application:
- Frontend: http://localhost:3000
- API Gateway: http://localhost:8080/api
- Individual Services: http://localhost:808X/api (where X is the service port suffix)
- Database: localhost:5432

### Stop services:
```bash
docker-compose down
```

### View logs:
```bash
docker-compose logs -f api-gateway      # API Gateway logs
docker-compose logs -f users-service    # Users Service logs
docker-compose logs -f [service-name]   # Any service logs
docker-compose logs -f postgres         # Database logs
```

## Local Development (Without Docker)

### Backend Setup:
```bash
cd backend

# Build the entire multi-module project
mvn clean install

# Run individual service (example: users-service)
cd users-service
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Or run API Gateway
cd ../api-gateway
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Frontend Setup:
```bash
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

### Database Setup (Local):
```sql
-- Create database
CREATE DATABASE virtual_portfolio;

-- Run init.sql script
psql -U postgres -d virtual_portfolio -f ../database/init.sql
```

## API Routes

### Through API Gateway (Recommended)
All requests should go through the API Gateway at `http://localhost:8080/api`

- **Users Service**: `POST /api/users/login`, `GET /api/users/{id}`
- **Skills Service**: `GET /api/skills`, `POST /api/skills`, `PUT /api/skills/{id}`
- **Projects Service**: `GET /api/projects`, `POST /api/projects`
- **Experience Service**: `GET /api/experience`, `POST /api/experience`
- **Education Service**: `GET /api/education`, `POST /api/education`
- **Hobbies Service**: `GET /api/hobbies`, `POST /api/hobbies`
- **Testimonials Service**: `GET /api/testimonials`, `POST /api/testimonials`
- **Messages Service**: `GET /api/messages`, `POST /api/messages`

### Direct Service Routes (For testing)
- Users Service: `http://localhost:8081/api`
- Skills Service: `http://localhost:8082/api`
- Projects Service: `http://localhost:8083/api`
- Experience Service: `http://localhost:8084/api`
- Education Service: `http://localhost:8085/api`
- Hobbies Service: `http://localhost:8086/api`
- Testimonials Service: `http://localhost:8087/api`
- Messages Service: `http://localhost:8088/api`

## Default Credentials
- Email: `admin@portfolio.com`
- Password: `admin123` (⚠️ Change this in production!)

## Microservices Communication

### Service-to-Service Communication
Each microservice can communicate with other services through direct REST calls or through the API Gateway. Services should use the following base URLs:

- **Internal (within Docker)**: `http://[service-name]:808X/api`
- **External (for testing)**: `http://localhost:808X/api`

### Example: Testimonials calling Users Service
```java
// Direct call to users-service
RestTemplate restTemplate = new RestTemplate();
String usersServiceUrl = "http://users-service:8081/api/users/" + userId;
User user = restTemplate.getForObject(usersServiceUrl, User.class);
```

## Data Model (To Be Implemented)

Each service will manage its own data model:

### Users Service
- Users (Authentication, Profiles)
- Roles

### Skills Service
- Skills
- Proficiency Levels

### Projects Service
- Projects
- Project Technologies
- Project Links

### Experience Service
- Work Experience

### Education Service
- Education Records
- Certifications

### Hobbies Service
- Hobbies

### Testimonials Service
- Testimonials (with approval status)
- Testimonial Ratings

### Messages Service
- Contact Messages
- Message Status

### Future Shared Services
- Contact Information
- File Management (Resume, Images)

## Features (To Be Implemented)

### Admin Panel
- [ ] User authentication with JWT
- [ ] Dashboard
- [ ] Manage Skills
- [ ] Manage Projects
- [ ] Manage Work Experience
- [ ] Manage Education
- [ ] Manage Hobbies
- [ ] Manage Resume (Download feature)
- [ ] Manage Contact Information
- [ ] View Contact Messages
- [ ] Manage Testimonials (Approve/Reject)

### Public Pages
- [ ] Portfolio Display
- [ ] Contact Form
- [ ] Testimonials
- [ ] Resume Download
- [ ] Language Switcher

## Building for Production

### Build all backend services:
```bash
cd backend
mvn clean package -DskipTests
```

### Build frontend:
```bash
cd frontend
npm run build
```

### Deploy with Docker (Recommended):
```bash
docker-compose up --build -d
```

## Development Workflow

1. Create feature branch: `git checkout -b feature/feature-name`
2. Make changes to the relevant service
3. Commit changes: `git commit -m "Add feature to [service-name]"`
4. Push to repository: `git push origin feature/feature-name`
5. Create Pull Request

## Troubleshooting

### Backend services won't connect to database:
- Ensure PostgreSQL is running: `docker-compose logs postgres`
- Check database credentials in `.env`
- Verify all services are in the same Docker network

### Frontend can't reach API Gateway:
- Ensure API Gateway is running: `docker-compose logs api-gateway`
- Check `REACT_APP_API_BASE_URL` in frontend environment
- Verify network connectivity between containers

### Individual service debugging:
```bash
# View specific service logs
docker-compose logs -f users-service
docker-compose logs -f api-gateway

# Connect to database for testing
psql -h localhost -U postgres -d virtual_portfolio
```

## Project Roadmap

### Phase 1: Structure (Current)
- ✅ Project initialization
- ✅ Microservices architecture setup
- ✅ Docker configuration
- 🔄 Database schema definition

### Phase 2: Core Services (Next)
- Service implementation (CRUD operations)
- API endpoints for each service
- Inter-service communication
- Database tables and relationships

### Phase 3: Frontend Development
- Public portfolio pages
- Admin dashboard
- Service integration
- Testing and bug fixes

### Phase 4: Deployment & Optimization
- Production builds
- Performance optimization
- Security hardening
- CI/CD pipeline setup
- Check CORS configuration in `SecurityConfig.java`
- Verify `VITE_API_BASE_URL` environment variable

### Docker port conflicts:
- Change ports in `docker-compose.yml`
- Or kill processes using those ports

## Contributing
Please follow the project structure and naming conventions. All code should be well-documented.

## License
MIT License

## Support
For issues and questions, please open an issue on GitHub.
