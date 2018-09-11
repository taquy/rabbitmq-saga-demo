FROM openjdk:8-jdk-alpine
MAINTAINER taquy <taquy.se@gmail.com>
VOLUME /tmp
EXPOSE 8091
ARG JAR_FILE=target/saga.orchestrator-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} saga.orchestrator-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/saga.orchestrator-0.0.1-SNAPSHOT.jar"]
