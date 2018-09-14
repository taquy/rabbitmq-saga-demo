FROM openjdk:8-jdk-alpine
MAINTAINER taquy <taquy.se@gmail.com>
VOLUME /tmp
EXPOSE 8091
ARG JAR_FILE=target/service.ticket-1.jar
ADD ${JAR_FILE} service.ticket-1.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/service.ticket-1.jar"]
