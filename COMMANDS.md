# Virtual Portfolio - Complete Command Reference

## Docker Commands (Recommended)

### Start Services
```bash
# Start all services in background
docker-compose up -d

# Start with live logs
docker-compose up

# Rebuild and start
docker-compose up --build -d
```

### Stop Services
```bash
# Stop services (data persisted)
docker-compose down

# Stop and remove volumes (clean slate)
docker-compose down -v

# Stop and remove everything
docker-compose down --remove-orphans -v
```

### View Status & Logs
```bash
# List running containers
docker-compose ps

# View all logs (follow mode)
docker-compose logs -f

# View specific service logs
docker-compose logs -f backend     # Java backend
docker-compose logs -f frontend    # React frontend
docker-compose logs -f postgres    # PostgreSQL database

# View last 50 lines
docker-compose logs --tail=50

# View logs from specific time
docker-compose logs --since 10m
```

### Service Management
```bash
# Restart services
docker-compose restart

# Restart specific service
docker-compose restart backend
docker-compose restart frontend
docker-compose restart postgres

# Pause/Unpause services
docker-compose pause
docker-compose unpause

# Stop and remove containers (keep volumes)
docker-compose stop
```

### Build & Images
```bash
# Build images
docker-compose build

# Rebuild without cache
docker-compose build --no-cache

# Pull latest base images
docker-compose pull
```

### Database
```bash
# Access PostgreSQL shell
docker exec -it portfolio-postgres psql -U postgres -d virtual_portfolio

# Backup database
docker exec portfolio-postgres pg_dump -U postgres virtual_portfolio > backup.sql

# Restore database
docker exec -i portfolio-postgres psql -U postgres virtual_portfolio < backup.sql
```

---

## Windows Batch Commands (run.bat)

```bash
# Start services
.\run.bat docker-up

# Stop services
.\run.bat docker-down

# Build images
.\run.bat docker-build

# View logs
.\run.bat docker-logs

# Setup backend
.\run.bat backend-setup

# Run backend locally
.\run.bat backend-run

# Setup frontend
.\run.bat frontend-setup

# Run frontend dev server
.\run.bat frontend-dev

# Build frontend
.\run.bat frontend-build

# Clean all
.\run.bat clean
```

---

## Linux/Mac Make Commands

```bash
# Start services
make docker-up

# Stop services
make docker-down

# Build images
make docker-build

# View logs
make docker-logs
make docker-logs-backend
make docker-logs-frontend
make docker-logs-db

# Backend
make backend-setup
make backend-run
make backend-test
make backend-build

# Frontend
make frontend-setup
make frontend-dev
make frontend-build
make frontend-lint

# Database
make db-init

# Utility
make clean
make install
make all
```

---

## Backend Maven Commands

```bash
# Install dependencies
mvn install

# Run application
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Build JAR
mvn clean package -DskipTests

# Run tests
mvn test

# Skip tests during build
mvn install -DskipTests

# Update dependencies
mvn dependency:resolve-ranges

# Display dependency tree
mvn dependency:tree

# Clean build artifacts
mvn clean

# Run specific test class
mvn test -Dtest=UserRepositoryTest
```

---

## Frontend npm Commands

```bash
# Install dependencies
npm install

# Run development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview

# Lint code
npm run lint

# Update packages
npm update

# Install specific package
npm install <package-name>

# Install dev dependency
npm install --save-dev <package-name>

# Remove package
npm uninstall <package-name>

# Clear npm cache
npm cache clean --force

# Check outdated packages
npm outdated
```

---

## PostgreSQL Commands

### Using Docker
```bash
# Connect to database
docker exec -it portfolio-postgres psql -U postgres -d virtual_portfolio

# List databases
\l

# List tables
\dt

# Show table structure
\d <table_name>

# Exit
\q
```

### Local PostgreSQL
```bash
# Connect
psql -U postgres -d virtual_portfolio

# Create database
CREATE DATABASE virtual_portfolio;

# Create user
CREATE USER portfolio_user WITH PASSWORD 'password';

# Grant privileges
GRANT ALL PRIVILEGES ON DATABASE virtual_portfolio TO portfolio_user;

# Run SQL file
psql -U postgres -d virtual_portfolio -f init.sql
```

---

## Git Commands

```bash
# Clone repository
git clone https://github.com/J-DPAL/Virtual-Portfolio.git

# Create feature branch
git checkout -b feature/feature-name

# Stage changes
git add .

# Commit changes
git commit -m "Description of changes"

# Push to remote
git push origin feature/feature-name

# Switch branches
git checkout main

# Pull latest changes
git pull origin main

# View status
git status

# View log
git log --oneline
```

---

## Access Points

```
Frontend:        http://localhost:3000
Backend API:     http://localhost:8080/api
Database:        localhost:5432
```

### Login Credentials
```
Email:    admin@portfolio.com
Password: admin123
```

---

## Useful Curl Commands for Testing

```bash
# Check backend health
curl http://localhost:8080/api/v1/auth/health

# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@portfolio.com","password":"admin123"}'

# Get with token
curl -H "Authorization: Bearer <TOKEN>" \
  http://localhost:8080/api/endpoint
```

---

## File & Directory Management

```bash
# List project structure
tree /F                    # Windows
ls -la                     # Linux/Mac
tree -L 3                  # Show 3 levels

# Find files
find . -name "*.java"      # Find Java files
find . -name "*.jsx"       # Find React files

# Search in files
grep -r "string" ./        # Search recursively
grep -i "string" ./        # Case-insensitive search
```

---

## Environment Variable Management

```bash
# View environment variables
echo $ENV_VAR              # Linux/Mac
echo %ENV_VAR%             # Windows

# Set temporary variable
export VAR=value           # Linux/Mac
set VAR=value              # Windows Command Prompt
$env:VAR="value"           # Windows PowerShell

# Edit .env file
nano .env                  # Linux/Mac
edit .env                  # Windows
```

---

## System Cleanup

```bash
# Remove unused Docker resources
docker system prune

# Remove unused images
docker image prune

# Remove unused volumes
docker volume prune

# Remove unused networks
docker network prune

# Deep clean (remove all unused)
docker system prune -a --volumes
```

---

## Performance & Monitoring

```bash
# View Docker container stats
docker stats

# View specific container stats
docker stats portfolio-backend

# View container processes
docker top portfolio-backend

# Inspect container
docker inspect portfolio-postgres

# Check disk usage
docker system df
```

---

## Troubleshooting Commands

```bash
# Check if ports are in use
netstat -ano | findstr :3000              # Windows
lsof -i :3000                             # Linux/Mac

# Kill process on port
taskkill /PID <PID> /F                    # Windows
kill -9 <PID>                             # Linux/Mac

# View network
docker network ls
docker network inspect portfolio-network

# Check container IP
docker inspect -f '{{.NetworkSettings.Networks.portfolio-network.IPAddress}}' portfolio-backend

# Test connectivity between containers
docker exec portfolio-backend ping postgres
```

---

## Backup & Restore

```bash
# Backup entire compose setup
docker-compose config > backup-config.yml

# Backup database
docker exec portfolio-postgres pg_dump -U postgres -d virtual_portfolio > db-backup.sql

# Restore database
docker exec -i portfolio-postgres psql -U postgres -d virtual_portfolio < db-backup.sql

# Backup volumes
docker run --rm -v portfolio-postgres_postgres_data:/data -v $(pwd):/backup alpine tar czf /backup/postgres-backup.tar.gz -C /data .
```

---

## Development Shortcuts

### Quick Start
```bash
docker-compose up -d && echo "Services starting... wait 10-15 seconds"
```

### Quick Logs
```bash
docker-compose logs -f backend & docker-compose logs -f frontend
```

### Quick Rebuild
```bash
docker-compose down && docker-compose up --build -d
```

### Quick Clean
```bash
docker-compose down -v && docker system prune -a
```

---

## Advanced Commands

### Connect to running container
```bash
docker-compose exec backend bash
docker-compose exec frontend sh
```

### View environment variables in container
```bash
docker exec portfolio-backend env
```

### Copy file from container
```bash
docker cp portfolio-backend:/app/logs ./logs
```

### View Docker network
```bash
docker network ls
docker network inspect portfolio-network
```

---

## Useful Aliases (Optional)

Add to your shell profile (`~/.bashrc`, `~/.zshrc`, etc.):

```bash
alias dc='docker-compose'
alias dcup='docker-compose up -d'
alias dcdown='docker-compose down'
alias dclogs='docker-compose logs -f'
alias dcbuild='docker-compose build --no-cache'
alias dcclean='docker-compose down -v'
```

Then use: `dcup`, `dcdown`, etc.

---

**Last Updated**: January 14, 2026
**Version**: 1.0.0
