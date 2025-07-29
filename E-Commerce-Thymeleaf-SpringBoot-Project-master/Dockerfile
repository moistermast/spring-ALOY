FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copy only the essential files needed for Maven build
COPY pom.xml ./
COPY src ./src

# Build using system Maven (more reliable)
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]