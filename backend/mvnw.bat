@echo off
REM Maven wrapper script for Windows

set MAVEN_HOME=%USERPROFILE%\.mvn\apache-maven-3.9.5
set MAVEN_EXE=%MAVEN_HOME%\bin\mvn.cmd

if not exist "%MAVEN_EXE%" (
    echo Maven not found at %MAVEN_EXE%
    echo Please run the setup-maven.ps1 script from the project root
    exit /b 1
)

"%MAVEN_EXE%" %*
