@echo off
REM JaCoCo Code Coverage Generation Script for Windows
REM This script generates code coverage reports for all modules

echo.
echo ========================================
echo JaCoCo Code Coverage Generation
echo ========================================
echo.

REM Navigate to backend directory
cd /d "%~dp0backend"

REM Clean and run tests with coverage
echo Running tests and generating coverage reports...
call mvn clean test jacoco:report -DskipTests=false

if %ERRORLEVEL% neq 0 (
    echo.
    echo ERROR: Test execution failed!
    echo.
    exit /b 1
)

echo.
echo ========================================
echo Coverage Reports Generated Successfully
echo ========================================
echo.
echo Coverage reports are available at:
echo.
for /d %%D in (.\*-service) do (
    if exist "%%D\target\site\jacoco\index.html" (
        echo   - %%D\target\site\jacoco\index.html
    )
)

if exist ".\api-gateway\target\site\jacoco\index.html" (
    echo   - .\api-gateway\target\site\jacoco\index.html
)

if exist ".\eureka-server\target\site\jacoco\index.html" (
    echo   - .\eureka-server\target\site\jacoco\index.html
)

echo.
echo To view coverage reports:
echo   1. Open the index.html files in your browser
echo   2. Or run: mvn jacoco:report && start .\MODULE\target\site\jacoco\index.html
echo.

REM Return to original directory
cd /d "%~dp0"
exit /b 0
