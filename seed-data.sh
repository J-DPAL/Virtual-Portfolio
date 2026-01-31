#!/bin/bash

# Portfolio Data Seeding Script using curl
# Populates the portfolio database with CV information

BASE_URL="http://localhost:8080/api"

echo "======================================"
echo "Portfolio Data Seeding Script"
echo "======================================"
echo "Base URL: $BASE_URL"
echo ""

# Function to add experience
add_experience() {
  local company_en=$1
  local company_fr=$2
  local position_en=$3
  local position_fr=$4
  local desc_en=$5
  local desc_fr=$6
  local start_date=$7
  local end_date=$8
  
  curl -X POST "$BASE_URL/v1/experience" \
    -H "Content-Type: application/json" \
    -d "{
      \"companyNameEn\": \"$company_en\",
      \"companyNameFr\": \"$company_fr\",
      \"positionEn\": \"$position_en\",
      \"positionFr\": \"$position_fr\",
      \"descriptionEn\": \"$desc_en\",
      \"descriptionFr\": \"$desc_fr\",
      \"startDate\": \"$start_date\",
      \"endDate\": $end_date,
      \"isCurrent\": false
    }" 2>/dev/null
  
  echo "✓ Added experience: $position_en"
}

# Function to add education
add_education() {
  local institution_en=$1
  local institution_fr=$2
  local degree_en=$3
  local degree_fr=$4
  local field_en=$5
  local field_fr=$6
  local desc_en=$7
  local desc_fr=$8
  local start_date=$9
  local end_date=${10}
  
  curl -X POST "$BASE_URL/v1/education" \
    -H "Content-Type: application/json" \
    -d "{
      \"institutionNameEn\": \"$institution_en\",
      \"institutionNameFr\": \"$institution_fr\",
      \"degreeEn\": \"$degree_en\",
      \"degreeFr\": \"$degree_fr\",
      \"fieldOfStudyEn\": \"$field_en\",
      \"fieldOfStudyFr\": \"$field_fr\",
      \"descriptionEn\": \"$desc_en\",
      \"descriptionFr\": \"$desc_fr\",
      \"startDate\": \"$start_date\",
      \"endDate\": \"$end_date\"
    }" 2>/dev/null
  
  echo "✓ Added education: $degree_en"
}

# Add experiences
echo ""
echo "Seeding Work Experiences..."
add_experience "Juliette & Chocolat" "Juliette & Chocolat" "Cook" "Cuisinier" \
  "Prepared and assembled dishes in a fast-paced, demanding environment." \
  "Préparation et assemblage de plats dans un environnement rapide et exigeant." \
  "2023-06-01" "null"

add_experience "Élections Québec" "Élections Québec" "Voter Attendant" "Préposé aux électeurs" \
  "Welcomed voters and verified identification, ensuring confidentiality and adherence to procedures." \
  "Accueil des électeurs et vérification de l'identification, garantissant la confidentialité." \
  "2022-10-01" "\"2022-10-31\""

echo ""
echo "Data seeding completed!"
