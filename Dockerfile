FROM gradle:7.6.0-jdk17 as build

ENV APP_HOME=/home/project_dev/Salary-Back

WORKDIR /home/project_dev/Salary-Back

COPY build.gradle settings.gradle gradlew $APP_HOME

COPY gradle $APP_HOME/gradle

RUN chmod +x gradlew

RUN ./gradlew clean build || return 0

COPY src $APP_HOME/src

FROM openjdk:17-alpine as runtime

ENV APP_HOME=/home/project_dev/Salary-Back
ARG ARTIFACT_NAME=salary.jar
ARG JAR_FILE_PATH=build/libs/salary.jar

WORKDIR $APP_HOME

COPY --from=build $APP_HOME/$JAR_FILE_PATH $ARTIFACT_NAME

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "salary.jar"]


# FROM openjdk:17-alpine

# ARG JAR_PATH=build/libs/*.jar

# COPY ${JAR_PATH} salary.jar

# ENTRYPOINT ["java", "-jar", "salary.jar"]
