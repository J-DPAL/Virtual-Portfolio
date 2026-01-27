FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /build

# Copy full backend context so parent pom can resolve child modules
COPY . .

RUN apk add --no-cache maven
RUN mvn -q -e -DskipTests clean package -pl files-service -am

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN mkdir -p /app/uploads/resumes

COPY --from=build /build/files-service/target/*.jar app.jar

EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]
