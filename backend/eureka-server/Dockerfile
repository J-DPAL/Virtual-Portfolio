# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /build
# Copy entire backend to allow module build with dependencies
COPY . /build
RUN mvn -q -e -DskipTests clean package -pl eureka-server -am

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /build/eureka-server/target/eureka-server-*.jar /app/app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app/app.jar"]
