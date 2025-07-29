FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copy specific files/directories explicitly
COPY pom.xml /build/
COPY src/ /build/src/
COPY mvnw /build/
COPY .mvn/ /build/.mvn/

# Verify they're there
RUN ls -la /build
RUN ls -la /build/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]