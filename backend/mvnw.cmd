@echo off
REM Maven Wrapper for Windows
REM This downloads Maven to a local directory and runs it
REM No admin rights needed!

setlocal enabledelayedexpansion

set "MAVEN_HOME=%USERPROFILE%\.mvn\apache-maven-3.9.5"
set "MAVEN_EXE=%MAVEN_HOME%\bin\mvn.cmd"
set "MAVEN_DOWNLOAD_URL=https://archive.apache.org/dist/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip"

REM Check if Maven is already downloaded
if exist "%MAVEN_EXE%" (
    echo Found Maven at: !MAVEN_EXE!
    goto :run_maven
)

REM Download Maven
echo Downloading Maven 3.9.5...
powershell -NoProfile -ExecutionPolicy Bypass -Command "^
    $ProgressPreference = 'SilentlyContinue'; ^
    mkdir -Force '%USERPROFILE%\.mvn' | Out-Null; ^
    Invoke-WebRequest -Uri '%MAVEN_DOWNLOAD_URL%' -OutFile '%USERPROFILE%\.mvn\maven.zip'; ^
    Expand-Archive -Path '%USERPROFILE%\.mvn\maven.zip' -DestinationPath '%USERPROFILE%\.mvn' -Force; ^
    Remove-Item '%USERPROFILE%\.mvn\maven.zip'; ^
    Write-Host 'Maven downloaded successfully!'
"

:run_maven
REM Run Maven with all arguments passed through
"%MAVEN_EXE%" %*
