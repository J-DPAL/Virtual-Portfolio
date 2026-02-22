@echo off
setlocal

if "%1"=="" goto help
if "%1"=="docker-up" goto docker-up
if "%1"=="docker-down" goto docker-down
if "%1"=="docker-build" goto docker-build
if "%1"=="docker-logs" goto docker-logs
if "%1"=="backend-run" goto backend-run
if "%1"=="backend-test" goto backend-test
if "%1"=="backend-build" goto backend-build
if "%1"=="frontend-setup" goto frontend-setup
if "%1"=="frontend-dev" goto frontend-dev
if "%1"=="frontend-build" goto frontend-build
if "%1"=="clean" goto clean
goto help

:help
echo Virtual Portfolio (Monolith) - Commands
echo =======================================
echo   run.bat docker-up
echo   run.bat docker-down
echo   run.bat docker-build
echo   run.bat docker-logs
echo   run.bat backend-run
echo   run.bat backend-test
echo   run.bat backend-build
echo   run.bat frontend-setup
echo   run.bat frontend-dev
echo   run.bat frontend-build
echo   run.bat clean
goto end

:docker-up
docker compose up -d --build
goto end

:docker-down
docker compose down
goto end

:docker-build
docker compose build --no-cache
goto end

:docker-logs
docker compose logs -f
goto end

:backend-run
cd backend
call mvn -pl monolith-service -am spring-boot:run
cd ..
goto end

:backend-test
cd backend
call mvn -pl monolith-service -am test
cd ..
goto end

:backend-build
cd backend
call mvn -pl monolith-service -am clean package -DskipTests
cd ..
goto end

:frontend-setup
cd frontend
call npm ci
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
cd ..
docker compose down -v
goto end

:end
endlocal
