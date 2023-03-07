FROM gradle:7.4-jdk11-alpine as builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:11.0-slim
COPY --from=builder build/libs/*.jar app.jar
ARG ENVIRONMENT
ENV SPRING_PROFILES_ACTIVE=${ENVIRONMENT}
EXPOSE 8080
ENTRYPOINT [\
        "java",   \
         "-jar", \
        "/app.jar"    \
]