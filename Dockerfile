FROM openjdk:11-jre-slim
VOLUME /tmp
ARG JAR_FILE
ARG TARGET_ENV
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${TARGET_ENV}", "-jar","/app.jar"]
