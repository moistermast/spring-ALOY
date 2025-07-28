
FROM openjdk:17-jdk-slim

WORKDIR /app

EXPOSE 8080

ADD target/spring-boot-docker.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
