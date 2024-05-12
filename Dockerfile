FROM openjdk:17

VOLUME /logs
VOLUME /tmp

ARG JAR_FILE=./build/libs/*.jar
ENV IDLE_PROFILE dev

COPY ${JAR_FILE} spring.jar
COPY ./src/main/resources/application.properties /application.properties

# 실행 명령

ENTRYPOINT ["nohup", "java","-jar",\
"-Dspring.config.location=/application.properties",\
"spring.jar", "2>&1", "&"]