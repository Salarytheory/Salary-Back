FROM openjdk:17-alpine

WORKDIR /home/project_dev/Salary-Back

COPY . /home/project_dev/Salary-Back

RUN ./gradlew clean build

CMD ["java", "-jar", "/home/project_dev/Salary-Back/build/libs/salary.jar"]
