# Multi-stage build for API Gateway
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom
COPY pom.xml .

# Copy api-gateway
COPY api-gateway/ api-gateway/

# Build api-gateway
RUN mvn clean package -pl api-gateway -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/api-gateway/target/api-gateway-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
