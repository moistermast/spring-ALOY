FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .

# Add these debug lines
RUN echo "=== Listing /app directory ==="
RUN ls -la /app
RUN echo "=== Checking for pom.xml ==="
RUN ls -la pom.xml || echo "pom.xml not found!"
RUN echo "=== Current working directory ==="
RUN pwd

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]