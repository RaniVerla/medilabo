FROM eclipse-temurin:21-jdk
ARG JAR_FILE=build/libs/demographics-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "./application.jar"]