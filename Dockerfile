FROM openjdk:17-alpine

WORKDIR /home/project_dev/Salary-Back

COPY . /home/project_dev/Salary-Back

RUN ./gradlew clean build --daemon

CMD ["java", "-jar", "/home/project_dev/Salary-Back/build/libs/salary.jar"]

# FROM openjdk:17-alpine
#
# ARG JAR_PATH=build/libs/*.jar
#
# COPY ${JAR_PATH} salary.jar
#
# ENTRYPOINT ["java", "-jar", "salary.jar"]
