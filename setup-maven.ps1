# Maven Setup Script for Virtual Portfolio
# This script helps set up Maven if it's not installed

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "Virtual Portfolio Maven Setup" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Check Java
Write-Host "Checking Java installation..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1
    Write-Host "✓ Java found:" -ForegroundColor Green
    Write-Host ($javaVersion -split '\n')[0]
} catch {
    Write-Host "✗ Java not found. Please install Java 17+" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "1. Option A: Install Maven via Chocolatey (requires admin):" -ForegroundColor White
Write-Host "   choco install maven -y" -ForegroundColor Gray
Write-Host ""
Write-Host "2. Option B: Use VS Code Maven extension:" -ForegroundColor White
Write-Host "   - Open Command Palette (Ctrl+Shift+P)" -ForegroundColor Gray
Write-Host "   - Type 'Maven: update project index'" -ForegroundColor Gray
Write-Host "   - OR 'Maven: resolve conflicts'" -ForegroundColor Gray
Write-Host ""
Write-Host "3. Option C: Open project folder in VS Code and wait for indexing" -ForegroundColor White
Write-Host ""
Write-Host "If all else fails, you can restart VS Code:" -ForegroundColor Yellow
Write-Host "   File > Close Folder, then reopen the project folder" -ForegroundColor Gray
