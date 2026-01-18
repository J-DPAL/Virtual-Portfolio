# Multi-stage build for Messages Service
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom
COPY pom.xml .

# Copy messages-service
COPY messages-service/ messages-service/

# Build messages-service
RUN mvn clean package -pl messages-service -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/messages-service/target/messages-service-*.jar app.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "app.jar"]
