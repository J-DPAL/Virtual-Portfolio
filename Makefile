.PHONY: help docker-up docker-down docker-build docker-logs \
        backend-run backend-test backend-build frontend-setup \
        frontend-dev frontend-build clean

help:
	@echo "Virtual Portfolio (Monolith) - Commands"
	@echo "======================================="
	@echo "Docker:"
	@echo "  make docker-up         Start frontend + monolith"
	@echo "  make docker-down       Stop services"
	@echo "  make docker-build      Rebuild images"
	@echo "  make docker-logs       Follow logs"
	@echo ""
	@echo "Backend:"
	@echo "  make backend-run       Run monolith locally"
	@echo "  make backend-test      Run monolith tests"
	@echo "  make backend-build     Build monolith jar"
	@echo ""
	@echo "Frontend:"
	@echo "  make frontend-setup    Install dependencies"
	@echo "  make frontend-dev      Start Vite dev server"
	@echo "  make frontend-build    Build frontend"
	@echo ""
	@echo "Utility:"
	@echo "  make clean             Clean build artifacts"

docker-up:
	docker compose up -d --build

docker-down:
	docker compose down

docker-build:
	docker compose build --no-cache

docker-logs:
	docker compose logs -f

backend-run:
	cd backend && mvn -pl monolith-service -am spring-boot:run

backend-test:
	cd backend && mvn -pl monolith-service -am test

backend-build:
	cd backend && mvn -pl monolith-service -am clean package -DskipTests

frontend-setup:
	cd frontend && npm ci

frontend-dev:
	cd frontend && npm run dev

frontend-build:
	cd frontend && npm run build

clean:
	cd backend && mvn clean
	docker compose down -v

.DEFAULT_GOAL := help
