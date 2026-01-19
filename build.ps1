$ErrorActionPreference = "Continue"

$MavenHome = "$env:USERPROFILE\.mvn\apache-maven-3.9.5"
$MavenExe = "$MavenHome\bin\mvn.cmd"
$MavenUrl = "https://archive.apache.org/dist/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip"
$BackendDir = "c:\Final Project\Virtual Portfolio\backend"

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "Virtual Portfolio Maven Build Script" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Check Java
Write-Host "[1/3] Checking Java..." -ForegroundColor Yellow
$javaVersion = java -version 2>&1
if ($?) {
    Write-Host "✓ Java is installed" -ForegroundColor Green
} else {
    Write-Host "✗ Java not found" -ForegroundColor Red
    exit 1
}

# Download Maven if needed
if (-not (Test-Path $MavenExe)) {
    Write-Host "[2/3] Downloading Maven 3.9.5..." -ForegroundColor Yellow
    $ProgressPreference = 'SilentlyContinue'
    
    mkdir -Force "$env:USERPROFILE\.mvn" | Out-Null
    Write-Host "Downloading from: $MavenUrl" -ForegroundColor Gray
    
    Invoke-WebRequest -Uri $MavenUrl -OutFile "$env:USERPROFILE\.mvn\maven.zip"
    Write-Host "Extracting..." -ForegroundColor Gray
    Expand-Archive -Path "$env:USERPROFILE\.mvn\maven.zip" -DestinationPath "$env:USERPROFILE\.mvn" -Force
    Remove-Item "$env:USERPROFILE\.mvn\maven.zip"
    Write-Host "✓ Maven downloaded and extracted" -ForegroundColor Green
} else {
    Write-Host "[2/3] Maven already downloaded" -ForegroundColor Yellow
    Write-Host "Located at: $MavenHome" -ForegroundColor Gray
}

# Run Maven build
Write-Host "[3/3] Building project..." -ForegroundColor Yellow
Write-Host ""
cd $BackendDir
& $MavenExe clean install -DskipTests

Write-Host ""
Write-Host "============================================" -ForegroundColor Green
Write-Host "✓ BUILD COMPLETE!" -ForegroundColor Green
Write-Host "============================================" -ForegroundColor Green
Write-Host ""
Write-Host "All dependencies downloaded and project built." -ForegroundColor Green
Write-Host "Reload the project in VS Code to clear errors." -ForegroundColor Green
