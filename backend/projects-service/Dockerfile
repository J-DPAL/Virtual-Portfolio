# Multi-stage build for Projects Service
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom
COPY pom.xml .

# Copy projects-service
COPY projects-service/ projects-service/

# Build projects-service
RUN mvn clean package -pl projects-service -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/projects-service/target/projects-service-*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
