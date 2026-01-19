$ProgressPreference = 'SilentlyContinue'
$MavenHome = "$env:USERPROFILE\.mvn\apache-maven-3.9.5"
$MavenExe = "$MavenHome\bin\mvn.cmd"
$MavenUrl = "https://archive.apache.org/dist/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip"
$BackendDir = "C:\Final Project\Virtual Portfolio\backend"

Write-Host "Virtual Portfolio Maven Build" -ForegroundColor Cyan
Write-Host "============================" -ForegroundColor Cyan

if (!(Test-Path $MavenExe)) {
    Write-Host "Downloading Maven..." -ForegroundColor Yellow
    New-Item -Force -ItemType Directory -Path "$env:USERPROFILE\.mvn" | Out-Null
    Invoke-WebRequest -Uri $MavenUrl -OutFile "$env:USERPROFILE\.mvn\maven.zip"
    Expand-Archive -Path "$env:USERPROFILE\.mvn\maven.zip" -DestinationPath "$env:USERPROFILE\.mvn" -Force
    Remove-Item "$env:USERPROFILE\.mvn\maven.zip"
    Write-Host "Maven downloaded!" -ForegroundColor Green
}

Write-Host "Running Maven build..." -ForegroundColor Yellow
Set-Location -Path $BackendDir
& $MavenExe clean install -DskipTests 2>&1 | Select-String -Pattern "Building|SUCCESS|FAILURE|ERROR" | Select-Object -First 50

Write-Host ""
Write-Host "Build complete! Reload VS Code project." -ForegroundColor Green
