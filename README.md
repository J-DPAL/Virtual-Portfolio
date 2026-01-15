# Virtual Portfolio - Bilingual Dynamic Portfolio Application

## Project Description
A fully dynamic, bilingual portfolio website built with Java microservices backend and React frontend, featuring admin panel for managing portfolio content and testimonials.

## Tech Stack
- **Backend**: Java 17, Spring Boot 3.2, PostgreSQL, JWT, Microservices Architecture
- **Frontend**: React 18, Tailwind CSS, i18n (Internationalization), Axios
- **Deployment**: Docker, Docker Compose
- **Architecture**: Data Access Layer, Business Logic Layer, Presentation Layer, Mapping Layer

## Project Structure
```
Virtual Portfolio/
├── backend/                    # Java Backend
│   ├── src/
│   │   ├── main/java/com/portfolio/
│   │   │   ├── api/               # Main application class
│   │   │   ├── dataAccessLayer/   # Repositories and entities
│   │   │   ├── businessLogicLayer/# Services
│   │   │   ├── presentationLayer/ # Controllers
│   │   │   ├── mappingLayer/      # DTOs and mappers
│   │   │   ├── security/          # JWT and security config
│   │   │   ├── exception/         # Exception handling
│   │   │   └── utils/             # Utility classes
│   │   └── resources/
│   │       └── application.yml    # Configuration
│   ├── pom.xml                 # Maven dependencies
│   └── Dockerfile
├── frontend/                   # React Frontend
│   ├── src/
│   │   ├── components/         # React components
│   │   ├── pages/              # Page components
│   │   ├── context/            # React context (Auth, Language)
│   │   ├── services/           # API services
│   │   ├── hooks/              # Custom hooks
│   │   ├── locales/            # i18n translations
│   │   └── utils/              # Utility functions
│   ├── package.json
│   ├── vite.config.js
│   ├── tailwind.config.cjs
│   └── Dockerfile
├── database/
│   └── init.sql               # Database initialization script
├── docker-compose.yml         # Docker Compose configuration
├── .env                       # Environment variables
└── .env.production            # Production environment variables
```

## Prerequisites
- Docker and Docker Compose
- Java 17 (for local development)
- Node.js 18+ (for frontend development)
- Maven 3.9+ (for backend development)
- PostgreSQL 16 (for local development)

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/J-DPAL/Virtual-Portfolio.git
cd Virtual-Portfolio
```

### 2. Configure Environment Variables
The `.env` file is already set with default values. For production, update `.env.production`:
```bash
DB_USERNAME=postgres
DB_PASSWORD=your_secure_password
JWT_SECRET=your_long_random_secret_key
```

### 3. Run with Docker (Recommended)

#### Start all services:
```bash
docker-compose up --build
```

This will start:
- PostgreSQL database on port 5432
- Java backend on port 8080
- React frontend on port 3000

#### Access the application:
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080/api
- Database: localhost:5432

#### Stop services:
```bash
docker-compose down
```

#### View logs:
```bash
docker-compose logs -f backend    # Backend logs
docker-compose logs -f frontend   # Frontend logs
docker-compose logs -f postgres   # Database logs
```

### 4. Local Development (Without Docker)

#### Backend Setup:
```bash
cd backend

# Install dependencies
mvn install

# Run with development profile
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

#### Frontend Setup:
```bash
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

#### Database Setup (Local):
```sql
-- Create database
CREATE DATABASE virtual_portfolio;

-- Run init.sql script
psql -U postgres -d virtual_portfolio -f ../database/init.sql
```

## Default Credentials
- Email: `admin@portfolio.com`
- Password: `admin123` (Change this in production!)

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

## API Endpoints (To Be Documented)

### Authentication
- `POST /api/v1/auth/login` - User login

## Database Schema

### Users Table
- id (Primary Key)
- email (Unique)
- password (Hashed)
- full_name
- role (ADMIN, USER)
- active
- created_at
- updated_at

(Additional tables for skills, projects, experience, etc. will be added)

## Building for Production

### Build backend JAR:
```bash
cd backend
mvn clean package -DskipTests
```

### Build frontend:
```bash
cd frontend
npm run build
```

### Deploy with Docker:
```bash
docker-compose -f docker-compose.yml up -d
```

## Development Workflow

1. Create feature branch: `git checkout -b feature/feature-name`
2. Make changes
3. Commit changes: `git commit -m "Add feature description"`
4. Push to repository: `git push origin feature/feature-name`
5. Create Pull Request

## Troubleshooting

### Backend won't connect to database:
- Ensure PostgreSQL is running and accessible
- Check database credentials in `.env`
- Verify PostgreSQL is listening on port 5432

### Frontend can't reach backend:
- Ensure backend is running on port 8080
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
