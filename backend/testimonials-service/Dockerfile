# Multi-stage build for Testimonials Service
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy entire backend so the parent pom can resolve all modules during the targeted build
COPY . .

# Build testimonials-service only (compile all required modules)
RUN mvn clean package -pl testimonials-service -am -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/testimonials-service/target/testimonials-service-*.jar app.jar

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "app.jar"]
