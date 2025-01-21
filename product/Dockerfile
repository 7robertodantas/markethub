# Build stage
FROM maven:3.9-amazoncorretto-17-alpine AS build
ARG MAVEN_OPTS
WORKDIR /home/app
COPY pom.xml /home/app
RUN --mount=type=cache,target=/root/.m2 mvn -f /home/app/pom.xml dependency:go-offline -B
COPY src /home/app/src
RUN --mount=type=cache,target=/root/.m2 mvn -f /home/app/pom.xml ${MAVEN_OPTS} clean test package

# Package stage
FROM amazoncorretto:17-alpine3.19-jdk
COPY --from=build /home/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]