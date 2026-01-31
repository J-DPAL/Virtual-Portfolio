# Portfolio Data Seeding Script
# Populates the portfolio database with CV information

$baseUrl = "http://localhost:8080/api"
$headers = @{ "Content-Type" = "application/json" }

Write-Host "Portfolio Data Seeding Script" -ForegroundColor Cyan
Write-Host "Base URL: $baseUrl" -ForegroundColor Yellow
Write-Host ""

function global:Add-Experience {
    param([string]$companyEn, [string]$companyFr, [string]$positionEn, [string]$positionFr, [string]$descEn, [string]$descFr, [string]$startDate, [string]$endDate)
    $body = @{
        companyNameEn = $companyEn; companyNameFr = $companyFr; positionEn = $positionEn; positionFr = $positionFr
        descriptionEn = $descEn; descriptionFr = $descFr; startDate = $startDate; endDate = $endDate; isCurrent = $false; location = "Montreal, QC"
    } | ConvertTo-Json
    try {
        $response = Invoke-WebRequest -Uri "$baseUrl/experience" -Method Post -Headers $headers -Body $body -SkipHttpErrorCheck
        if ($response.StatusCode -in @(200, 201)) { Write-Host "✓ Added: $positionEn" -ForegroundColor Green }
        else { Write-Host "✗ Failed: $positionEn" -ForegroundColor Red }
    } catch { Write-Host "✗ Error: $positionEn" -ForegroundColor Red }
}

function global:Add-Education {
    param([string]$institutionEn, [string]$institutionFr, [string]$degreeEn, [string]$degreeFr, [string]$fieldEn, [string]$fieldFr, [string]$descEn, [string]$descFr, [string]$startDate, [string]$endDate, [bool]$current = $false)
    $body = @{
        institutionNameEn = $institutionEn; institutionNameFr = $institutionFr; degreeEn = $degreeEn; degreeFr = $degreeFr
        fieldOfStudyEn = $fieldEn; fieldOfStudyFr = $fieldFr; descriptionEn = $descEn; descriptionFr = $descFr
        startDate = $startDate; endDate = $endDate; isCurrent = $current; location = "Montreal, QC"
    } | ConvertTo-Json
    try {
        $response = Invoke-WebRequest -Uri "$baseUrl/education" -Method Post -Headers $headers -Body $body -SkipHttpErrorCheck
        if ($response.StatusCode -in @(200, 201)) { Write-Host "✓ Added: $degreeEn" -ForegroundColor Green }
        else { Write-Host "✗ Failed: $degreeEn" -ForegroundColor Red }
    } catch { Write-Host "✗ Error: $degreeEn" -ForegroundColor Red }
}

function global:Add-Project {
    param([string]$titleEn, [string]$titleFr, [string]$descEn, [string]$descFr, [string]$tech, [string]$startDate, [string]$endDate, [string]$status = "Completed")
    $body = @{
        titleEn = $titleEn; titleFr = $titleFr; descriptionEn = $descEn; descriptionFr = $descFr
        technologies = $tech; startDate = $startDate; endDate = $endDate; status = $status
    } | ConvertTo-Json
    try {
        $response = Invoke-WebRequest -Uri "$baseUrl/projects" -Method Post -Headers $headers -Body $body -SkipHttpErrorCheck
        if ($response.StatusCode -in @(200, 201)) { Write-Host "✓ Added: $titleEn" -ForegroundColor Green }
        else { Write-Host "✗ Failed: $titleEn" -ForegroundColor Red }
    } catch { Write-Host "✗ Error: $titleEn" -ForegroundColor Red }
}

function global:Add-Skill {
    param([string]$nameEn, [string]$nameFr, [string]$descEn, [string]$descFr, [string]$category, [string]$level, [int]$years = 0)
    $body = @{
        nameEn = $nameEn; nameFr = $nameFr; descriptionEn = $descEn; descriptionFr = $descFr
        category = $category; proficiencyLevel = $level; yearsOfExperience = $years
    } | ConvertTo-Json
    try {
        $response = Invoke-WebRequest -Uri "$baseUrl/skills" -Method Post -Headers $headers -Body $body -SkipHttpErrorCheck
        if ($response.StatusCode -in @(200, 201)) { Write-Host "✓ Skill: $nameEn" -ForegroundColor Green }
        else { Write-Host "✗ Failed: $nameEn" -ForegroundColor Red }
    } catch { Write-Host "✗ Error: $nameEn" -ForegroundColor Red }
}

Write-Host "Seeding Experiences..." -ForegroundColor Yellow
Add-Experience "Juliette and Chocolat" "Juliette and Chocolat" "Cook" "Cuisinier" `
    "Fast-paced kitchen environment with strong teamwork" "Fast-paced kitchen environment with strong teamwork" `
    "2023-06-01" "2023-06-30"
Add-Experience "Elections Quebec" "Elections Quebec" "Voter Attendant" "Voter Attendant" `
    "Welcoming voters and verifying identification" "Welcoming voters and verifying identification" `
    "2022-10-01" "2022-10-31"

Write-Host ""
Write-Host "Seeding Education..." -ForegroundColor Yellow
Add-Education "Champlain College" "Champlain College" "DEC Computer Science" "DEC Computer Science" `
    "Computer Science" "Computer Science" `
    "Technology student passionate about cybersecurity and development" "Technology student passionate about cybersecurity and development" `
    "2023-09-01" "2026-05-31" $true
Add-Education "College Charles-Lemoyne" "College Charles-Lemoyne" "High School Diploma" "High School Diploma" `
    "General Studies" "General Studies" `
    "High school diploma" "High school diploma" `
    "2019-09-01" "2023-06-30" $false

Write-Host ""
Write-Host "Seeding Projects..." -ForegroundColor Yellow
Add-Project "OpenHand MANA Mobile App" "OpenHand MANA Mobile App" `
    "React Native mobile app for MANA with Expo Router, Docker, and WhatsApp integration" `
    "React Native mobile app for MANA with Expo Router, Docker, and WhatsApp integration" `
    "React Native,Expo,TypeScript,Docker,PostgreSQL,Spring Boot" "2026-02-01" "" "In Progress"
Add-Project "Champlain PetClinic" "Champlain PetClinic" `
    "Agile web app with archive functionality and UX improvements" `
    "Agile web app with archive functionality and UX improvements" `
    "Java,Spring Boot,JUnit5,React,Jira" "2025-10-01" "2025-12-15" "Completed"

Write-Host ""
Write-Host "Seeding Skills..." -ForegroundColor Yellow
Add-Skill "Java" "Java" "Backend and enterprise applications" "Backend applications" "Programming Languages" "Advanced" 3
Add-Skill "Python" "Python" "Scripting and data processing" "Scripting" "Programming Languages" "Intermediate" 2
Add-Skill "JavaScript" "JavaScript" "Web development and Node.js" "Web development" "Programming Languages" "Advanced" 3
Add-Skill "TypeScript" "TypeScript" "JavaScript superset with typing" "Enhanced JavaScript" "Programming Languages" "Advanced" 2
Add-Skill "SQL" "SQL" "Database language and queries" "Database language" "Programming Languages" "Advanced" 3
Add-Skill "React" "React" "JavaScript library for UIs" "UI library" "Frontend Frameworks" "Advanced" 2
Add-Skill "React Native" "React Native" "Cross-platform mobile apps" "Mobile apps" "Mobile Frameworks" "Advanced" 1
Add-Skill "Spring Boot" "Spring Boot" "Java backend framework" "Backend framework" "Backend Frameworks" "Advanced" 2
Add-Skill "Docker" "Docker" "Container and deployment" "Container technology" "DevOps" "Advanced" 2
Add-Skill "PostgreSQL" "PostgreSQL" "Relational database system" "Database system" "Databases" "Advanced" 2
Add-Skill "Git/GitHub" "Git/GitHub" "Version control system" "Version control" "Development Tools" "Advanced" 3
Add-Skill "Tailwind CSS" "Tailwind CSS" "Utility-first CSS framework" "CSS framework" "Frontend Frameworks" "Advanced" 2

Write-Host ""
Write-Host "Seeding completed!" -ForegroundColor Cyan
