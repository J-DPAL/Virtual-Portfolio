# Build stage
FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app

COPY . .

# Build only the monolith service and required parents.
RUN mvn clean package -pl monolith-service -am -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=builder /app/monolith-service/target/*.jar app.jar

ENV SERVER_PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
