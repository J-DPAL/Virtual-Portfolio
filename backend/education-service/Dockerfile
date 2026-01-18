# Multi-stage build for Education Service
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom
COPY pom.xml .

# Copy education-service
COPY education-service/ education-service/

# Build education-service
RUN mvn clean package -pl education-service -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/education-service/target/education-service-*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]
