FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/alarmas-0.0.1.jar
COPY ${JAR_FILE} app_alarmas.jar

EXPOSE 8084
ENTRYPOINT ["java","-jar","/app_alarmas.jar"]
