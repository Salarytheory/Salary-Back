FROM openjdk:17-alpine

WORKDIR JAR_PATH=build/libs/*.jar

COPY ${JAR_PATH} salary.jar

ENTRYPOINT ["java", "-jar", "salary.jar"]
