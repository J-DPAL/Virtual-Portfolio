# Multi-stage build for Testimonials Service
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom
COPY pom.xml .

# Copy testimonials-service
COPY testimonials-service/ testimonials-service/

# Build testimonials-service
RUN mvn clean package -pl testimonials-service -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/testimonials-service/target/testimonials-service-*.jar app.jar

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "app.jar"]
