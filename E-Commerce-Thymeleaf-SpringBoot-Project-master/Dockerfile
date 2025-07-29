FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy everything
COPY . .

# Debug: List contents (these will work in the Linux container)
RUN ls -la
RUN ls -la src/ || echo "src directory not found"
RUN pwd
RUN find . -name "pom.xml" || echo "pom.xml not found"

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]