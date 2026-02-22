@echo off
setlocal

echo.
echo ========================================
echo JaCoCo Coverage - Monolith Service
echo ========================================
echo.

cd /d "%~dp0backend"
call mvn -pl monolith-service -am clean test jacoco:report -DskipTests=false
if %ERRORLEVEL% neq 0 (
  echo.
  echo Coverage generation failed.
  exit /b 1
)

if exist ".\monolith-service\target\site\jacoco\index.html" (
  echo.
  echo Coverage report:
  echo   backend\monolith-service\target\site\jacoco\index.html
) else (
  echo.
  echo Report file not found.
)

cd /d "%~dp0"
endlocal
