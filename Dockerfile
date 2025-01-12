FROM maven:3.9.1-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
ENV LANG C.UTF-8
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim AS builder
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:21-jdk-slim
RUN apt-get update && apt-get install -y curl \
    && curl -o /usr/local/bin/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
    && chmod +x /usr/local/bin/wait-for-it.sh
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]