FROM openjdk:17-alpine

WORKDIR /home/project_dev/Salary-Back

RUN chmod 700 gradlew

RUN ./gradlew clean build --no-daemon

ARG JAR_FILE=./build/libs/*.jar

RUN cp ${JAR_FILE} /salary.jar

ENTRYPOINT ["java", "-jar", "salary.jar"]

# FROM openjdk:17-alpine

# ARG JAR_PATH=build/libs/*.jar

# COPY ${JAR_PATH} salary.jar

# ENTRYPOINT ["java", "-jar", "salary.jar"]
