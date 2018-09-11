FROM openjdk:8-jdk-alpine
MAINTAINER taquy <taquy.se@gmail.com>
VOLUME /tmp
EXPOSE 8093
ARG JAR_FILE=target/saga.service.b-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} saga.service.b-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/saga.service.b-0.0.1-SNAPSHOT.jar"]
