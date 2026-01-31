$baseUrl = "http://localhost:8080/api"
$headers = @{"Content-Type"="application/json"}

Write-Host "Seeding Skills..." -ForegroundColor Green
$skills = @(
    @{nameEn="Python"},
    @{nameEn="JavaScript"},
    @{nameEn="SQL"}
)
foreach ($skill in $skills) {
    $skill.nameFr = $skill.nameEn
    $skill.proficiencyLevel = "Advanced"
    $skill.category = "Backend"
    try {
        Invoke-WebRequest -Uri "$baseUrl/skills" -Method Post -Headers $headers -Body ($skill | ConvertTo-Json) -UseBasicParsing | Out-Null
        Write-Host "Added: $($skill.nameEn)"
    } catch {
        Write-Host "Error: $($skill.nameEn)"
    }
}
