# Multi-stage build for Experience Service
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom
COPY pom.xml .

# Copy experience-service
COPY experience-service/ experience-service/

# Build experience-service
RUN mvn clean package -pl experience-service -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/experience-service/target/experience-service-*.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]
