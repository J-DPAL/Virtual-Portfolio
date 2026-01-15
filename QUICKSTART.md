# Virtual Portfolio - Quick Start Guide

## 🚀 Start the Project

### Option 1: Using Docker (Recommended)

#### Windows:
```bash
.\run.bat docker-up
```

#### Linux/Mac:
```bash
make docker-up
```

#### Direct Docker:
```bash
docker-compose up -d
```

**What this does:**
- Starts PostgreSQL database (port 5432)
- Starts Java backend (port 8080)
- Starts React frontend (port 3000)

---

## 🌐 Access the Application

After services start (wait 10-15 seconds):

| Component | URL |
|-----------|-----|
| **Frontend** | http://localhost:3000 |
| **Backend API** | http://localhost:8080/api |
| **Database** | localhost:5432 |

---

## 🔐 Login Credentials

```
Email: admin@portfolio.com
Password: admin123
```

---

## 📊 View Logs

### Backend logs:
```bash
docker-compose logs -f backend
```

### Frontend logs:
```bash
docker-compose logs -f frontend
```

### Database logs:
```bash
docker-compose logs -f postgres
```

### All logs:
```bash
docker-compose logs -f
```

---

## 🛑 Stop the Project

### Windows:
```bash
.\run.bat docker-down
```

### Linux/Mac:
```bash
make docker-down
```

### Direct Docker:
```bash
docker-compose down
```

---

## 🔄 Restart the Project

```bash
docker-compose restart
```

---

## 🏗️ Rebuild Images (if code changed)

```bash
docker-compose up -d --build
```

---

## 🗑️ Clean Everything

### Windows:
```bash
.\run.bat clean
```

### Linux/Mac:
```bash
make clean
```

### Direct Docker:
```bash
docker-compose down -v
docker system prune
```

---

## 📦 Local Development (Without Docker)

### Backend Setup:
```bash
cd backend
mvn install
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Frontend Setup:
```bash
cd frontend
npm install
npm run dev
```

Requires:
- Java 17+
- Maven 3.9+
- Node.js 18+
- PostgreSQL running locally

---

## 🔧 Troubleshooting

### Services won't start:
```bash
# Check Docker status
docker ps

# Check logs
docker-compose logs

# Rebuild everything
docker-compose down -v
docker-compose up --build
```

### Port already in use:

**Windows:**
```bash
netstat -ano | findstr :3000
taskkill /PID <PID> /F
```

**Linux/Mac:**
```bash
lsof -i :3000
kill -9 <PID>
```

### Database connection error:
```bash
# Wait 30 seconds for database to initialize, then restart
docker-compose restart backend
```

---

## 📁 File Locations

| File | Purpose |
|------|---------|
| `.env` | Environment variables (development) |
| `.env.production` | Production variables |
| `docker-compose.yml` | Docker service configuration |
| `backend/src/main/java/com/portfolio/api/PortfolioApplication.java` | Backend entry point |
| `frontend/src/App.jsx` | Frontend entry point |
| `database/init.sql` | Database schema |

---

## ⚠️ Important

1. **Default password** (`admin123`) should be changed immediately
2. **JWT Secret** should be changed in `.env` before production
3. **CORS origins** should be updated for production domains
4. **Database backups** should be set up for production

---

## ✅ Verify Everything Works

1. Frontend loads: http://localhost:3000 ✓
2. Backend API responds: http://localhost:8080/api/v1/auth/health ✓
3. Login works: Email: admin@portfolio.com, Password: admin123 ✓
4. Admin dashboard loads: http://localhost:3000/admin/dashboard ✓

---

## 📖 Full Documentation

See [README.md](README.md) and [SETUP.md](SETUP.md) for comprehensive documentation.

---

**Happy coding! 🎉**
