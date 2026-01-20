# Multi-stage build for Skills Service
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy entire backend so the parent pom can resolve all modules during the targeted build
COPY . .

# Build skills-service only (compile all required modules)
RUN mvn clean package -pl skills-service -am -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/skills-service/target/skills-service-*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
