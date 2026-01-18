# Multi-stage build for Hobbies Service
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom
COPY pom.xml .

# Copy hobbies-service
COPY hobbies-service/ hobbies-service/

# Build hobbies-service
RUN mvn clean package -pl hobbies-service -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/hobbies-service/target/hobbies-service-*.jar app.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "app.jar"]
