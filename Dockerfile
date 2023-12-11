FROM gradle:6.9.2-openjdk:17-alpine

WORKDIR /home/project_dev/Salary-Back

# Gradle 설치
# RUN apk --no-cache add curl
# COPY sdkman-install.sh /home/project_dev/Salary-Back/sdkman-install.sh
# RUN chmod +x /home/project_dev/Salary-Back/sdkman-install.sh
# RUN /bin/ash /home/project_dev/Salary-Back/sdkman-install.sh
# RUN rm /home/project_dev/Salary-Back/sdkman-install.sh
#
# # Shell을 사용하여 명령어 실행
# SHELL ["/bin/sh", "-c"]
# RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && sdk install gradle

RUN chmod 755 gradlew
RUN ./gradlew clean build --no-daemon

ARG JAR_FILE=./build/libs/*.jar
RUN cp ${JAR_FILE} /salary.jar
ENTRYPOINT ["java", "-jar", "salary.jar"]

# FROM openjdk:17-alpine

# ARG JAR_PATH=build/libs/*.jar

# COPY ${JAR_PATH} salary.jar

# ENTRYPOINT ["java", "-jar", "salary.jar"]
