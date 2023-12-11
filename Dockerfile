FROM openjdk:17-alpine

# RUN git clone https://github.com/brick0123/dummy.git # 깃허브에서 소스코드 clone
WORKDIR /home/project_dev/Salary-Back

RUN chmod 700 gradlew

RUN ./gradlew clean build

ARG JAR_FILE=./build/libs/*.jar

RUN cp ${JAR_FILE} /salary.jar

ENTRYPOINT ["java", "-jar", "salary.jar"]

# FROM openjdk:17-alpine

# ARG JAR_PATH=build/libs/*.jar

# COPY ${JAR_PATH} salary.jar

# ENTRYPOINT ["java", "-jar", "salary.jar"]
