#!/usr/bin/env pwsh
# Maven wrapper script for PowerShell

$MavenHome = "$env:USERPROFILE\.mvn\apache-maven-3.9.5"
$MavenExe = "$MavenHome\bin\mvn.cmd"

if (-not (Test-Path $MavenExe)) {
    Write-Error "Maven not found at $MavenExe"
    Write-Host "Please run the setup-maven.ps1 script from the project root"
    exit 1
}

& $MavenExe @args
