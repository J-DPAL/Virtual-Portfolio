# Makefile for Virtual Portfolio

.PHONY: help docker-up docker-down docker-build docker-logs \
        backend-setup backend-run backend-test frontend-setup \
        frontend-dev frontend-build db-init clean all

help:
	@echo "Virtual Portfolio - Available Commands"
	@echo "======================================"
	@echo "Docker Commands:"
	@echo "  make docker-up         Start all services with Docker Compose"
	@echo "  make docker-down       Stop all services"
	@echo "  make docker-build      Build all Docker images"
	@echo "  make docker-logs       View logs from all services"
	@echo ""
	@echo "Backend Commands:"
	@echo "  make backend-setup     Install backend dependencies"
	@echo "  make backend-run       Run backend locally"
	@echo "  make backend-test      Run backend tests"
	@echo ""
	@echo "Frontend Commands:"
	@echo "  make frontend-setup    Install frontend dependencies"
	@echo "  make frontend-dev      Run frontend dev server"
	@echo "  make frontend-build    Build frontend for production"
	@echo ""
	@echo "Database Commands:"
	@echo "  make db-init           Initialize database"
	@echo ""
	@echo "Utility Commands:"
	@echo "  make clean             Clean all build artifacts"
	@echo "  make all               Full setup and start"

# Docker commands
docker-up:
	docker-compose up -d
	@echo "Services started. Access:"
	@echo "Frontend: http://localhost:3000"
	@echo "Backend: http://localhost:8080/api"
	@echo "Database: localhost:5432"

docker-down:
	docker-compose down

docker-build:
	docker-compose build --no-cache

docker-logs:
	docker-compose logs -f

docker-logs-backend:
	docker-compose logs -f backend

docker-logs-frontend:
	docker-compose logs -f frontend

docker-logs-db:
	docker-compose logs -f postgres

# Backend commands
backend-setup:
	cd backend && mvn install

backend-run:
	cd backend && mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

backend-test:
	cd backend && mvn test

backend-build:
	cd backend && mvn clean package -DskipTests

# Frontend commands
frontend-setup:
	cd frontend && npm install

frontend-dev:
	cd frontend && npm run dev

frontend-build:
	cd frontend && npm run build

frontend-lint:
	cd frontend && npm run lint

# Database commands
db-init:
	psql -U postgres -d virtual_portfolio -f database/init.sql

# Utility commands
clean:
	cd backend && mvn clean
	cd frontend && npm run clean || true
	docker-compose down -v

all: clean backend-setup frontend-setup docker-up
	@echo "Project setup complete!"

# Install dependencies
install: backend-setup frontend-setup
	@echo "All dependencies installed"

# Run tests
test: backend-test
	@echo "All tests completed"

.DEFAULT_GOAL := help
