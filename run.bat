@echo off
REM Batch file for Windows equivalents of Makefile commands

setlocal enabledelayedexpansion

if "%1"=="" goto help
if "%1"=="docker-up" goto docker-up
if "%1"=="docker-down" goto docker-down
if "%1"=="docker-build" goto docker-build
if "%1"=="docker-logs" goto docker-logs
if "%1"=="backend-setup" goto backend-setup
if "%1"=="backend-run" goto backend-run
if "%1"=="frontend-setup" goto frontend-setup
if "%1"=="frontend-dev" goto frontend-dev
if "%1"=="frontend-build" goto frontend-build
if "%1"=="clean" goto clean
goto help

:help
echo Virtual Portfolio - Available Commands
echo ======================================
echo Docker Commands:
echo   .\run.bat docker-up       Start all services with Docker Compose
echo   .\run.bat docker-down     Stop all services
echo   .\run.bat docker-build    Build all Docker images
echo   .\run.bat docker-logs     View logs from all services
echo.
echo Backend Commands:
echo   .\run.bat backend-setup   Install backend dependencies
echo   .\run.bat backend-run     Run backend locally
echo.
echo Frontend Commands:
echo   .\run.bat frontend-setup  Install frontend dependencies
echo   .\run.bat frontend-dev    Run frontend dev server
echo   .\run.bat frontend-build  Build frontend for production
echo.
echo Utility Commands:
echo   .\run.bat clean           Clean all build artifacts
goto end

:docker-up
docker-compose up -d
echo Services started. Access:
echo Frontend: http://localhost:3000
echo Backend: http://localhost:8080/api
goto end

:docker-down
docker-compose down
goto end

:docker-build
docker-compose build --no-cache
goto end

:docker-logs
docker-compose logs -f
goto end

:backend-setup
cd backend
call mvn install
cd ..
goto end

:backend-run
cd backend
call mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
cd ..
goto end

:frontend-setup
cd frontend
call npm install
cd ..
goto end

:frontend-dev
cd frontend
call npm run dev
cd ..
goto end

:frontend-build
cd frontend
call npm run build
cd ..
goto end

:clean
cd backend
call mvn clean
cd ..\frontend
call npm run clean 2>nul || echo Skipped
cd ..
call docker-compose down -v
goto end

:end
endlocal
