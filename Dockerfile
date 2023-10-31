FROM openjdk:17-alpine

WORKDIR build/libs/*.jar

ARG JAR_PATH=build/libs/*.jar

COPY ${JAR_PATH} salary.jar

ENTRYPOINT ["java", "-jar", "salary.jar"]
